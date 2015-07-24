package es.model.anotai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Classmate {

    private long id;
    private String name;
    private Set<String> phoneNumbers;

    public Classmate() {
    	this(0, "", new ArrayList<String>());
    }

    public Classmate(String name, List<String> phoneNumbers) {
        this(0, name, phoneNumbers);
    }
    
    public Classmate(final long id, String name, List<String> phoneNumbers) {
    	this.id = id;
        this.name = name;
        this.phoneNumbers = new HashSet<String>(phoneNumbers);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    public void addPhoneNumber(String number) {
        phoneNumbers.add(number);
    }

    public void removePhoneNumber(Integer number) {
        phoneNumbers.remove(number);
    }

    public void clearPhoneNumbers(Integer number) {
        phoneNumbers.clear();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((phoneNumbers == null) ? 0 : phoneNumbers.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	if(!(obj instanceof Classmate)){
    		return false;
    	}
    	
        Classmate other = (Classmate) obj;
        return	this.name.equals(other.name) && 
        		this.getPhoneNumbers().equals(other.getPhoneNumbers());
    }
    
    public String toString(){
    	StringBuilder msg = new StringBuilder();
    	msg.append("Name:\n").append(this.name).append("\nPhones:\n");
    		for(String phone: this.phoneNumbers){
    			msg.append(phone).append("\n");
    		}
    		
    	return msg.toString();
    }
}
