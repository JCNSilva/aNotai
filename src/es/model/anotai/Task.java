package es.model.anotai;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public abstract class Task {
	
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
		this.discipline = discipline;
		this.description = description;
		this.cadasterDate = new GregorianCalendar();
		this.deadlineDate = deadlineDate;
		this.priority = priority;
		this.grade = 0.0;
	}
	
	public Discipline getDiscipline(){
		return discipline;
	}
	
	public void setDiscipline(Discipline newDiscipline){
		this.discipline = newDiscipline;
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
	    return dateFormat.format(cadasterDate.getTime());
	}
	
	public String getDeadlineDateText() {
        return dateFormat.format(deadlineDate.getTime());
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
        this.id = id;
    }

    public enum Priority {HIGH, NORMAL, LOW}
}
