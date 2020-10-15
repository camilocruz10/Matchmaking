package com.atomiclab.socialgamerbackend.domain.model;

public class Reaction {
    String persona_id;
    String publicacion_id;

    public Reaction(String persona_id, String publicacion_id) {
        this.persona_id = persona_id;
        this.publicacion_id = publicacion_id;
    }

    public String getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(String persona_id) {
        this.persona_id = persona_id;
    }

    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }
}