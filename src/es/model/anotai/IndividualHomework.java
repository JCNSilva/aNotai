package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {
    
	private static final long serialVersionUID = 8679244822728352892L;

	public IndividualHomework() {
        super();
    }
    
    public IndividualHomework(String title, Discipline discipline, String description, 
    		Calendar deadlineDate, Priority priority) {
		super(title, discipline, description, deadlineDate, priority);
	}
    
	public IndividualHomework(String title, long newId, Discipline discipline, String description,
			Calendar deadlineDate, Priority priority) {
		super(newId, title, discipline, description, deadlineDate, priority);
	}
}
