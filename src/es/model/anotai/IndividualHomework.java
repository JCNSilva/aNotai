package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {

	public IndividualHomework(String name, Calendar deadlineDate, Priority priority, String description) {
		super(name, deadlineDate, priority, description);
	}
	
	public IndividualHomework(String name, Calendar deadlineDate, Priority priority) {
		this(name, deadlineDate, priority, "");
	}

}
