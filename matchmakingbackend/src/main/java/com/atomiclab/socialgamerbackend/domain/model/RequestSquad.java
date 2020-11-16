package com.atomiclab.socialgamerbackend.domain.model;

public class RequestSquad {
    String idSquad;
    String nombreSquad;
    Person remitente;
    Person receptor;

    public String getIdSquad() {
        return idSquad;
    }

    public void setIdSquad(String idSquad) {
        this.idSquad = idSquad;
    }

    public String getNombreSquad() {
        return nombreSquad;
    }

    public void setNombreSquad(String nombreSquad) {
        this.nombreSquad = nombreSquad;
    }

    public Person getRemitente() {
        return remitente;
    }

    public void setRemitente(Person remitente) {
        this.remitente = remitente;
    }

    public Person getReceptor() {
        return receptor;
    }

    public void setReceptor(Person receptor) {
        this.receptor = receptor;
    }

}
