package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    /** The id. */
    private long id;
    
    public Exam() {
        super();
        id = 0;
    }
    
	public Exam(long newId, String name, Calendar deadlineDate, Priority priority, String subject) {
		super(newId, name, deadlineDate, priority, subject);
		setId(newId);
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
