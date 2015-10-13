package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{
    
    private static final long serialVersionUID = 1392845360047935400L;

	public Exam() {
        super();
    }
    
    public Exam(String title, Discipline discipline, String description, Calendar deadlineDate, Priority priority) {
		super(title, discipline, description, deadlineDate, priority);
	}
    
	public Exam(final long newId, String title, Discipline discipline, String description,
			Calendar deadlineDate, Priority priority) {
		super(newId, title, discipline, description, deadlineDate, priority);
	}
}
