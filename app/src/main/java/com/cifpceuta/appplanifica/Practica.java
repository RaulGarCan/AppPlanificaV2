package com.cifpceuta.appplanifica;

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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
    public Practica(HashMap<String, Object> mapa){
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
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int tiempoPlazo(){
        // 0-1 Dias -> Rojo (-1)
        // 1-3 Dias -> Ambar (0)
        // >3 dias -> Verde (1)
        String[] datosFechaFin = fechaFin.split("/");
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.of(Integer.parseInt(datosFechaFin[2]),Integer.parseInt(datosFechaFin[1]),Integer.parseInt(datosFechaFin[0]));
        long diferenciaDias = ChronoPeriod.between(fechaInicio, fechaFin).get(ChronoUnit.DAYS);
        if(diferenciaDias<=1){
            return -1; // Rojo
        } else if(diferenciaDias>3) {
            return 1; // Verde
        } else {
            return 0; // Ambar
        }
    }
    public LocalDate fechaFinDate(){
        String[] datos = fechaFin.split("/");
        return LocalDate.of(Integer.parseInt(datos[2]),Integer.parseInt(datos[1]),Integer.parseInt(datos[0]));
    }
    public LocalDate fechaInicioDate(){
        String[] datos = fechaInicio.split("/");
        return LocalDate.of(Integer.parseInt(datos[2]),Integer.parseInt(datos[1]),Integer.parseInt(datos[0]));
    }
    public HashMap<String, Object> objetoAMap(){
        HashMap<String, Object> datos = new HashMap<>();
        datos.put("modulo",this.getModulo());
        datos.put("titulo",this.getTitulo());
        datos.put("fechaInicio",this.getFechaInicio());
        datos.put("fechaFin",this.getFechaFin());
        datos.put("descripcion",this.getDescripcion());
        return datos;
    }

    @Override
    public String toString() {
        return "Practica{" +
                "modulo='" + modulo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
