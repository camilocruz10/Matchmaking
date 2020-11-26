package com.atomiclab.socialgamerbackend.domain.model;

public class Request {
    Person person;
    String receptor_id;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getReceptor_id() {
        return receptor_id;
    }

    public void setReceptor_id(String receptor_id) {
        this.receptor_id = receptor_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        result = prime * result + ((receptor_id == null) ? 0 : receptor_id.hashCode());
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
        Request other = (Request) obj;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        if (receptor_id == null) {
            if (other.receptor_id != null)
                return false;
        } else if (!receptor_id.equals(other.receptor_id))
            return false;
        return true;
    }
}
