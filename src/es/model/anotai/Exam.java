package es.model.anotai;

import java.util.Calendar;

public class Exam extends Task{

	public Exam(String name, Calendar deadlineDate, Priority priority, String subject) {
		super(name, deadlineDate, priority, subject);
	}
	
	public Exam(String name, Calendar deadlineDate, Priority priority) {
		this(name, deadlineDate, priority, "");
	}

}
