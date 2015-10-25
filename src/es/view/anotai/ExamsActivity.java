package es.view.anotai;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.Task.Priority;
import es.utils.anotai.NotificationUtils;
import projeto.es.view.anotai.R;

//TODO Refatorar
public class ExamsActivity extends Activity {

	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private EditText deadDate, examDescription;
	private TaskPersister tPersister;
	private DisciplinePersister dPersister;
	private Calendar deadDateCalendar = Calendar.getInstance();
	private SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_exams);

		tPersister = new TaskPersister(this);
		dPersister = new DisciplinePersister(this);

		povoateDiscSpinner();

		deadDate = (EditText) findViewById(R.id.activity_exams_et_deadline_date);
		deadDate.setInputType(InputType.TYPE_NULL);
		deadDate.setText(formatter.format(deadDateCalendar.getTime()));
		deadDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		examDescription = (EditText) findViewById(R.id.activity_exams_et_exam_description);

		Button btSaveExam = (Button) findViewById(R.id.activity_exams_bt_create_exam);
		btSaveExam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String description = examDescription.getText().toString();
				Calendar calendar = Calendar.getInstance();

				if (deadDateCalendar.after(calendar) && !description.isEmpty()) {
					NotificationUtils.createNotifications(deadDateCalendar,
							ExamsActivity.this, description);
					Log.i("ExamsActivity", "Notificação configurada");

					Spinner dSelect = (Spinner) findViewById(R.id.activity_exams_sp_discipline_select);
					Discipline dSelected = (Discipline) dSelect.getSelectedItem();

					Spinner prioritySelect = (Spinner) findViewById(R.id.activity_exams_sp_priority);
					String selectedPriority = (String) prioritySelect.getSelectedItem();
					Priority newPriority = getPriority(selectedPriority);

					EditText titleET = (EditText) findViewById(R.id.activity_exams_et_title);
					String title = titleET.getText().toString();

					tPersister.create(new Exam(title, dSelected, description,
							deadDateCalendar, newPriority));
					Log.i("ExamsActivity", "Prova salva");
					startTasksActivity();
					finish();

				} else {
					Toast.makeText(ExamsActivity.this,
							getResources().getString(R.string.error_message),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private Priority getPriority(String selectedPriority) {
		Priority newPriority = Priority.NORMAL;

		if (selectedPriority.equalsIgnoreCase("alta")) {
			newPriority = Priority.HIGH;
		} else if (selectedPriority.equalsIgnoreCase("baixa")) {
			newPriority = Priority.LOW;
		}

		return newPriority;
	}

	private void startTasksActivity() {
		Intent i = new Intent();
		i.setClass(ExamsActivity.this, TasksActivity.class);
		startActivity(i);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			int year = deadDateCalendar.get(Calendar.YEAR);
			int month = deadDateCalendar.get(Calendar.MONTH);
			int day = deadDateCalendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		case TIME_DIALOG_ID:
			int hour = deadDateCalendar.get(Calendar.HOUR_OF_DAY);
			int minute = deadDateCalendar.get(Calendar.MINUTE);
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					true);
		default:
			return null;
		}

	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			deadDateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			deadDateCalendar.set(Calendar.MINUTE, minuteOfHour);
			deadDate.setText(formatter.format(deadDateCalendar.getTime()));
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			deadDateCalendar.set(Calendar.YEAR, selectedYear);
			deadDateCalendar.set(Calendar.MONTH, selectedMonth);
			deadDateCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
			deadDate.setText(formatter.format(deadDateCalendar.getTime()));

			showDialog(TIME_DIALOG_ID);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.exams, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;

		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void povoateDiscSpinner() {
		Spinner spDisciplines = (Spinner) findViewById(R.id.activity_exams_sp_discipline_select);
		List<Discipline> disciplines = dPersister.retrieveAll();
		spDisciplines.setAdapter(new DisciplineAdapter(this, disciplines));
	}
}
