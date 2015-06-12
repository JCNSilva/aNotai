package es.model.anotai;

public class Discipline {
    private String name;
    private String teacher;

    public Discipline(String name, String teacher) {
    	if(name == null)
    		throw new IllegalArgumentException("Name can't be empty");
    	
        this.name = name;
        this.teacher = teacher;
    }
    
    public Discipline(String name) {
        this(name, "");
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
}
