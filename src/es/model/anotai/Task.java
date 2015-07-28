package es.model.anotai;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public abstract class Task implements Serializable{
	
    private static final long serialVersionUID = 3784704156437126356L;
	private long id;
    private Discipline discipline;
	private String description;
	private Calendar cadasterDate;
	private Calendar deadlineDate;
	private Priority priority;
	private double grade;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
	
    public Task() {
    	this(0, new Discipline("", ""), "", new GregorianCalendar(), Priority.NORMAL);
    }
    
    public Task(Discipline discipline, String description, Calendar deadlineDate, Priority priority){
		this(0, discipline, description, deadlineDate, priority);
	}
	
	public Task(final long newId, Discipline discipline, String description,
			Calendar deadlineDate, Priority priority){
		if(deadlineDate == null)
			throw new IllegalArgumentException("Deadline date can't empty");
		if(discipline == null)
			throw new IllegalArgumentException("A task must be related with a discipline");
		
		
		this.setId(newId);
		this.setDiscipline(discipline);
		this.setDescription(description);
		this.cadasterDate = new GregorianCalendar();
		this.setDeadlineDate(deadlineDate);
		this.priority = priority;
		this.grade = 0.0;
	}
	
	public Discipline getDiscipline(){
		return discipline;
	}
	
	public void setDiscipline(Discipline newDiscipline){
		if(newDiscipline == null)
			throw new IllegalArgumentException("A task must be related with a discipline");
		this.discipline = newDiscipline;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description == null)
			throw new IllegalArgumentException("Description can be empty but null is not allowed");
		this.description = description;
	}

	public Calendar getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Calendar deadlineDate) {
		if(deadlineDate == null)
			throw new IllegalArgumentException("Deadline date can't empty");
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
	    return dateFormat.format(cadasterDate.getTime());
	}
	
	public long getCadasterDateMillis() {
		return cadasterDate.getTimeInMillis();
	}
	
	public String getDeadlineDateText() {
        return dateFormat.format(deadlineDate.getTime());
    }
	
	public long getDeadlineDateMillis() {
		return deadlineDate.getTimeInMillis();
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	public String getPriorityText(){
		return priority.toString().toLowerCase(Locale.US);
	}
	
	public long getId() {
        return id;
    }

    public void setId(long id) {
    	if(id < 0)
			throw new IllegalArgumentException("Id can't be negative. Value found: " + id);
        this.id = id;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cadasterDate == null) ? 0 : cadasterDate.hashCode());
		result = prime * result
				+ ((deadlineDate == null) ? 0 : deadlineDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((discipline == null) ? 0 : discipline.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
	if(!(obj instanceof Task)){
		return false;
	}
	
	Task other = (Task) obj;
	return 	this.discipline.equals(other.discipline) &&
			this.description.equals(other.description) &&
			this.cadasterDate.equals(other.cadasterDate) &&
			this.deadlineDate.equals(other.deadlineDate);
	}

	public enum Priority {HIGH, NORMAL, LOW}
}
