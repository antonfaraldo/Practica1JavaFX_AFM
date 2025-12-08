package com.practica.javafx.dao;

import com.practica.javafx.db.DatabaseConnection;
import com.practica.javafx.model.Cantante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CantanteDAOImpl implements CantanteDAO {

    @Override
    public void addCantante(Cantante cantante) {
        String sql = "INSERT INTO cantantes (nombre, apellido, nombre-artistico, fecha_nacimiento, genero_musical) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cantante.getNombre());
            pstmt.setString(2, cantante.getApellido());
            pstmt.setString(3, cantante.getNombreArtistico());
            pstmt.setDate(4, Date.valueOf(cantante.getFechaNacimiento()));
            pstmt.setString(5, cantante.getGeneroMusical());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCantante(Cantante cantante) {
        String sql = "UPDATE cantantes SET nombre = ?, apellido = ?, nombre_artistico = ?, fecha_nacimiento = ?, genero_musical = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cantante.getNombre());
            pstmt.setString(2, cantante.getApellido());
            pstmt.setString(3, cantante.getNombreArtistico());
            pstmt.setDate(4, Date.valueOf(cantante.getFechaNacimiento()));
            pstmt.setString(5, cantante.getGeneroMusical());
            pstmt.setInt(6, cantante.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCantante(int id) {
        String sql = "DELETE FROM cantantes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    @Override
    public List<Cantante> getAllCantantes() {
        List<Cantante> cantantes = new ArrayList<>();
        String sql = "SELECT * FROM cantantes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cantante cantante = new Cantante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nombre-artistico"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("genero_musical")
                );
                cantantes.add(cantante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantantes;
    }

    @Override
    public Cantante getCantanteById(int id) {
        String sql = "SELECT * FROM cantantes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Acabar esto
                }
            }
        }
        return null;
    }

    @Override
    public List<Cantante> buscarJugadorPorNombreOApodo(String termino) {
        List<Cantante> cantantes = new ArrayList<>();
        String sql = "SELECT * FROM cantantes WHERE nombre LIKE ? OR LOWER(nombre_artistico) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String terminoBusqueda = "%" + termino.toLowerCase() + "%";
            pstmt.setString(1, terminoBusqueda);
            pstmt.setString(2, terminoBusqueda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cantante cantante = new Cantante(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nombre_artistico"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("genero_musical")
                    );
                    cantantes.add(cantante);
                }
            } catch (SQLException e) {
                System.err.println("Error al buscar jugadores: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return cantantes;
    }

}
