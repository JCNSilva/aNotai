package es.model.anotai;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Task {
	
	private String name;
	private String description;
	private Calendar cadasterDate;
	private Calendar deadlineDate;
	private Priority priority;
	private double grade;
	
	public Task(String name, Calendar deadlineDate, Priority priority, String description){
		if(name == null)
			throw new IllegalArgumentException("Name can't be empty");
		if(deadlineDate == null)
			throw new IllegalArgumentException("Deadline date can't empty");
		
		this.name = name;
		this.description = description;
		this.cadasterDate = new GregorianCalendar();
		this.deadlineDate = deadlineDate;
		this.priority = priority;
		this.grade = 0.0;
	}
	
	public Task(String name, Calendar deadlineDate, Priority priority){
		this(name, deadlineDate, priority, "");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Calendar deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Calendar getCadasterDate() {
		return cadasterDate;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public enum Priority {HIGH, NORMAL, LOW}
}
