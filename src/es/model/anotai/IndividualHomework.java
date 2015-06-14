package es.model.anotai;

import java.util.Calendar;

public class IndividualHomework extends Task {
    /** The id. */
    private long id;    

    public IndividualHomework() {
        super();
        id = 0;
    }
    
	public IndividualHomework(long newId, String name, Calendar deadlineDate, Priority priority, String description) {
		super(newId, name, deadlineDate, priority, description);
		setId(newId);
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

}
