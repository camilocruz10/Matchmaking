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
}
