package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    /** The id. */
    private long id;
    
    public Exam() {
        super();
        id = 0;
    }
    
	public Exam(long newId, String subject, Calendar deadlineDate, Priority priority) {
		super(newId, subject, deadlineDate, priority);
		setId(newId);
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
