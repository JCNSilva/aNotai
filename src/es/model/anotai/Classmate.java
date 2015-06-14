package es.model.anotai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Classmate {

    private long id;
    private String name;
    private Set<String> phoneNumbers;

    public Classmate() {
        setId(0);
        name = "";
        phoneNumbers = new HashSet<String>();
    }

    public Classmate(String name, String... phoneNumbers) {
        this.name = name;
        this.phoneNumbers = new HashSet<String>(Arrays.asList(phoneNumbers));
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

    public void rmeovePhoneNumber(Integer number) {
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Classmate other = (Classmate) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phoneNumbers == null) {
            if (other.phoneNumbers != null)
                return false;
        } else if (!phoneNumbers.equals(other.phoneNumbers))
            return false;
        return true;
    }
}
