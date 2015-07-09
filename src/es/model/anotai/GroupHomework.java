package es.model.anotai;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class GroupHomework extends Task {

    private long id;
    private Set<Classmate> group;

    public GroupHomework() {
        id = 0;
        group = new HashSet<Classmate>();
    }

    public GroupHomework(long newId, String description, Calendar deadlineDate,
            Priority priority, Classmate... mates) {
        super(newId, description, deadlineDate, priority);
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
