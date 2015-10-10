package es.view.anotai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task.Priority;
import es.utils.anotai.NotificationUtils;
import projeto.es.view.anotai.R;

public class HomeworkActivity extends Activity {
	
	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private static final int PICK_CONTACT = 1;
	private int day, month, year, hour, minute;
	private EditText deadDate;
	private TaskPersister tPersister;
	private DisciplinePersister dPersister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		tPersister = new TaskPersister(this);
		dPersister = new DisciplinePersister(this);
		
		povoateDiscSpinner();
		
		final Calendar calendar = Calendar.getInstance();
		
		setClickListenerDeadlineDate(calendar);
		
		Button btAddClassmate = (Button) findViewById(R.id.activity_homework_bt_add_classmate);
		btAddClassmate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent pickContact = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				pickContact.setType(Phone.CONTENT_TYPE);
				startActivityForResult(pickContact, PICK_CONTACT);				
			}
		});
		
		final EditText homeWorkDescription = (EditText) findViewById(R.id.activity_homework_et_homework_description);
		
		Button btSave = (Button) findViewById(R.id.activity_homework_bt_create_homework);
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Calendar calExam = Calendar.getInstance();
            	calExam.set(year, month, day, hour, minute);
            	
				if (calExam.after(calendar)) {
					String description = homeWorkDescription.getText().toString();
					NotificationUtils.createNotifications(calExam, HomeworkActivity.this, description);
					Log.i("ExamsActivity", "Notificação configurada");
					
					final CheckBox isGroupHomework = (CheckBox) findViewById(R.id.activity_homework_check_group);
					final Spinner dSelect = (Spinner) findViewById(R.id.activity_homework_sp_discipline_select);
					final Discipline dSelected = (Discipline) dSelect.getSelectedItem();
					
					if(isGroupHomework.isChecked()){
						tPersister.create(new GroupHomework("", dSelected, description, calExam, Priority.NORMAL)); //FIXME titulo e prioridade
						Log.i("ExamsActivity", "Novo trabalho em grupo salvo");
					} else {
						tPersister.create(new IndividualHomework("", dSelected, description, calExam, Priority.NORMAL)); //FIXME titulo e prioridade
						Log.i("ExamsActivity", "Novo trabalho individual salvo");
					}
					
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case PICK_CONTACT:
			if(resultCode == RESULT_OK){
				// Get the URI that points to the selected contact
	            Uri contactUri = data.getData();
	            // We only need the NUMBER column, because there will be only one row in the result
	            String[] projection = {Phone.DISPLAY_NAME, Phone.NUMBER};

	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            // Retrieve the phone number from the NUMBER column
	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(column);
	            
	         // Retrieve the phone number from the DISPLAY_NAME column
	            int columnDN = cursor.getColumnIndex(Phone.DISPLAY_NAME);
	            String name = cursor.getString(columnDN);
	            Log.d("HomeworkActivity", "nome: " + name);
	            Log.d("HomeworkActivity", "numero: " + number);
	            
	            
			}
		}
	}

	private void setClickListenerDeadlineDate(Calendar calendar) {
		deadDate = (EditText) findViewById(R.id.activity_homework_et_deadline_date);
			
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
	
	private void povoateDiscSpinner() {
		Spinner spDisciplines = (Spinner) findViewById(R.id.activity_homework_sp_discipline_select);
        List<Discipline> disciplines = dPersister.retrieveAll();
		spDisciplines.setAdapter(new DisciplineAdapter(this, disciplines));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homework, menu);
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
}
