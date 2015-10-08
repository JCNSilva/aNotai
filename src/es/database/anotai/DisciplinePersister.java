package es.database.anotai;

import static es.database.anotai.AnotaiDbHelper.*;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.model.anotai.Discipline;

public class DisciplinePersister implements AbstractPersister<Discipline> {
	
	private SQLiteDatabase db;
	
	private static final String TABLE_NAME = "disciplineTable";
	
	public DisciplinePersister(Context context){
		AnotaiDbHelper helper = new AnotaiDbHelper(context);
		db = helper.getWritableDatabase();
		db.execSQL("PRAGMA foreign_keys = ON"); //Ativa o suporte a restrições de chaves estrangeiras
	}

	@Override
	public void create(Discipline target) {
		if(target != null) {
			ContentValues values = new ContentValues();
			values.put(DISCIPLINEENTRY_COLUMN_NAME, target.getName());
			values.put(DISCIPLINEENTRY_COLUMN_TEACHER, target.getTeacher());
			
			long lastId = db.insert(TABLE_NAME, null, values);
			target.setId(lastId);
		}
	}

	@Override
	public int update(Discipline target) {
		int disciplinesUpdated = 0;
		
		if(target != null) {
			ContentValues values = new ContentValues();
			values.put(DISCIPLINEENTRY_COLUMN_NAME, target.getName());
			values.put(DISCIPLINEENTRY_COLUMN_TEACHER, target.getTeacher());
			
			disciplinesUpdated = db.update(TABLE_NAME, values, "_id = " + target.getId(), null);
		}	
		
		return disciplinesUpdated;
	}

	@Override
	public void delete(Discipline target) {
		if(target != null) {
			db.delete(TABLE_NAME, "_id = " + target.getId(), null);
		}
	}

	@Override
	public Discipline retrieveById(long id) {
		Discipline target = null;
		String[] columns = {
				DISCIPLINEENTRY_COLUMN_ID,
				DISCIPLINEENTRY_COLUMN_NAME,
				DISCIPLINEENTRY_COLUMN_TEACHER
		};
		
		Cursor cursorDisc = db.query(TABLE_NAME, columns, "_id = " + id, null, null, null, null);
		
		if(cursorDisc.getCount() > 0){
			cursorDisc.moveToFirst();
			
			target = new Discipline();
			target.setId(cursorDisc.getLong(0));
			target.setName(cursorDisc.getString(1));
			target.setTeacher(cursorDisc.getString(2));
		}
		
		return target;
	}
	

	@Override
	public List<Discipline> retrieveAll() {
		List<Discipline> list = new ArrayList<Discipline>();
		Cursor cursor = db.query(TABLE_NAME, new String[]{"_id"}, null, null, null, null, null);
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				long idDiscipline = cursor.getLong(0);
				Discipline target = retrieveById(idDiscipline);
				list.add(target);				
			} while(cursor.moveToNext());
		}
		
		return list;
	}
	

	@Override
	public void close() throws Exception {
		if(db == null || !db.isOpen()) {
			throw new Exception("Database is not present or open");
		}
		
		db.close();		
	}
	
	
	

}
