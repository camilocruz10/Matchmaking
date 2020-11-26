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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + (esPrivado ? 1231 : 1237);
        result = prime * result + ((foto_clan == null) ? 0 : foto_clan.hashCode());
        result = prime * result + ((nombre_clan == null) ? 0 : nombre_clan.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
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
        Clan other = (Clan) obj;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (esPrivado != other.esPrivado)
            return false;
        if (foto_clan == null) {
            if (other.foto_clan != null)
                return false;
        } else if (!foto_clan.equals(other.foto_clan))
            return false;
        if (nombre_clan == null) {
            if (other.nombre_clan != null)
                return false;
        } else if (!nombre_clan.equals(other.nombre_clan))
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        return true;
    }
}
