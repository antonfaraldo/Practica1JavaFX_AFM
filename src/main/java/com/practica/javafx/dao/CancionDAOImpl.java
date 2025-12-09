package com.practica.javafx.dao;

import com.practica.javafx.db.DatabaseConnection;
import com.practica.javafx.model.Cancion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancionDAOImpl implements CancionDAO {
    @Override
    public void updateCancion(Cancion cancion) {
        String sql = "UPDATE canciones SET titulo = ?, duracion_segundos = ?, anho_lanzamiento = ?, cantante_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setInt(2, cancion.getDuracionSegundos());
            pstmt.setInt(3, cancion.getAnhoLanzamiento());
            pstmt.setInt(4, cancion.getCantanteId());
            pstmt.setInt(5, cancion.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar canción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addCancion(Cancion cancion) {
        String sql = "INSERT INTO canciones (titulo, duracion_segundos, anho_lanzamiento, cantante_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setInt(2, cancion.getDuracionSegundos());
            pstmt.setInt(3, cancion.getAnhoLanzamiento());
            pstmt.setInt(4, cancion.getCantanteId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al añadir canción: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void deleteCancion(int id) {
        String sql = "DELETE FROM canciones WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cancion: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public Cancion getCancionById(int id) {
        String sql = "SELECT * FROM canciones WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Cancion cancion = new Cancion();
                    cancion.setId(rs.getInt("id"));
                    cancion.setTitulo(rs.getString("titulo"));
                    cancion.setDuracionSegundos(rs.getInt("duracion_segundos"));
                    cancion.setAnhoLanzamiento(rs.getInt("anho_lanzamiento"));
                    cancion.setCantanteId(rs.getInt("cantante_id"));
                    return cancion;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cancion por Id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Cancion> getCancionesByCantanteId(int cantanteId) {
        List<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT * FROM canciones WHERE cantante_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cantanteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cancion cancion = new Cancion();
                    cancion.setId(rs.getInt("id"));
                    cancion.setTitulo(rs.getString("titulo"));
                    cancion.setDuracionSegundos(rs.getInt("duracion_segundos"));
                    cancion.setAnhoLanzamiento(rs.getInt("anho_lanzamiento"));
                    cancion.setCantanteId(rs.getInt("cantante_id"));
                    canciones.add(cancion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener canciones por cantante: " + e.getMessage());
            e.printStackTrace();
        }
        return canciones;
    }
}
