package com.atomiclab.socialgamerbackend.domain.model;

import java.util.List;

public class Matchmaking {
    private List<String> juegos;
    private List<String> plataformas;
    private String region;
    private Person person;

    public List<String> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<String> juegos) {
        this.juegos = juegos;
    }

    public List<String> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
