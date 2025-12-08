package com.practica.javafx.dao;

import com.practica.javafx.model.Cantante;

import java.util.List;

public interface FavoritoDAO {
    List<Cantante> getCantantesFavoritos();
    void addCantanteFavorito(int usuarioId, int cantanteId);
    void removeCantanteFavorito(int usuarioId, int cantanteId);
}
