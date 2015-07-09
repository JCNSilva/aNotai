package es.database.anotai;

import static es.database.anotai.AnotaiDbHelper.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.model.anotai.Classmate;
import es.model.anotai.Exam;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task;
import es.model.anotai.Task.Priority;

public class TaskPersister implements AbstractPersister<Task> {

	private SQLiteDatabase db;
	
	public TaskPersister(Context context){
		AnotaiDbHelper helper = new AnotaiDbHelper(context);
		db = helper.getWritableDatabase();
	}
	
	
	
	@Override
	public void create(Task target) {
		ContentValues values = new ContentValues();
		
		values.put(TASKENTRY_COLUMN_DESCRIPTION, target.getDescription());
		values.put(TASKENTRY_COLUMN_CADASTER_DATE, target.getCadasterDateText());
		values.put(TASKENTRY_COLUMN_DEADLINE_DATE, target.getDeadlineDateText());
		values.put(TASKENTRY_COLUMN_PRIORITY, target.getPriorityText());
		values.put(TASKENTRY_COLUMN_GRADE, target.getGrade());
		values.put(TASKENTRY_COLUMN_ID_DISCIPLINE, 0); //FIXME pegar id correta
		
		if (target instanceof Exam) {
			values.put(TASKENTRY_COLUMN_SUBCLASS, 
					Exam.class.getSimpleName().toLowerCase(Locale.US));
		} else if (target instanceof IndividualHomework) {
			values.put(TASKENTRY_COLUMN_SUBCLASS, 
					IndividualHomework.class.getSimpleName().toLowerCase(Locale.US));
		} else {
			values.put(TASKENTRY_COLUMN_SUBCLASS,
					GroupHomework.class.getSimpleName().toLowerCase(Locale.US));
			
			GroupHomework gHomework = (GroupHomework) target;
			for(Classmate classmate: gHomework.getGroup()){
				
				ContentValues valuesClassmates = new ContentValues();
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID_TASK, 0); //FIXME pegar id correta
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_NAME, classmate.getName());
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID, 0); //FIXME pegar id correta
				
				for(String number: classmate.getPhoneNumbers()){
					ContentValues valuesNumbers = new ContentValues();
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID_CLASSMATE, 0); //FIXME pegar id correta
					valuesNumbers.put(PHONENUMBERS_COLUMN_PHONE_NUMBER, number);
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID, 0); //FIXME pegar id correta
					db.insert(PHONENUMBERS_TABLE_NAME, null, valuesNumbers);
				}
				
				db.insert(CLASSMATEENTRY_TABLE_NAME, null, valuesClassmates);
			}
		}
		
		db.insert(TASKENTRY_TABLE_NAME, null, values);	
	}
	
	

	@Override
	public void update(Task target) {
		ContentValues values = new ContentValues();
		
		values.put(TASKENTRY_COLUMN_DESCRIPTION, target.getDescription());
		values.put(TASKENTRY_COLUMN_CADASTER_DATE, target.getCadasterDateText());
		values.put(TASKENTRY_COLUMN_DEADLINE_DATE, target.getDeadlineDateText());
		values.put(TASKENTRY_COLUMN_PRIORITY, target.getPriorityText());
		values.put(TASKENTRY_COLUMN_GRADE, target.getGrade());
		values.put(TASKENTRY_COLUMN_ID_DISCIPLINE, 0); //FIXME pegar id correta
		
		if (target instanceof Exam) {
			values.put(TASKENTRY_COLUMN_SUBCLASS, 
					Exam.class.getSimpleName().toLowerCase(Locale.US));
		} else if (target instanceof IndividualHomework) {
			values.put(TASKENTRY_COLUMN_SUBCLASS, 
					IndividualHomework.class.getSimpleName().toLowerCase(Locale.US));
		} else {
			values.put(TASKENTRY_COLUMN_SUBCLASS,
					GroupHomework.class.getSimpleName().toLowerCase(Locale.US));
			
			GroupHomework gHomework = (GroupHomework) target;
			for(Classmate classmate: gHomework.getGroup()){
				
				ContentValues valuesClassmates = new ContentValues();
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID_TASK, 0); //FIXME pegar id correta
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_NAME, classmate.getName());
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID, 0); //FIXME pegar id correta
				
				for(String number: classmate.getPhoneNumbers()){
					ContentValues valuesNumbers = new ContentValues();
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID_CLASSMATE, 0); //FIXME pegar id correta
					valuesNumbers.put(PHONENUMBERS_COLUMN_PHONE_NUMBER, number);
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID, 0); //FIXME pegar id correta
					db.insert(PHONENUMBERS_TABLE_NAME, null, valuesNumbers);
				}
				
				db.insert(CLASSMATEENTRY_TABLE_NAME, null, valuesClassmates);
			}
		}
		
		db.insert(TASKENTRY_TABLE_NAME, null, values);
		
	}
	
	

	@Override
	public void delete(Task target) {
		db.delete(TASKENTRY_TABLE_NAME, "_id = " + target.getId(), null);
	}
	
	
	

	@Override
	public Task retrieveById(long id) {
		Task target = null;
		String[] columns = {
				TASKENTRY_COLUMN_SUBCLASS,
				TASKENTRY_COLUMN_ID,
				TASKENTRY_COLUMN_DESCRIPTION,
				TASKENTRY_COLUMN_CADASTER_DATE,
				TASKENTRY_COLUMN_DEADLINE_DATE,
				TASKENTRY_COLUMN_PRIORITY,
				TASKENTRY_COLUMN_GRADE				
		};
		
		Cursor cursorTask = db.query(TASKENTRY_TABLE_NAME, columns, "_id = " + id, null, null, null, null);
		
		if(cursorTask.getCount() > 0){
			cursorTask.moveToFirst();
			
			String type = cursorTask.getString(0);
			
			if(type.equals("exam")) {
				target = new Exam();
			} else if(type.equals("grouphomework")) {
				target = new GroupHomework();
			} else if (type.equals("individualhomework")) {
				target = new IndividualHomework();
			}
				
			target.setId(cursorTask.getLong(1));
			target.setDescription(cursorTask.getString(2));
			target.setCadasterDate(createCalendar(cursorTask.getString(3)));
			target.setDeadlineDate(createCalendar(cursorTask.getString(4)));
			target.setPriority(retrievePriority(cursorTask.getString(5)));
			target.setGrade(cursorTask.getDouble(6));
		}
		
		//Caso a Tarefa seja um trabalho em grupo, é preciso adicionar os membros do grupo
		if(target instanceof GroupHomework){
			GroupHomework myHomework = (GroupHomework) target;
			
			/*Cursor cursorMembers = db.query(CLASSMATEENTRY_TABLE_NAME, columns, "_id = " + id, null, null, null, null);
			
			if(cursorTask.getCount() > 0){
				cursorTask.moveToFirst();
			}*/
			
			target = myHomework;
			
		}
		
		return target;
	}
	
	

	@Override
	public List<Task> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	/**Cria Calendar a partir de String. Atenção: A String deve ter a forma dd/MM/yyyy
	 * 
	 * @param dateFormat
	 * @return
	 */
	private Calendar createCalendar(String dateFormat){
		if(dateFormat == null){
			throw new IllegalArgumentException("Null String can not be converted");
		}
		if(!dateFormat.matches("\\d{2}/[0&&[1-9]][1&&[0-2]]/\\d{4}")){ //FIXME representação no bd ignora e exclui zeros a esquerda
			throw new IllegalArgumentException("String does not match expected format");
		}
		
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateFormat.substring(0, 2)));
		myCalendar.set(Calendar.MONTH, Integer.parseInt(dateFormat.substring(3, 5)) - 1);
		myCalendar.set(Calendar.YEAR, Integer.parseInt(dateFormat.substring(6)));
		
		return myCalendar;
	}
		
		
		
	private Priority retrievePriority(String priority) {
		if(priority == null){
			throw new IllegalArgumentException("Null String can not be converted");
		}
		
		Priority newPriority = null;
		
		if(priority.equals("HIGH")){
			newPriority = Priority.HIGH;
		} else if(priority.equals("NORMAL")){
			newPriority = Priority.NORMAL;
		} else if(priority.equals("LOW")){
			newPriority = Priority.LOW;
		}
		
		return newPriority;
	}

}
