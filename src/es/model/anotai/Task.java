package es.model.anotai;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Task {
	
    private long id;
	private String description;
	private Calendar cadasterDate;
	private Calendar deadlineDate;
	private Priority priority;
	private double grade;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM");
	
    public Task() {
        setId(0);
        description = "";
        cadasterDate = new GregorianCalendar();
        deadlineDate = new GregorianCalendar();
        priority = Priority.NORMAL;
        grade = 0;
    }
	
	public Task(long newId, String description, Calendar deadlineDate, Priority priority){
		if(deadlineDate == null)
			throw new IllegalArgumentException("Deadline date can't empty");
		
		this.setId(newId);
		this.description = description;
		this.cadasterDate = new GregorianCalendar();
		this.deadlineDate = deadlineDate;
		this.priority = priority;
		this.grade = 0.0;
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
	
	public void setCadasterDate(Calendar cadasterDate) {
		this.cadasterDate = cadasterDate;
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
	
	public String getCadasterDateText() {
	    return dateFormat.format(cadasterDate);
	}
	
	public String getDeadlineDateText() {
        return dateFormat.format(deadlineDate);
    }
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	public String getPriorityText(){
		return priority.toString().toLowerCase();
	}

	/**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    public enum Priority {HIGH, NORMAL, LOW}
}
