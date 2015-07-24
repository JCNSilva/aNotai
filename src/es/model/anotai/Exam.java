package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    public Exam() {
        super();
    }
    
    public Exam(Discipline discipline, String subject, Calendar deadlineDate, Priority priority) {
		super(discipline, subject, deadlineDate, priority);
	}
    
	public Exam(final long newId, Discipline discipline, String subject,
			Calendar deadlineDate, Priority priority) {
		super(newId, discipline, subject, deadlineDate, priority);
	}
}
