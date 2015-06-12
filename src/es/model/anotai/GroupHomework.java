package es.model.anotai;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class GroupHomework extends Task {
	
	private Set<Classmate> group;

	public GroupHomework(	String name, Calendar deadlineDate, Priority priority, 
							String description, Classmate... mates) {
		super(name, deadlineDate, priority, description);
		this.group = new HashSet<Classmate>(Arrays.asList(mates));
	}
	
	public GroupHomework(String name, Calendar deadlineDate, Priority priority) {
		this(name, deadlineDate, priority, "");
	}
	
	public void addToGroup(Classmate mate){
		group.add(mate);
	}
	
	public void removeFromGroup(Classmate mate){
		group.remove(mate);
	}
	
	public void clearGroup(){
		group.clear();
	}

}
