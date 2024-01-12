package com.cifpceuta.appplanifica;

public class Estudiante {
    private String nombre;
    private String email;
    private String grupo;
    private String turno;

    public Estudiante(String nombre, String email, String grupo, String turno) {
        this.nombre = nombre;
        this.email = email;
        this.grupo = grupo;
        this.turno = turno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getTurno() {
        return turno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
