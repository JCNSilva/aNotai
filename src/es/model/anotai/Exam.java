package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    public Exam() {
        super();
    }
    
    public Exam(String subject, Calendar deadlineDate, Priority priority) {
		super(subject, deadlineDate, priority);
	}
    
	public Exam(final long newId, String subject, Calendar deadlineDate, Priority priority) {
		super(newId, subject, deadlineDate, priority);
	}
}
