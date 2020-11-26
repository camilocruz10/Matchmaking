package com.atomiclab.socialgamerbackend.domain.model;

import java.util.Date;
import java.util.List;

public class Chat {
    String id;
    Date ultimomsj;
    List<Mensaje> mensajes;
    List<String> integrantes;

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUltimomsj() {
        return ultimomsj;
    }

    public void setUltimomsj(Date ultimomsj) {
        this.ultimomsj = ultimomsj;
    }

    public List<String> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<String> integrantes) {
        this.integrantes = integrantes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((integrantes == null) ? 0 : integrantes.hashCode());
        result = prime * result + ((mensajes == null) ? 0 : mensajes.hashCode());
        result = prime * result + ((ultimomsj == null) ? 0 : ultimomsj.hashCode());
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
        Chat other = (Chat) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (integrantes == null) {
            if (other.integrantes != null)
                return false;
        } else if (!integrantes.equals(other.integrantes))
            return false;
        if (mensajes == null) {
            if (other.mensajes != null)
                return false;
        } else if (!mensajes.equals(other.mensajes))
            return false;
        if (ultimomsj == null) {
            if (other.ultimomsj != null)
                return false;
        } else if (!ultimomsj.equals(other.ultimomsj))
            return false;
        return true;
    }

}
