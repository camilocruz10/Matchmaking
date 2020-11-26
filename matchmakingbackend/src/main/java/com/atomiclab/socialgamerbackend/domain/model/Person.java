package com.atomiclab.socialgamerbackend.domain.model;

import javax.persistence.Entity;

@Entity
public class Person {
    private String persona_id;
    private String nombre_usuario;
    private String foto_perfil;

    public String getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(String persona_id) {
        this.persona_id = persona_id;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((foto_perfil == null) ? 0 : foto_perfil.hashCode());
        result = prime * result + ((nombre_usuario == null) ? 0 : nombre_usuario.hashCode());
        result = prime * result + ((persona_id == null) ? 0 : persona_id.hashCode());
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
        Person other = (Person) obj;
        if (foto_perfil == null) {
            if (other.foto_perfil != null)
                return false;
        } else if (!foto_perfil.equals(other.foto_perfil))
            return false;
        if (nombre_usuario == null) {
            if (other.nombre_usuario != null)
                return false;
        } else if (!nombre_usuario.equals(other.nombre_usuario))
            return false;
        if (persona_id == null) {
            if (other.persona_id != null)
                return false;
        } else if (!persona_id.equals(other.persona_id))
            return false;
        return true;
    }

}
