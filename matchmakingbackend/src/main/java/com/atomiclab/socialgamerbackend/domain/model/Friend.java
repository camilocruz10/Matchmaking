package com.atomiclab.socialgamerbackend.domain.model;

import javax.persistence.Entity;

@Entity
public class Friend {
    String persona1;
    String persona2;

    public Friend(String persona1, String persona2) {
        this.persona1 = persona1;
        this.persona2 = persona2;
    }

    public String getPersona1() {
        return persona1;
    }

    public void setPersona1(String persona1) {
        this.persona1 = persona1;
    }

    public String getPersona2() {
        return persona2;
    }

    public void setPersona2(String persona2) {
        this.persona2 = persona2;
    }

}
