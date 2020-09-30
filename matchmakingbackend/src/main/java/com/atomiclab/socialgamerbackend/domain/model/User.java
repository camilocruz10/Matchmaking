package com.atomiclab.socialgamerbackend.domain.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class User {
    private String correo;
    private String apellidos;
    private Integer conexion;
    private Date fecha_nacimiento;
    private String foto_perfil;
    private String jugando;
    private String nombre_usuario;
    private String nombres;
    private List<String> plataformas;
    private String region_id;
    private Boolean reportado;

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public Integer getConexion() {
        return conexion;
    }
    public void setConexion(Integer conexion) {
        this.conexion = conexion;
    }
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public String getFoto_perfil() {
        return foto_perfil;
    }
    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }
    public String getJugando() {
        return jugando;
    }
    public void setJugando(String jugando) {
        this.jugando = jugando;
    }
    public String getNombre_usuario() {
        return nombre_usuario;
    }
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getRegion_id() {
        return region_id;
    }
    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
    public Boolean getReportado() {
        return reportado;
    }
    public void setReportado(Boolean reportado) {
        this.reportado = reportado;
    }

    public List<String> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}