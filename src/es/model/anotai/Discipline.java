package es.model.anotai;

import java.util.ArrayList;
import java.util.List;

public class Discipline {

    private long id;
    private String name;
    private String teacher;
    private List<Task> tasks;

    public Discipline() {
        this(0,"","");
    }
    
    public Discipline(String name, String teacher) {
        this(0, name, teacher);
    }

    public Discipline(final long newId, String name, String teacher) {
        if (name == null)
            throw new IllegalArgumentException("Name can't be empty");

        this.setId(newId);
        this.name = name;
        this.teacher = teacher;
        this.setTasks(new ArrayList<Task>());
    }

    public long getId() {
        return id;
    }

    public void setId(long iDisciplineId) {
        if (id >= 0) {
            this.id = iDisciplineId;
        }
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * @param tasks
     *            the tasks to set
     */
    public void setTasks(List<Task> tasks) {
        if (tasks == null)
            throw new IllegalArgumentException("Tasks can't be null");

        this.tasks = tasks;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
    	if(!(obj instanceof Discipline)){
    		return false;
    	}
    	
    	Discipline other = (Discipline) obj;
    	return this.name.equals(other.getName()) &&
    			this.teacher.equals(other.getTeacher());
    }
    
    
}
