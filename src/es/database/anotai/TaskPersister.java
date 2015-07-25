package es.database.anotai;

import static es.database.anotai.AnotaiDbHelper.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.model.anotai.Classmate;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task;
import es.model.anotai.Task.Priority;

public class TaskPersister implements AbstractPersister<Task>{

	private SQLiteDatabase db;
	
	public TaskPersister(Context context){
		AnotaiDbHelper helper = new AnotaiDbHelper(context);
		db = helper.getWritableDatabase();
		db.execSQL("PRAGMA foreign_keys = ON"); //Activate support on foreign keys constraints);
	}
	
	
	
	public void create(Task newTask) {
		if(newTask == null){
			return;
		}
		
		//Adicionamos as informações da disciplina relacionada à tarefa
		ContentValues valuesDiscipline = new ContentValues();
		valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_NAME, newTask.getDiscipline().getName());
		valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_TEACHER, newTask.getDiscipline().getTeacher());
		
		//Caso a disciplina já exista 
		int rowsAffected = db.update(DISCIPLINEENTRY_TABLE_NAME, valuesDiscipline, "_id = " + newTask.getDiscipline().getId(), null);
		long idNewDiscipline = newTask.getDiscipline().getId(); 
		
		//Caso a disciplina ainda não exista
		if(rowsAffected == 0) {
			idNewDiscipline = db.insert(DISCIPLINEENTRY_TABLE_NAME, null, valuesDiscipline); 
			newTask.getDiscipline().setId(idNewDiscipline);
		}
		
		ContentValues valuesTask = new ContentValues();
		valuesTask.put(TASKENTRY_COLUMN_DESCRIPTION, newTask.getDescription());
		valuesTask.put(TASKENTRY_COLUMN_CADASTER_DATE, newTask.getCadasterDateMillis());
		valuesTask.put(TASKENTRY_COLUMN_DEADLINE_DATE, newTask.getDeadlineDateMillis());
		valuesTask.put(TASKENTRY_COLUMN_PRIORITY, newTask.getPriorityText());
		valuesTask.put(TASKENTRY_COLUMN_GRADE, newTask.getGrade());
		valuesTask.put(TASKENTRY_COLUMN_ID_DISCIPLINE, idNewDiscipline);
		
		if (newTask instanceof Exam) {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS, 
					Exam.class.getSimpleName().toLowerCase(Locale.US));
		} else if (newTask instanceof IndividualHomework) {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS, 
					IndividualHomework.class.getSimpleName().toLowerCase(Locale.US));
		} else {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS,
					GroupHomework.class.getSimpleName().toLowerCase(Locale.US));
		}
		
		long idNewTask = db.insert(TASKENTRY_TABLE_NAME, null, valuesTask);
		newTask.setId(idNewTask);
		
		
		
		//Se o trabalho for em grupo, precisamos adicionar os membros do grupo e seus telefones
		if (newTask instanceof GroupHomework){
			GroupHomework gHomework = (GroupHomework) newTask;
			for(Classmate classmate: gHomework.getGroup()){
				
				ContentValues valuesClassmates = new ContentValues();
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID_TASK, idNewTask);
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_NAME, classmate.getName());
				long idNewClassmate = db.insert(CLASSMATEENTRY_TABLE_NAME, null, valuesClassmates);
				classmate.setId(idNewClassmate);
				
				for(String number: classmate.getPhoneNumbers()){
					ContentValues valuesNumbers = new ContentValues();
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID_CLASSMATE, idNewClassmate);
					valuesNumbers.put(PHONENUMBERS_COLUMN_PHONE_NUMBER, number);
					db.insert(PHONENUMBERS_TABLE_NAME, null, valuesNumbers);
				}
			}
		}
		
		
	}
	
	

	public int update(Task taskUpdated) {
		if(taskUpdated == null){
			return 0;
		}
		
		ContentValues valuesTask = new ContentValues();
		valuesTask.put(TASKENTRY_COLUMN_DESCRIPTION, taskUpdated.getDescription());
		valuesTask.put(TASKENTRY_COLUMN_CADASTER_DATE, taskUpdated.getCadasterDateMillis());
		valuesTask.put(TASKENTRY_COLUMN_DEADLINE_DATE, taskUpdated.getDeadlineDateMillis());
		valuesTask.put(TASKENTRY_COLUMN_PRIORITY, taskUpdated.getPriorityText());
		valuesTask.put(TASKENTRY_COLUMN_GRADE, taskUpdated.getGrade());
		
		if (taskUpdated instanceof Exam) {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS, 
					Exam.class.getSimpleName().toLowerCase(Locale.US));
		} else if (taskUpdated instanceof IndividualHomework) {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS, 
					IndividualHomework.class.getSimpleName().toLowerCase(Locale.US));
		} else {
			valuesTask.put(TASKENTRY_COLUMN_SUBCLASS,
					GroupHomework.class.getSimpleName().toLowerCase(Locale.US));
		}
		
		int rowsAffected = db.update(TASKENTRY_TABLE_NAME, valuesTask, "_id = " + taskUpdated.getId(), null);
		
		//Adicionamos as informações da disciplina relacionada à tarefa
		ContentValues valuesDiscipline = new ContentValues();
		valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_NAME, taskUpdated.getDiscipline().getName());
		valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_TEACHER, taskUpdated.getDiscipline().getTeacher());
		
		//Tenta dar update na disciplina. Se ela não existir no banco de dados, persiste a mesma.
		int discUpdated = db.update(DISCIPLINEENTRY_TABLE_NAME, valuesDiscipline, "_id = " + taskUpdated.getDiscipline().getId(), null);
		if(discUpdated == 0){
			long idnewDisc = db.insert(DISCIPLINEENTRY_TABLE_NAME, null, valuesDiscipline);
			taskUpdated.getDiscipline().setId(idnewDisc);
		}
		
		
		//Se o trabalho for em grupo, precisamos adicionar os membros do grupo e seus telefones
		if (taskUpdated instanceof GroupHomework){
			GroupHomework gHomework = (GroupHomework) taskUpdated;
			for(Classmate classmate: gHomework.getGroup()){
				
				ContentValues valuesClassmates = new ContentValues();
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_ID_TASK, taskUpdated.getId());
				valuesClassmates.put(CLASSMATEENTRY_COLUMN_NAME, classmate.getName());
				db.update(CLASSMATEENTRY_TABLE_NAME, valuesClassmates, "id_homework = " + taskUpdated.getId(), null);
				
				for(String number: classmate.getPhoneNumbers()){
					ContentValues valuesNumbers = new ContentValues();
					valuesNumbers.put(PHONENUMBERS_COLUMN_ID_CLASSMATE, classmate.getId());
					valuesNumbers.put(PHONENUMBERS_COLUMN_PHONE_NUMBER, number);
					db.update(PHONENUMBERS_TABLE_NAME, valuesNumbers, "id_classmate = " + classmate.getId(), null);
				}
			}
		}
		
		return rowsAffected;
	}
	
	

	public void delete(Task target) {
		if(target != null){
			db.delete(TASKENTRY_TABLE_NAME, "_id = " + target.getId(), null);
		}
	}
	
	
	
	public Task retrieveById(long idTask) {
		Task target = null;
		String[] columns = {
				TASKENTRY_COLUMN_SUBCLASS,
				TASKENTRY_COLUMN_ID,
				TASKENTRY_COLUMN_DESCRIPTION,
				TASKENTRY_COLUMN_CADASTER_DATE,
				TASKENTRY_COLUMN_DEADLINE_DATE,
				TASKENTRY_COLUMN_PRIORITY,
				TASKENTRY_COLUMN_GRADE,	
				TASKENTRY_COLUMN_ID_DISCIPLINE
		};
		
		Cursor cursorTask = db.query(TASKENTRY_TABLE_NAME, columns, 
				"_id = " + idTask, null, null, null, null);
		
		if(cursorTask.getCount() < 0){
			return null;
		}
	
		cursorTask.moveToFirst();
		
		//Decide qual o tipo de tarefa a ser criada
		String type = cursorTask.getString(0);
		if(type.equals("exam")) {
			target = new Exam();
		} else if(type.equals("grouphomework")) {
			target = new GroupHomework();
		} else if (type.equals("individualhomework")) {
			target = new IndividualHomework();
		} else {
			throw new RuntimeException("Undefined type on database");
		}
			
		//Adiciona informações da tarefa
		target.setId(cursorTask.getLong(1));
		target.setDescription(cursorTask.getString(2));
		
		GregorianCalendar cadaster = new GregorianCalendar();
		cadaster.setTimeInMillis(cursorTask.getLong(3));
		target.setCadasterDate(cadaster);
		
		GregorianCalendar deadline = new GregorianCalendar();
		deadline.setTimeInMillis(cursorTask.getLong(4));
		target.setDeadlineDate(deadline);
		
		target.setPriority(retrievePriority(cursorTask.getString(5)));
		target.setGrade(cursorTask.getDouble(6));
		
		//Adiciona as informações referentes à Disciplina
		long idDiscipline = cursorTask.getLong(7);
		Discipline disc = new Discipline();
		String[] columnsDiscipline = {
				DISCIPLINEENTRY_COLUMN_ID,
				DISCIPLINEENTRY_COLUMN_NAME,
				DISCIPLINEENTRY_COLUMN_TEACHER
		};
		
		Cursor cursorDiscipline = db.query(DISCIPLINEENTRY_TABLE_NAME, columnsDiscipline, 
				"_id = " + idDiscipline, null, null, null, null);
		
		if(cursorDiscipline.getCount() > 0){
			cursorDiscipline.moveToFirst();
			disc.setId(cursorDiscipline.getLong(0));
			disc.setName(cursorDiscipline.getString(1));
			disc.setTeacher(cursorDiscipline.getString(2));
		}
		
		target.setDiscipline(disc);		
		
		//Caso a Tarefa seja um trabalho em grupo, adiciona os membros do grupo
		if(target instanceof GroupHomework){
			GroupHomework myHomework = (GroupHomework) target;
			String[] columnsClassmate = {
					CLASSMATEENTRY_COLUMN_ID,
					CLASSMATEENTRY_COLUMN_NAME
			};
			
			Cursor cursorMembers = db.query(CLASSMATEENTRY_TABLE_NAME, columnsClassmate, 
					"id_homework = " + idTask, null, null, null, null);
			
			if(cursorMembers.getCount() > 0){
				cursorMembers.moveToFirst();
				
				 do{
					 
					 Classmate mate = new Classmate();
					 long idMate = cursorMembers.getLong(0);
					 mate.setId(idMate);
					 mate.setName(cursorMembers.getString(1));
					 
					 //Pega os telefones de cada membro
					 String[] columnsPhone = { PHONENUMBERS_COLUMN_PHONE_NUMBER };
					 Cursor cursorPhones = db.query(PHONENUMBERS_TABLE_NAME, columnsPhone, 
							 "id_classmate = " + idMate, null, null, null, null);
					 
					 if(cursorPhones.getCount() > 0){
						 cursorPhones.moveToFirst();
						 
						 do {
							 mate.addPhoneNumber(cursorPhones.getString(0));							 							 
						 } while(cursorPhones.moveToNext());
					 }
					 
					 myHomework.addToGroup(mate);
				 } while(cursorMembers.moveToNext());
			}			
		}
		
		return target;
	}
	
	
	public List<Task> retrieveAll() {
		return retrieveAll(null);		
	}
	
	
	public List<Task> retrieveAll(Discipline discipline) {
		Cursor cursorTasks = null;
		if(discipline == null){
			cursorTasks = db.query(TASKENTRY_TABLE_NAME, new String[]{TASKENTRY_COLUMN_ID},
					null, null, null, null, null);
		} else {
			cursorTasks = db.query(TASKENTRY_TABLE_NAME, new String[]{TASKENTRY_COLUMN_ID},
					"id_discipline = " + discipline.getId(), null, null, null, null);
		}
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		if(cursorTasks.getCount() > 0){
			cursorTasks.moveToFirst();
			do {
				Task taskAtual = retrieveById(cursorTasks.getLong(0));
				tasks.add(taskAtual);
			} while(cursorTasks.moveToNext());
		}
		
		return tasks;
	}
	
	public void close() throws Exception {
		if(db == null || !db.isOpen()) {
			throw new Exception("Database is not present or open");
		}
		
		db.close();		
	}		
		
	private Priority retrievePriority(String priority) {
		if(priority == null){
			throw new IllegalArgumentException("Null String can not be converted");
		}
		
		Priority newPriority = null;
		
		if(priority.equalsIgnoreCase("HIGH")){
			newPriority = Priority.HIGH;
		} else if(priority.equalsIgnoreCase("NORMAL")){
			newPriority = Priority.NORMAL;
		} else if(priority.equalsIgnoreCase("LOW")){
			newPriority = Priority.LOW;
		}
		
		return newPriority;
	}
}
