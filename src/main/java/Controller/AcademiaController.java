package Controller;

import ViewController.AcademiaViewController;

public class AcademiaController {

    private AcademiaViewController academiaViewController;

    public AcademiaController(AcademiaViewController academiaViewController) {
        this.academiaViewController = academiaViewController;
    }

    public void abrirEstudiantes() {
        academiaViewController.cargarVista("Estudiantes.fxml");
    }

    public void abrirProfesores() {
        academiaViewController.cargarVista("Profesor.fxml");
    }

    public void abrirCursos() {
        academiaViewController.cargarVista("Cursos.fxml");
    }

    public void abrirMatricula() {
        academiaViewController.cargarVista("Matricula.fxml");
    }

    public void abrirReportes() {
        academiaViewController.cargarVista("Reportes.fxml");
    }
}