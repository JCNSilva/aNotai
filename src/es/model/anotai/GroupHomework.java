package es.model.anotai;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class GroupHomework extends Task {

    private static final long serialVersionUID = -6420788605564983257L;

	private Set<Classmate> group;

    public GroupHomework() {
    	this.group = new HashSet<Classmate>();
    }

    public GroupHomework(String title, Discipline discipline, String description, Calendar deadlineDate,
            Priority priority, Classmate... mates) {
        super(title, discipline, description, deadlineDate, priority);
        this.group = new HashSet<Classmate>(Arrays.asList(mates));
    }
    
    public GroupHomework(String title, long newId, Discipline discipline, String description, Calendar deadlineDate,
            Priority priority, Classmate... mates) {
        super(newId, title, discipline, description, deadlineDate, priority);
        this.group = new HashSet<Classmate>(Arrays.asList(mates));
    }

    public Set<Classmate> getGroup() {
        return this.group;
    }

    public void addToGroup(Classmate mate) {
        group.add(mate);
    }

    public void removeFromGroup(Classmate mate) {
        group.remove(mate);
    }

    public void clearGroup() {
        group.clear();
    }
}
