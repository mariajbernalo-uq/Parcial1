package ViewController;

import App.App;
import Controller.AcademiaController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AcademiaViewController {

    private App app;
    private AcademiaController academiaController;

    @FXML
    private StackPane contenedorContenido;

    public void setApp(App app) {
        this.app = app;

        this.academiaController =
                new AcademiaController(this);
    }

    @FXML
    private void mostrarVistaEstudiantes() {
        academiaController.abrirEstudiantes();
    }

    @FXML
    private void mostrarVistaProfesores() {
        academiaController.abrirProfesores();
    }

    @FXML
    private void mostrarVistaCursos() {
        academiaController.abrirCursos();
    }

    @FXML
    private void mostrarVistaMatricula() {
        academiaController.abrirMatricula();
    }

    @FXML
    private void mostrarVistaReportes() {
        academiaController.abrirReportes();
    }

    public void cargarVista(String nombreArchivo) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/co/uniquindio/parcial1/" + nombreArchivo
                    )
            );

            Parent vista = loader.load();

            contenedorContenido.getChildren().setAll(vista);

        } catch (IOException | NullPointerException e) {

            System.err.println(
                    "No se pudo cargar la vista: " + nombreArchivo
            );

            e.printStackTrace();
        }
    }
}