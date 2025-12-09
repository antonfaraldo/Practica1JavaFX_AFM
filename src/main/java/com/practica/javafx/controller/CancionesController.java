package com.practica.javafx.controller;

import com.practica.javafx.dao.CancionDAO;
import com.practica.javafx.dao.CancionDAOImpl;
import com.practica.javafx.model.Cancion;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CancionesController implements Initializable {
    @FXML private Label lblNombreCantante;
    @FXML private TableView<Cancion> tablaCanciones;
    @FXML private TableColumn<Cancion, Integer> colId;
    @FXML private TableColumn<Cancion, String> colTitulo;
    @FXML private TableColumn<Cancion, Integer> colDuracion;
    @FXML private TableColumn<Cancion, Integer> colAnho;
    @FXML private TableColumn<Cancion, String> colDuracionMinutos; //dato calculado

    @FXML private TextField tfTitulo;
    @FXML private TextField tfDuracion;
    @FXML private TextField tfAnho;

    private CancionDAO cancionDAO;
    private ObservableList<Cancion> listaCanciones;
    private Cantante cantanteSeleccionado;
    private Cancion cancionEnEdicion;

    public CancionesController() {
        this.cancionDAO = new CancionDAOImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaCanciones = FXCollections.observableArrayList();
        tablaCanciones.setItems(listaCanciones);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionSegundos"));
        colAnho.setCellValueFactory(new PropertyValueFactory<>("anhoLanzamiento"));

        // Configurar dato calculado (duracion en m:s)
        colDuracionMinutos.setCellValueFactory(cellData -> {
            Cancion cancion = cellData.getValue();
            int segundos = cancion.getDuracionSegundos();
            int minutos = segundos / 60;
            int segundosRestantes = segundos % 60;
            String formato = String.format("%02d:%02d", minutos, segundosRestantes);
            return new javafx.beans.property.SimpleStringProperty(formato);
        });
        limpiarFormulario();
        configurarListenersTabla();
        mostrarDatosIniciales();
    }

    private void mostrarDatosIniciales() {
        mostrarDatosCantante();
        cargarCanciones();
    }

    private void mostrarDatosCantante() {
        if (cantanteSeleccionado != null) {
            lblNombreCantante.setText(cantanteSeleccionado.getNombreArtistico() + " (" + cantanteSeleccionado.getNombre() + ")");
        }
    }

    private void limpiarFormulario() {
        tfTitulo.clear();
        tfDuracion.clear();
        tfAnho.clear();
        cancionEnEdicion = null;
    }

    private void configurarListenersTabla() {
        tablaCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostarDetallesCancion(newSel);
                cancionEnEdicion = newSel;
            } else {
                limpiarFormulario();
                cancionEnEdicion = null;
            }
        });
    }
    // Este metodo es llamado por CantantesController
    public void setCantanteSeleccionado(Cantante cantante) {
        this.cantanteSeleccionado = cantante;

        if (lblNombreCantante != null) {
            mostrarDatosCantante();
            cargarCanciones();
        }
    }

    private void cargarCanciones() {
        listaCanciones.clear();
        if (cantanteSeleccionado != null) {
            try {
                listaCanciones.addAll(cancionDAO.getCancionesByCantanteId(cantanteSeleccionado.getId()));
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Carga", "No se pudieron cargar las canciones.");
                e.printStackTrace();
            }
        }
    }
    private void mostarDetallesCancion(Cancion cancion) {
        if (cancion != null) {
            tfTitulo.setText(cancion.getTitulo());
            tfDuracion.setText(String.valueOf( cancion.getDuracionSegundos()));
            tfAnho.setText(String.valueOf( cancion.getAnhoLanzamiento()));
        } else {
            limpiarFormulario();
        }
    }
    @FXML
    private void handleNuevo() {
        limpiarFormulario();
        tablaCanciones.getSelectionModel().clearSelection();
    }
    @FXML
    private void handleEditar() {
        Cancion cancion = tablaCanciones.getSelectionModel().getSelectedItem();
        if (cancion != null) {
            mostarDetallesCancion(cancion);
            cancionEnEdicion = cancion;
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección", "Selecciona una canción para editar.");
        }
    }
    @FXML
    private void handleGuardar() {
        if (cantanteSeleccionado != null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay un cantante seleccionado.");
            return;
        }

        try {
            String titulo = tfTitulo.getText();
            int duracion = Integer.parseInt(tfDuracion.getText());
            int anho  = Integer.parseInt(tfAnho.getText());

            if (titulo.isEmpty() || duracion <= 0 || anho < 1900) {
                mostrarAlerta(Alert.AlertType.ERROR, "Validación", "Por favor, completa todos los campos correctamente");
                return;
            }
            if (cancionEnEdicion == null) {
                Cancion nuevaCancion = new Cancion();
                nuevaCancion.setTitulo(titulo);
                nuevaCancion.setDuracionSegundos(duracion);
                nuevaCancion.setAnhoLanzamiento(anho);
                nuevaCancion.setCantanteId(cantanteSeleccionado.getId());

                cancionDAO.addCancion(nuevaCancion);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Canción guardada correctamente");

            } else {
                cancionEnEdicion.setTitulo(titulo);
                cancionEnEdicion.setDuracionSegundos(duracion);
                cancionEnEdicion.setAnhoLanzamiento(anho);

                cancionDAO.updateCancion( cancionEnEdicion);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Canción actualizada correctamente");
            }
            cargarCanciones();
            limpiarFormulario();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "Asegúrate de que la duración y el año son números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de BD", "Ocurrió un error al guardar la canción.");
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancelar() {
        limpiarFormulario();
        tablaCanciones.getSelectionModel().clearSelection();
    }
    @FXML
    private void handleEliminar() {
        Cancion cancion = tablaCanciones.getSelectionModel().getSelectedItem();
        if (cancion != null) {
            try {
                cancionDAO.deleteCancion(cancion.getId());
                cargarCanciones();
                limpiarFormulario();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Canción eliminada.");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la canción.");
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección", "Selecciona una canción para eliminar.");
        }
    }
    @FXML
    private void handleVolver(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cantante-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Carga", "No se pudo cargar la vista de Cantantes.");
            e.printStackTrace();
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
