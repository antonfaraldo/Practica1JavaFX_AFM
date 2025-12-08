package com.practica.javafx.model;

import java.time.LocalDate;

public class Usuario {
    private int usuario_id;
    private String nombre;
    private String apellido;
    private String nickname;
    private LocalDate fechaNacimiento;
    private String email;
    private String password;

    // Constructor vacio
    public Usuario() {

    }
    public Usuario(int usuario_id, String nombre, String apellido,  String nickname, LocalDate fechaNacimiento, String email, String password) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.password = password;
    }
    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuario_id=" + usuario_id  + ", nombre=" + nombre + ", apellido=" + apellido + ", nickname=" + nickname + "fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", password=" + password + '}';
    }
}
