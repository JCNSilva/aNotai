package es.model.anotai;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class GroupHomework extends Task implements Serializable{

    private static final long serialVersionUID = -288792196911274116L;
	private long id;
    private Set<Classmate> group;

    public GroupHomework() {
        id = 0;
        group = new HashSet<Classmate>();
    }

    public GroupHomework(long newId, String name, Calendar deadlineDate,
            Priority priority, String description, Classmate... mates) {
        super(newId, name, deadlineDate, priority, description);
        this.group = new HashSet<Classmate>(Arrays.asList(mates));
        setId(newId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
