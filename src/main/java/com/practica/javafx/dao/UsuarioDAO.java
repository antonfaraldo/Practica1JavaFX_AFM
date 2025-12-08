package com.practica.javafx.dao;

import com.practica.javafx.model.Usuario;

public interface UsuarioDAO {
    boolean validarCredenciales(String nickname, String password);
    Usuario getUsuarioPorNickname(String nickname);
}
