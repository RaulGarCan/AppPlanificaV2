package com.cifpceuta.appplanifica;

import java.util.Map;

public class Practica {
    private String modulo;
    private String titulo;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;


    public Practica(String modulo, String titulo, String fechaInicio, String fechaFin, String descripcion) {
        this.modulo = modulo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }
    public Practica(Map<String, Object> mapa){
        this.modulo = mapa.get("modulo").toString();
        this.titulo = mapa.get("titulo").toString();
        this.fechaInicio = mapa.get("fechaInicio").toString();
        this.fechaFin = mapa.get("fechaFin").toString();
        this.descripcion = mapa.get("descripcion").toString();
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
