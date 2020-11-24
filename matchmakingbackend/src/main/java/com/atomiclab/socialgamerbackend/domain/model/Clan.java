package com.atomiclab.socialgamerbackend.domain.model;

import javax.persistence.Entity;

@Entity
public class Clan {
    private boolean esPrivado;
    private Person person;
    private String nombre_clan;
    private String foto_clan;
    private String descripcion;

    public String getNombre_clan() {
        return nombre_clan;
    }

    public void setNombre_clan(String nombre_clan) {
        this.nombre_clan = nombre_clan;
    }

    public String getFoto_clan() {
        return foto_clan;
    }

    public void setFoto_clan(String foto_clan) {
        this.foto_clan = foto_clan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsPrivado() {
        return esPrivado;
    }

    public void setEsPrivado(boolean esPrivado) {
        this.esPrivado = esPrivado;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
