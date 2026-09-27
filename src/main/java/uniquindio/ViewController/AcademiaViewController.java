package uniquindio.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import uniquindio.App;
import uniquindio.Controller.*;
import uniquindio.Model.Academia;

import java.io.IOException;


public class AcademiaViewController {

    private App app;
    private AcademiaController academiaController;
    private Academia academia;

    @FXML
    private StackPane contenedorContenido;

    @FXML
    private void mostrarVistaEstudiantes() {
        cargarVista("Estudiantes.fxml");
    }

    @FXML
    private void mostrarVistaProfesores() {
        cargarVista("Profesores.fxml");
    }

    @FXML
    private void mostrarVistaCursos() {
        cargarVista("Cursos.fxml");
    }
    @FXML
    private void mostrarVistaMatricula() {
        cargarVista("Matricula.fxml");
    }

    @FXML
    private void mostrarVistaReportes() {
        cargarVista("Reportes.fxml");
    }

    private void cargarVista(String nombreArchivo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/uniquindio/" + nombreArchivo)
            );

            Parent vista = loader.load();

            switch (nombreArchivo) {
                case "Estudiantes.fxml" -> {
                    EstudianteViewController controller =
                            loader.getController();

                    controller.setEstudianteController(
                            new EstudianteController(academia)
                    );
                } // Esta llave faltaba

                case "Profesores.fxml" -> {
                    ProfesorViewController controller =
                            loader.getController();

                    controller.setProfesorController(
                            new ProfesorController(academia)
                    );
                }
                case "Cursos.fxml" -> {
                    CursoViewController controller =
                            loader.getController();

                    controller.setCursoController(
                            new CursoController(academia)
                    );
                }
                case "Matricula.fxml" -> {
                    MatriculaViewController controller =
                            loader.getController();

                    controller.setMatriculaController(
                            new MatriculaController(academia)
                    );
                }
                case "Reportes.fxml" -> {
                    ReportesViewController controller =
                            loader.getController();
                    controller.setReporteController(
                            new ReportesController(academia)
                    );
                }

            }

            contenedorContenido.getChildren().setAll(vista);

        } catch (IOException | IllegalStateException e) {
            System.err.println("No se pudo cargar " + nombreArchivo);
            e.printStackTrace();
        }
    }
    public void setApp(App app){
        this.app=app;
        this.academiaController= new AcademiaController(app);
        this.academia = app.getAcademia();
    }
}
