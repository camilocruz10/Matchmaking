package com.atomiclab.socialgamerbackend.domain.model;

import java.util.List;

public class Squad {
    String id_squad;
    String nombre;
    String chat_id;
    String admin;
    boolean visibilidad;
    List<Person> integrantes;
    String imagen;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_squad == null) ? 0 : id_squad.hashCode());
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
        Squad other = (Squad) obj;
        if (id_squad == null) {
            if (other.id_squad != null)
                return false;
        } else if (!id_squad.equals(other.id_squad))
            return false;
        return true;
    }

    public String getId_squad() {
        return id_squad;
    }

    public void setId_squad(String id_squad) {
        this.id_squad = id_squad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<Person> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Person> integrantes) {
        this.integrantes = integrantes;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
