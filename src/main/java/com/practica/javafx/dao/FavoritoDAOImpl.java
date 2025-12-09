package com.practica.javafx.dao;

import com.practica.javafx.db.DatabaseConnection;
import com.practica.javafx.model.Cantante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAOImpl implements FavoritoDAO {
    @Override
    public List<Cantante> getCantantesFavoritos(int usuarioId) {
        List<Cantante> favoritos = new ArrayList<>();

        String sql = "SELECT c.* FROM cantantes c " +
                     "JOIN favoritos_cantantes fc ON c.id = fc.cantante_id" +
                        "WHERE fc.usuario_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cantante cantante = new Cantante();
                cantante.setId(rs.getInt("id"));
                cantante.setNombre(rs.getString("nombre"));
                cantante.setApellido(rs.getString("apellido"));
                cantante.setNombreArtistico(rs.getString("nombre_artistico"));
                cantante.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                cantante.setGeneroMusical(rs.getString("genero_musical"));
                favoritos.add(cantante);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantantes favoritos: " + e.getMessage());
            e.printStackTrace();
        }
        return favoritos;
    }
    @Override
    public void addCantanteFavorito(int usuarioId, int cantanteId) {
        String sql = "INSERT INTO favoritos_cantantes (usuario_id, cantante_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, cantanteId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al añadir cantante favorito: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void removeCantanteFavorito(int usuarioId, int cantanteId) {
        String sql = "DELETE FROM favoritos_cantantes WHERE usuario_id = ? AND cantante_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, cantanteId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar cantante favorito: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
