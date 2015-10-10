package es.view.anotai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task;
import projeto.es.view.anotai.R;

public class TaskActivity extends Activity {
	private DisciplinePersister dPersister; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dPersister = new DisciplinePersister(this);

		setContentView(R.layout.activity_task);
		Bundle extras = getIntent().getExtras();
		Task task = (Task) extras.get("TASK");
		if (task instanceof Exam) {
			makeLayoutExam((Exam) task);
		} else if (task instanceof IndividualHomework) {
			makeLayoutIndividualHomeWork((IndividualHomework) task);
		} else if (task instanceof GroupHomework) {
			makeLayoutGroupHomeWork((GroupHomework) task);
		}
	}

	private void makeLayoutGroupHomeWork(GroupHomework task) {
		makeComunLayout(task);
		// nome da atividade e lista de colegas
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_task_layout_title);
		layout.setVisibility(LinearLayout.VISIBLE);

		EditText etTitle = (EditText) findViewById(R.id.activity_task_et_name_homework);
		etTitle.setText(task.getTitle());
	}

	private void makeLayoutIndividualHomeWork(IndividualHomework task) {
		makeComunLayout(task);
		// nome da atividade.
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_task_layout_title);
		layout.setVisibility(LinearLayout.VISIBLE);

		EditText etTitle = (EditText) findViewById(R.id.activity_task_et_name_homework);
		etTitle.setText(task.getTitle());
	}

	private void makeLayoutExam(Exam task) {
		makeComunLayout(task);
	}

	private void makeComunLayout(final Task task) {
		// O valor 1 para o Spinner é apenas para teste e não é definitivo,
		// a forma como o spiner é preenchido deve mudar.
		povoateDiscSpinner(task);

		// descrição da tarefa
		EditText etDescription = (EditText) findViewById(R.id.activity_task_et_task_description);
		etDescription.setText(task.getDescription());

		// data de entrega
		Calendar deadlineDate = task.getDeadlineDate();
		EditText etDeadLineDate = (EditText) findViewById(R.id.activity_task_et_deadline_date);
		etDeadLineDate.setText(new StringBuilder().append(deadlineDate.get(Calendar.DAY_OF_MONTH)).append("/")
				.append(deadlineDate.get(Calendar.MONTH) + 1).append("/").append(deadlineDate.get(Calendar.YEAR))
				.append(" ").append(deadlineDate.get(Calendar.HOUR_OF_DAY)).append(":")
				.append(deadlineDate.get(Calendar.MINUTE)));

		final Button btSave = (Button) findViewById(R.id.activity_task_bt_save_task);

		final EditText etGrade = (EditText) findViewById(R.id.activity_task_et_grade);
		etGrade.setText(String.valueOf(task.getGrade()));
		final String oldValue = etGrade.getText().toString();

		etGrade.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// tentou editar a nota
				Log.i("TaskActivity", "O listner da nota pegou um evento");
				if (etGrade.getText().toString() != oldValue) {
//					oldValue = etGrade.getText().toString();
					btSave.setVisibility(Button.VISIBLE);
				}
			}
		});
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("TaskActivity", "salvar a tarefa com a nova nota");

				// salva a tarefa no banco e volta.
				task.setGrade(Float.parseFloat(etGrade.getText().toString()));
				//TODO
				onBackPressed();
			}
		});

	}

	private void povoateDiscSpinner(Task task) {
		Spinner spDisciplines = (Spinner) findViewById(R.id.activity_task_sp_discipline_select);
        List<Discipline> disciplines = dPersister.retrieveAll();
        spDisciplines.setAdapter(new DisciplineAdapter(this, disciplines));
        spDisciplines.setSelection(0);		
	}

}
