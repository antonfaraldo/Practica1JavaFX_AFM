package com.practica.javafx.dao;

import com.practica.javafx.db.DatabaseConnection;
import com.practica.javafx.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public boolean validarCredenciales(String nickname, String password) {
        String sql = "SELECT * FROM usuarios WHERE nickname = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nickname);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar las credenciales del usuario" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Usuario getUsuarioPorNickname(String nickname) {
        String sql = "SELECT * FROM usuarios WHERE nickname = ?";
        Usuario usuario = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nickname);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setUsuarioId(rs.getInt("usuario_id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setNickname(rs.getString("nickname"));
                    usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar las credenciales del usuario" + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }
}
