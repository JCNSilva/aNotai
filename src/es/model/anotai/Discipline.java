package es.model.anotai;

import java.io.Serializable;



public class Discipline implements Serializable{

    private static final long serialVersionUID = -2699314244245902531L;
	private long id;
    private String name;
    private String teacher;

    public Discipline() {
        this(0,"","");
    }
    
    public Discipline(String name, String teacher) {
        this(0, name, teacher);
    }

    //TODO refactor
    public Discipline(final long newId, String name, String teacher) {
        if (name == null)
            throw new IllegalArgumentException("Name can't be null");
        if (teacher == null)
            throw new IllegalArgumentException("Teacher can't be null");

        this.setId(newId);
        this.name = name;
        this.teacher = teacher;
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
