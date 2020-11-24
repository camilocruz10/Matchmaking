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

}
