package es.view.anotai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
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
import es.model.anotai.Task.Priority;
import es.utils.anotai.NotificationUtils;


public class ExamsActivity extends Activity {
	
	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private int day, month, year, hour, minute;
	private EditText deadDate, examDescription;
	private TaskPersister tPersister;
	private DisciplinePersister dPersister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_exams);
		
		tPersister = new TaskPersister(this);
		dPersister = new DisciplinePersister(this);
		
		povoateDiscSpinner();
		
		deadDate = (EditText) findViewById(R.id.et_deadline_date);
		final Calendar calendar = Calendar.getInstance();
		
		day = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		
		deadDate.setText(new StringBuilder()
				.append(day).append("/")
				.append(month + 1).append("/")
				.append(year).append(" ")
				.append(hour).append(":")
				.append(minute));
		
		deadDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);				
			}
		});
		
		examDescription = (EditText) findViewById(R.id.et_exam_description);
		
		Button btSaveExam = (Button) findViewById(R.id.bt_create_exam);
		btSaveExam.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) { 
            	Calendar calExam = Calendar.getInstance();
            	calExam.set(year, month, day, hour, minute);
            	
//            	Log.v("SOBRE OS CALENDARIOS", "atributo calendar: " + "dia: " + calExam.get(Calendar.DAY_OF_MONTH)
//            	+ " mês: " + calExam.get(Calendar.MONTH) + " year: " + calExam.get(Calendar.YEAR) 
//            	+ " hora: " + calExam.get(Calendar.HOUR) + " minuto: " + calExam.get(Calendar.MINUTE));
            	
				if (calExam.after(calendar)) {
					String description = examDescription.getText().toString();
					NotificationUtils.createNotifications(calExam, ExamsActivity.this, description);
					Log.i("ExamsActivity", "Notificação configurada"); 
					
					//Recupera disciplina selecionada no Spinner
					Spinner dSelect = (Spinner) findViewById(R.id.sp_discipline_select);
					Discipline dSelected = (Discipline) dSelect.getSelectedItem();
					tPersister.create(new Exam(dSelected, description, calExam, Priority.NORMAL)); //FIXME
					Log.i("ExamsActivity", "Prova salva");

				}
            }
        });
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, minute, true);
		default:
			return null;
		}
		
	}
	
	//cria listener para o timePicker
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			hour = hourOfDay;
			minute = minuteOfHour;

			deadDate.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/")
					.append(year).append(" ")
					.append(hour).append(":")
					.append(minute));
		}
	};

	//cria listener para o datePicker
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			deadDate.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/")
					.append(year).append(" ")
					.append(hour).append(":")
					.append(minute));
			
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
		Spinner spDisciplines = (Spinner) findViewById(R.id.sp_discipline_select);
        List<Discipline> disciplines = dPersister.retrieveAll();
        spDisciplines.setAdapter(new DisciplineAdapter(this, disciplines));
	}
}
