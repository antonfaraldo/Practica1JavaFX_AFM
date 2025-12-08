package com.practica.javafx.controller;

import com.practica.javafx.model.Usuario;
import com.practica.javafx.dao.UsuarioDAO;
import com.practica.javafx.dao.UsuarioDAOImpl;
import com.practica.javafx.Main;

import javafx.application.Platform;
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

import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        String nickname = usernameField.getText();
        String password = passwordField.getText();

        if (nickname.isEmpty() || password.isEmpty()) {
            showAlert("Error de validacion", "Introduce tu nickname y tu contraseña");
            return;
        }
        if (usuarioDAO.validarCredenciales(nickname, password)) {
            Usuario usuario = usuarioDAO.getUsuarioPorNickname(nickname);

            if (usuario != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("usuarios-view.fxml"));
                    Parent root = loader.load();

                    UsuariosController usuariosController = fxmlLoader.getController();
                    usuariosController.setUsuariologueado(usuario);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de Acceso");
                    alert.setHeaderText("Credenciales incorrectas");
                    alert.setContentText("Por favor, verifica tu usuario y contraseña.");
                    alert.showAndWait();
                }
            }
        }
    }
    @Override
    public void initialice(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        System.out.println("La vista de login está lista. Poniendo el foco en el campo de usuario.");
        Platform.runLater(() -> usernameField.requestFocus());
    }
    private void showAlert(String titulo, String mensage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensage);
        alert.showAndWait();
    }
}