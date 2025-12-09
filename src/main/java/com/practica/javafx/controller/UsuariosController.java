package com.practica.javafx.controller;

import com.practica.javafx.dao.CantanteDAO;
import com.practica.javafx.dao.CantanteDAOImpl;
import com.practica.javafx.dao.FavoritoDAO;
import com.practica.javafx.dao.FavoritoDAOImpl;
import com.practica.javafx.model.Cantante;
import com.practica.javafx.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField tfBuscador;

    @FXML
    private TableView<Cantante> tablaBusquedaCantantes;
    @FXML
    private TableColumn<Cantante, String> colNombreBusqueda;
    @FXML
    private TableColumn<Cantante, String> colApellidoBusqueda;
    @FXML
    private TableColumn<Cantante, String> colNombreArtisticoBusqueda;
    @FXML
    private Button btnAnadirFavorito;

    @FXML
    private TableView<Cantante> tablaCantantesFavoritos;
    @FXML
    private TableColumn<Cantante, String> colNombreFavorito;
    @FXML
    private TableColumn<Cantante, String> colNombreArtisticoFavorito;
    @FXML
    private Button btnEliminarFavorito;

    private CantanteDAO cantanteDAO;
    private FavoritoDAO favoritoDAO;
    private ObservableList<Cantante> listaBusquedaCantantes;
    private ObservableList<Cantante> listaCantantesFavoritos;

    private Usuario usuarioLogueado;

    private Timeline debounceTimeline;
    private static final int DEBOUNCE_DELAY_MS = 300;

    public UsuariosController() {
        this.cantanteDAO = new CantanteDAOImpl();
        this.favoritoDAO = new FavoritoDAOImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar listas
        listaBusquedaCantantes = FXCollections.observableArrayList();
        listaCantantesFavoritos = FXCollections.observableArrayList();

        // Configurar columnas de la tabla busqueda
        colNombreBusqueda.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoBusqueda.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colNombreArtisticoBusqueda.setCellValueFactory(new PropertyValueFactory<>("nombreArtistico"));
        tablaBusquedaCantantes.setItems(listaBusquedaCantantes);

        // Configurar columnas de la tabla favoritos
        colNombreFavorito.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreArtisticoFavorito.setCellValueFactory(new PropertyValueFactory<>("nombreArtistico"));
        tablaCantantesFavoritos.setItems(listaCantantesFavoritos);

        // Configurar timeline debouncing
        debounceTimeline = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), event -> {
            buscarCantantes(tfBuscador.getText());
        }));
        debounceTimeline.setCycleCount(1);

        // Configurar listener
        tfBuscador.textProperty().addListener((observable, oldValue, newValue) -> {
            debounceTimeline.stop();
            debounceTimeline.playFromStart();
        });
        tablaBusquedaCantantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) ->
                btnAnadirFavorito.setDisable(newSel == null)
        );
        tablaCantantesFavoritos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) ->
                btnEliminarFavorito.setDisable(newSel == null)
        );

        btnAnadirFavorito.setDisable(true);
        btnEliminarFavorito.setDisable(true);
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        if (usuarioLogueado != null) {
            welcomeLabel.setText("Hola, " + usuarioLogueado.getNickname());
        }
        if (usuarioLogueado != null && usuarioLogueado.getCantantesFavoritos() != null) {
            listaCantantesFavoritos.addAll(usuarioLogueado.getCantantesFavoritos());
        }
    }
    public void buscarCantantes(String terminoBusqueda) {
        listaBusquedaCantantes.clear();
        if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
            List<Cantante> cantantesEncontrados = cantanteDAO.buscarCantantePorNombreOApodo(terminoBusqueda);
            listaBusquedaCantantes.addAll(cantantesEncontrados);
        }
    }
    @FXML
    private void handleAnadirFavorito() {
        Cantante cantanteSeleccionado = tablaBusquedaCantantes.getSelectionModel().getSelectedItem(); // Adaptado
        if (cantanteSeleccionado != null) {
            if (usuarioLogueado != null) {
                if (!listaCantantesFavoritos.contains(cantanteSeleccionado)) {
                    favoritoDAO.addCantanteFavorito(usuarioLogueado.getUsuarioId(), cantanteSeleccionado.getId());
                    listaCantantesFavoritos.add(cantanteSeleccionado);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Cantante Añadido", "El cantante ha sido " +
                            "añadido a tus favoritos.");
                }else {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Cantante ya es favorito", "Este cantante " +
                            "ya está en tu lista de favoritos.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay usuario logueado.");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Ningún Cantante Seleccionado", "Por favor, selecciona" +
                    " un cantante de la lista de búsqueda.");
            }
    }
    @FXML
    private void handleEliminarFavorito() {
        Cantante cantanteSeleccionado = tablaCantantesFavoritos.getSelectionModel().getSelectedItem(); // Adaptado
        if (cantanteSeleccionado != null) {
            if (usuarioLogueado != null) {
                // Adaptado: Usamos removeCantanteFavorito y getUsuarioId()
                favoritoDAO.removeCantanteFavorito(usuarioLogueado.getUsuarioId(), cantanteSeleccionado.getId());
                listaCantantesFavoritos.remove(cantanteSeleccionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cantante Eliminado", "El cantante ha sido eliminado de tus favoritos.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay usuario logueado.");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Ningún Favorito Seleccionado", "Por favor, selecciona un cantante de tu lista de favoritos.");
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
