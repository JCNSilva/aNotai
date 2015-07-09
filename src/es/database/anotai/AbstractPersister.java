package es.database.anotai;

import java.util.List;

public interface AbstractPersister<T> {
	
	public void create(T target);
	
	public void update(T target);
	
	public void delete(T target);
	
	public T retrieveById(long id);
	
	public List<T> retrieveAll();

}
