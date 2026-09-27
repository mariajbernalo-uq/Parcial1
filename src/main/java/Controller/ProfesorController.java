package Controller;

import Model.Academia;
import Model.Idioma;
import Model.Profesor;

import java.util.List;

public class ProfesorController {

    private Academia academia;

    public ProfesorController(Academia academia) {
        this.academia = academia;
    }

    public void registrarProfesor(
            String nombre,
            String documento,
            String telefono,
            Idioma idioma,
            double tarifaPorSesion
    ) {

        academia.crearProfesor(
                nombre,
                documento,
                telefono,
                idioma,
                tarifaPorSesion
        );
    }

    public Profesor buscarProfesor(String documento) {

        return academia.buscarProfesor(documento);
    }

    public void actualizarProfesor(
            String documento,
            Profesor profesorActualizado
    ) {

        academia.actualizarProfesor(
                documento,
                profesorActualizado
        );
    }

    public void eliminarProfesor(String documento) {

        academia.eliminarProfesor(documento);
    }

    public List<Profesor> listarProfesores() {

        return academia.getListProfesores();
    }
}