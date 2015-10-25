package es.view.anotai;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task;
import es.model.anotai.Task.Priority;

public class TaskActivity extends Activity {
	private static final String CLASS_TAG = "TaskActivity";
	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private DisciplinePersister dPersister;
	private TaskPersister tPersister; 
	private EditText etDeadLineDate;
	private Calendar deadDateCalendar = Calendar.getInstance();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dPersister = new DisciplinePersister(this);
		tPersister = new TaskPersister(this);

		setContentView(R.layout.activity_task);
		Bundle extras = getIntent().getExtras();
		Task task = (Task) extras.get("TASK");
		if (task instanceof Exam) {
			makeLayout((Exam) task);
		} else if (task instanceof IndividualHomework) {
			makeLayout((IndividualHomework) task);
		} else if (task instanceof GroupHomework) {
			makeLayoutGroupHomeWork((GroupHomework) task);
		}
	}

	private void makeLayoutGroupHomeWork(GroupHomework task) {
		makeLayout(task); //FIXME Fazer lista de amigos aparecer
	}

	private void makeLayout(final Task task) {
		povoateDiscSpinner(task);

		final EditText etDescription = (EditText) findViewById(R.id.activity_task_et_task_description);
		etDescription.setText(task.getDescription());
		
		final EditText etTitle = (EditText) findViewById(R.id.activity_task_et_title_task);
		etTitle.setText(task.getTitle());

		etDeadLineDate = (EditText) findViewById(R.id.activity_task_et_deadline_date);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		Calendar deadlineDate = task.getDeadlineDate();
		etDeadLineDate.setText(formatter.format(deadlineDate.getTime()));
		etDeadLineDate.setInputType(InputType.TYPE_NULL);
		etDeadLineDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		}); 

		final EditText etGrade = (EditText) findViewById(R.id.activity_task_et_grade);
		etGrade.setText(String.valueOf(task.getGrade()));
		
		final Spinner spDiscipline = (Spinner) findViewById(R.id.activity_task_sp_discipline_select);
		final Spinner spPriority = (Spinner) findViewById(R.id.activity_task_sp_priority);
		
		final Button btSave = (Button) findViewById(R.id.activity_task_bt_save_task);
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				task.setTitle(etTitle.getText().toString());
				task.setDescription(etDescription.getText().toString());
				task.setDeadlineDate(createCalendar(etDeadLineDate.getText().toString()));
				task.setPriority(getPriority(spPriority.getSelectedItem().toString())); 
				task.setDiscipline((Discipline) spDiscipline.getSelectedItem());
				task.setGrade(Float.parseFloat(etGrade.getText().toString()));
				int rowsAffected = tPersister.update(task);
				Log.i(CLASS_TAG, rowsAffected + " campos foram alterados em " + task.getTitle());
				finish();
			}
		});
	}
	
	private Calendar createCalendar(String string) {
		String pattern = "[0-9]{2,2}/[0-9]{2,2}/[0-9]{4,4}\\s[0-9]{2,2}:[0-9]{2,2}";
		Calendar newCalendar = Calendar.getInstance();
		
		if(string.matches(pattern)){
			newCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(string.substring(0, 2)));
			newCalendar.set(Calendar.MONTH, Integer.parseInt(string.substring(3, 5)));
			newCalendar.set(Calendar.YEAR, Integer.parseInt(string.substring(6, 10)));
			newCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(string.substring(11, 13)));
			newCalendar.set(Calendar.MINUTE, Integer.parseInt(string.substring(14, 16)));
			newCalendar.set(Calendar.SECOND, 00);
		}
		
		return newCalendar;
	}
	
	private Priority getPriority(String selectedPriority) {
		Priority newPriority = Priority.NORMAL;
		
		if(selectedPriority.equalsIgnoreCase("alta")){
			newPriority = Priority.HIGH;
		} else if (selectedPriority.equalsIgnoreCase("baixa")) {
			newPriority = Priority.LOW;
		}
		
		return newPriority;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			int year = deadDateCalendar.get(Calendar.YEAR);
			int month = deadDateCalendar.get(Calendar.MONTH);
			int day = deadDateCalendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(this, datePickerListener, year, month, day);
			
		case TIME_DIALOG_ID:
			int hour = deadDateCalendar.get(Calendar.HOUR_OF_DAY);
			int minute = deadDateCalendar.get(Calendar.MINUTE);
			return new TimePickerDialog(this, timePickerListener, hour, minute, true);
			
		default: 
			return null;
		}
	}
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			deadDateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			deadDateCalendar.set(Calendar.MINUTE, minuteOfHour);
			etDeadLineDate.setText(formatter.format(deadDateCalendar.getTime()));
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
			deadDateCalendar.set(Calendar.YEAR, selectedYear);
			deadDateCalendar.set(Calendar.MONTH, selectedMonth);
			deadDateCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
			etDeadLineDate.setText(formatter.format(deadDateCalendar.getTime()));
			
			showDialog(TIME_DIALOG_ID);
		}
	};

	private void povoateDiscSpinner(Task task) {
		Spinner spDisciplines = (Spinner) findViewById(R.id.activity_task_sp_discipline_select);
        List<Discipline> disciplines = dPersister.retrieveAll();
        spDisciplines.setAdapter(new DisciplineAdapter(this, disciplines));
        spDisciplines.setSelection(0);		//FIXME
	}

}
