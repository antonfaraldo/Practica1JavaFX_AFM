package com.practica.javafx.dao;

import com.practica.javafx.model.Cancion;

import java.util.List;

public interface CancionDAO {
    void addCancion(Cancion cancion);
    void updateCancion(Cancion cancion);
    void deleteCancion(int id);
    Cancion getCancionById(int id);

    List<Cancion> getCancionesByCantanteId(int cantanteId);
}
