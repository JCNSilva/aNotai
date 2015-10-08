package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    private static final long serialVersionUID = 1392845360047935400L;

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
