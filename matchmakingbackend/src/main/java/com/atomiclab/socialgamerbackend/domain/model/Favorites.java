package com.atomiclab.socialgamerbackend.domain.model;

public class Favorites {

    Games game;
    Person person;

    public Games getGame() {
        return game;
    }

    public void setGame(Games game) {
        this.game = game;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
}
