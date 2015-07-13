package es.model.anotai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Discipline implements Serializable{

	private static final long serialVersionUID = -7503016405761927822L;
	private long id;
    private String name;
    private String teacher;
    private List<Task> tasks;

    public Discipline() {
        id = 0;
        name = "";
        teacher = "";
        tasks = new ArrayList<Task>();
    }

    public Discipline(final long newId, String name, String teacher,
            List<Task> tasks) {
        if (name == null)
            throw new IllegalArgumentException("Name can't be empty");

        this.setId(newId);
        this.name = name;
        this.teacher = teacher;
        this.setTasks(tasks);
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
}
