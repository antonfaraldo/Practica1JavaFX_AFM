package com.practica.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("usuario") || password.equals("1234")) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Aqui tiene que ir la 2 vista
            Parent root = FXMLLoader.load(getClass().getResource(""));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de acceso");
            alert.setHeaderText("Credenciales incorrectas");
            alert.setContentText("Incorrect username or password");
            alert.showAndWait();
        }
    }
}
