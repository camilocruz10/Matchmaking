package com.atomiclab.socialgamerbackend.domain.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Comment {
    String publicacion_id;
    Person person;
    String comentario;
    Date fecha;

    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
