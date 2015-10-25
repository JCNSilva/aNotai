package es.view.anotai;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import projeto.es.view.anotai.R;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import es.adapter.anotai.ClassmateAdapter;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.database.anotai.TaskPersister;
import es.model.anotai.Classmate;
import es.model.anotai.Discipline;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task.Priority;
import es.utils.anotai.NotificationUtils;

//TODO Refatorar
public class HomeworkActivity extends Activity {
	
	private static final String CLASS_TAG = "HomeworkActivity";
	private static final int DATE_DIALOG_ID = 100;
	private static final int TIME_DIALOG_ID = 101;
	private static final int PICK_CONTACT = 1;
	private int day, month, year, hour, minute;
	private EditText deadDate;
	private TaskPersister tPersister;
	private DisciplinePersister dPersister;
	private List<Classmate> classmateList = new ArrayList<Classmate>();
    private Calendar deadDateCalendar = Calendar.getInstance();
	

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
            	String description = homeWorkDescription.getText().toString();
            	
				if (calExam.after(calendar)  && !description.isEmpty()) {
					NotificationUtils.createNotifications(calExam, HomeworkActivity.this, description);
					Log.i("ExamsActivity", "Notificação configurada");
					
					final EditText titleET = (EditText) findViewById(R.id.activity_homework_et_name_homework);
					final String title = titleET.getText().toString();
					final CheckBox isGroupHomework = (CheckBox) findViewById(R.id.activity_homework_check_group);
					final Spinner dSelect = (Spinner) findViewById(R.id.activity_homework_sp_discipline_select);
					final Discipline dSelected = (Discipline) dSelect.getSelectedItem();
					final Spinner prioritySelect = (Spinner) findViewById(R.id.activity_homework_sp_priority);
					final String prioritySelected = (String) prioritySelect.getSelectedItem();
					
					if(isGroupHomework.isChecked()){
						List<Classmate> mates = new ArrayList<Classmate>();
						GroupHomework gHomework = 	new GroupHomework(title, dSelected, description, calExam,
													getPriority(prioritySelected), mates);
						tPersister.create(gHomework);
						Log.i("ExamsActivity", "Novo trabalho em grupo salvo");
					} else {
						tPersister.create(new IndividualHomework(title, dSelected, description, calExam, getPriority(prioritySelected)));
						Log.i("ExamsActivity", "Novo trabalho individual salvo");
					}
					
					startTasksActivity();
					finish();
					
				} else {
					Toast.makeText(HomeworkActivity.this, getResources().getString(R.string.error_message),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void startTasksActivity() {
		Intent i = new Intent();
		i.setClass(HomeworkActivity.this, TasksActivity.class);
		startActivity(i);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case PICK_CONTACT:
			if(resultCode == RESULT_OK){
				
				Uri contactUri = data.getData();
	            String[] projection = {Phone.DISPLAY_NAME, Phone.NUMBER};

	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(column);
	            int columnDN = cursor.getColumnIndex(Phone.DISPLAY_NAME);
	            String name = cursor.getString(columnDN);
	            
	            Log.d(CLASS_TAG, "nome: " + name);
	            Log.d(CLASS_TAG, "numero: " + number);
	            

	        	List<String> numbers = new ArrayList<String>();
	            numbers.add(number);
	            Classmate newClassmate = new Classmate(name, numbers); //FIXME receber mais de um telefone
	            classmateList.add(newClassmate);
	            
			}
			
			ListView classmates = (ListView) findViewById(R.id.activity_homework_lv_classmate);
            ClassmateAdapter cAdapter = new ClassmateAdapter(this, classmateList);
            classmates.setAdapter(cAdapter);
		}
	}

	private void setClickListenerDeadlineDate(Calendar calendar) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		
		deadDate = (EditText) findViewById(R.id.activity_homework_et_deadline_date);
		deadDate.setText(formatter.format(calendar.getTime()));
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
