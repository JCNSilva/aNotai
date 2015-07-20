package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {
    
	public IndividualHomework() {
        super();
    }
    
    public IndividualHomework(String description, Calendar deadlineDate, Priority priority) {
		super(description, deadlineDate, priority);
	}
    
	public IndividualHomework(long newId, String description, Calendar deadlineDate, Priority priority) {
		super(newId, description, deadlineDate, priority);
	}
}
