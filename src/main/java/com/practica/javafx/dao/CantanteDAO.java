package com.practica.javafx.dao;

import com.practica.javafx.model.Cantante;

import java.util.List;

public interface CantanteDAO {
    void addCantante(Cantante cantante);
    void updateCantante(Cantante cantante);
    void deleteCantante(int id);

    List<Cantante> getAllCantantes();
    Cantante getCantanteById(int id);
    List<Cantante> buscarCantantePorNombreOApodo(String termino);
}
