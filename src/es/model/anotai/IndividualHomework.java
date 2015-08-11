package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {
	private String title;
    
	public IndividualHomework() {
        super();
    }
    
    public IndividualHomework(String title, Discipline discipline, String description, 
    		Calendar deadlineDate, Priority priority) {
		super(discipline, description, deadlineDate, priority);
		this.setTitle(title);
	}
    
	public IndividualHomework(String title, long newId, Discipline discipline, String description,
			Calendar deadlineDate, Priority priority) {
		super(newId, discipline, description, deadlineDate, priority);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
