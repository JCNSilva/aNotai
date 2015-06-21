package es.view.anotai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import projeto.es.view.anotai.R;
import projeto.es.view.anotai.R.id;
import projeto.es.view.anotai.R.layout;
import projeto.es.view.anotai.R.menu;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class ExamsActivity extends Activity {
	
	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private int day, month, year, hour, minute;
	private EditText deadDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exams);
		
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
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, minute, true);
		}
		return null;
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

		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exams, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void povoateDiscSpinner() {
		Spinner spDisciplines = (Spinner) findViewById(R.id.sp_discipline_select);
        
        String[] array_disciplines = {
        		"Português", "Matemática", "História", "Geografia",
        		"Física", "Química", "Biologia", "Ed. Física",
        		"Inglês", "Espanhol", "Sociologia", "Filosofia"
        };
        
        List<String> disciplines = new ArrayList<String>(Arrays.asList(array_disciplines));
        
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, disciplines);  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spDisciplines.setAdapter(dataAdapter);
	}

}
