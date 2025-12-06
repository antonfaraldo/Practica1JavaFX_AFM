package com.practica.javafx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private  static final String URL = "jdbc:mysql://localhost:3306/cantantesfx";
    private  static final String USER = "root";
    private  static final String PASSWORD = "";

    private static Connection connection = null;

    private  DatabaseConnection() {
        /**
         * Devuelve una instancia de la conexión a la base de datos.
         * Si la conexión no existe o está cerrada, intenta crear una nueva.
         * @return un objeto Connection.
         * @throws SQLException si ocurre un error al conectar.
         */
    }
    public static Connection getConnection() throws SQLException {
        if (connection == null ||  connection.isClosed() ) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver JDBC no encontrado");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
