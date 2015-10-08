package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {
    
	private static final long serialVersionUID = 8679244822728352892L;

	public IndividualHomework() {
        super();
    }
    
    public IndividualHomework(Discipline discipline, String description, 
    		Calendar deadlineDate, Priority priority) {
		super(discipline, description, deadlineDate, priority);
	}
    
	public IndividualHomework(long newId, Discipline discipline, String description,
			Calendar deadlineDate, Priority priority) {
		super(newId, discipline, description, deadlineDate, priority);
	}
}
