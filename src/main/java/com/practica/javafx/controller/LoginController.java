package com.practica.javafx.controller;

import com.practica.javafx.model.Cancion;
import com.practica.javafx.model.Usuario;
import com.practica.javafx.dao.UsuarioDAO;
import com.practica.javafx.dao.UsuarioDAOImpl;
import com.practica.javafx.model.Cantante;
import com.practica.javafx.dao.FavoritoDAO;
import com.practica.javafx.dao.FavoritoDAOImpl;
import com.practica.javafx.Main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private FavoritoDAO favoritoDAO = new FavoritoDAOImpl();
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
                    List<Cantante> favoritos = favoritoDAO.getCantantesFavoritos(usuario.getUsuarioId());
                    usuario.setCantantesFavoritos(favoritos);

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com.practica.javafx.controller/usuarios-view.fxml"));
                    Parent root = fxmlLoader.load();

                    UsuariosController usuariosController = fxmlLoader.getController();
                    usuariosController.setUsuarioLogueado(usuario);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e){
                    showAlert("Error de carga", "No se pudo cargar la vista principal");
                    e.printStackTrace();
                } catch (Exception e) {
                    showAlert("ERROR", "Error al procesar los datos");
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "No cargo los datos completos del usuario");
            }
        } else {
            showAlert("Error de Acceso", "Credenciales no validos");
        }
    }
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
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