package com.practica.javafx.controller;

import com.practica.javafx.dao.CantanteDAO;
import com.practica.javafx.dao.CantanteDAOImpl;
import com.practica.javafx.model.Cantante;
import com.practica.javafx.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;


public class CantantesController implements Initializable {
    @FXML
    private TableView<Cantante> tablaCantantes;
    @FXML private TableColumn<Cantante, Integer> colId;
    @FXML private TableColumn<Cantante, String> colNombre;
    @FXML private TableColumn<Cantante, String> colApellido;
    @FXML private TableColumn<Cantante, String> colNombreArtistico;
    @FXML private TableColumn<Cantante, LocalDate> colFechaNacimiento;
    @FXML private TableColumn<Cantante, String> colGenero;
    @FXML private TableColumn<Cantante, String> colEdadCalculada; // Este es el dato calculado

    private CantanteDAO cantanteDAO;
    private ObservableList<Cantante> listaCantantes;

    public CantantesController() {
        this.cantanteDAO = new CantanteDAOImpl();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaCantantes = FXCollections.observableArrayList();
        tablaCantantes.setItems(listaCantantes);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colNombreArtistico.setCellValueFactory(new PropertyValueFactory<>("nombreArtistico"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("generoMusical"));

        // Configurar Dato Calculado (edad)
        //Momentaneo
        colEdadCalculada.setCellValueFactory(cellData -> {
            Cantante cantante = cellData.getValue();
            if (cantante.getFechaNacimiento() != null) {
                int edad = Period.between(cantante.getFechaNacimiento(), LocalDate.now()).getYears();
                return new javafx.beans.property.SimpleStringProperty(String.valueOf(edad));
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        cargarCantantes();
    }
    private void cargarCantantes() {
        try {
            listaCantantes.clear();
            listaCantantes.addAll(cantanteDAO.getAllCantantes());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de carga", "No se pudieron cargar los cantantes");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleNuevoCantante() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "CRUD", "Abrir formulario para nuevo cantante.");
    }
    @FXML
    private void handleEditarCantante() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "CRUD", "Abrir formulario para editar cantante.");
    }
    @FXML
    private void handleEliminarCantante() {
        Cantante seleccionado = tablaCantantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                cantanteDAO.deleteCantante(seleccionado.getId());
                cargarCantantes();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cantante eliminado.");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el cantante (posiblemente tiene canciones asociadas).");
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección", "Por favor, selecciona un cantante para eliminar.");
        }
    }
    @FXML
    private void handleGestionarCanciones(javafx.event.ActionEvent event) {
        Cantante seleccionado = tablaCantantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("com/vistas/canciones-view.fxml"));
                Parent root = fxmlLoader.load();

                CancionesController cancionesController = fxmlLoader.getController();
                cancionesController.setCantanteSeleccionado(seleccionado);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Carga", "No se pudo cargar la vista de Canciones.");
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección", "Selecciona un cantante para gestionar sus canciones.");
        }
    }
    @FXML
    private void handleVolver(javafx.event.ActionEvent event) {
        // Para volver a la vista principal
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

