package es.database.anotai;

import java.util.List;

public interface AbstractPersister<T> {
	
	public void create(T target);
	
	/** Returns the num of rows affected by the update */
	public int update(T target);
	
	public void delete(T target);
	
	public T retrieveById(long id);
	
	public List<T> retrieveAll();
	
	public void close() throws Exception;

}
