package com.practica.javafx.model;

import java.time.LocalDate;

public class Cantante {

    private int id;
    private String nombre;
    private String apellido;
    private String nombreArtistico;
    private LocalDate fechaNacimiento;
    private String generoMusical;

    // Constructor Vacio
    public Cantante() {

    }
    public Cantante(int id, String nombre,  String apellido, String nombreArtistico, java.time.LocalDate fechaNacimiento, String generoMusical) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreArtistico = nombreArtistico;
        this.fechaNacimiento = fechaNacimiento;
        this.generoMusical = generoMusical;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }
    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return java.time.Period.between(fechaNacimiento, java.time.LocalDate.now()).getYears();
    }
}
