package com.practica.javafx.model;

public class Cancion {
    private int id;
    private String titulo;
    private int duracionSegundos;
    private int anhoLanzamiento;
    private int cantanteId;

    private Cantante cantante;

    public Cancion() {

    }

    public Cancion(int id, String titulo, int duracionSegundos, int anhoLanzamiento, int cantanteId, Cantante cantante) {
        this.id = id;
        this.titulo = titulo;
        this.duracionSegundos = duracionSegundos;
        this.anhoLanzamiento = anhoLanzamiento;
        this.cantanteId = cantanteId;
        this.cantante = cantante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public int getAnhoLanzamiento() {
        return anhoLanzamiento;
    }

    public void setAnhoLanzamiento(int anhoLanzamiento) {
        this.anhoLanzamiento = anhoLanzamiento;
    }

    public int getCantanteId() {
        return cantanteId;
    }

    public void setCantanteId(int cantanteId) {
        this.cantanteId = cantanteId;
    }

    public Cantante getCantante() {
        return cantante;
    }

    public void setCantante(Cantante cantante) {
        this.cantante = cantante;
    }
    public String getDuracionFormateada() {
        int segundosTotales = this.duracionSegundos;
        int minutos = segundosTotales / 60;
        int segundosRestantes = segundosTotales % 60;

        return String.format("%02d:%02d", minutos, segundosRestantes); // Esto lo hace en formato M:ss como 2:07
    }
}
