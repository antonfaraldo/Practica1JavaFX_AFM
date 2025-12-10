package com.practica.javafx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private  static final String URL = "jdbc:mariadb://localhost:3306/cantantesfx";
    private  static final String USER = "root";
    private  static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: No se encontro la clase del driver de MariaDB", e);}
        }
}
