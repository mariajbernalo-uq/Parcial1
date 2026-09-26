package uniquindio.Controller;

import uniquindio.Model.Academia;
import uniquindio.Model.Idioma;
import uniquindio.Model.Profesor;

import java.util.List;

public class ProfesorController {

    private final Academia academia;

    public ProfesorController(Academia academia) {
        this.academia = academia;
    }

    // Métodos de Academia

    public boolean registrarProfesor(String nombre,
                                     String documento,
                                     String telefono,
                                     Idioma idiomaQueEnsenia,
                                     double tarifaPorSesion) {
        return academia.crearProfesor(
                nombre,
                documento,
                telefono,
                idiomaQueEnsenia,
                tarifaPorSesion
        );
    }

    public boolean actualizarDatosProfesor(String documento,
                                           Profesor profesor) {
        return academia.actualizarDatosProfesor(documento, profesor);
    }

    public boolean eliminarProfesor(String documento) {
        return academia.eliminarProfesor(documento);
    }

    public Profesor buscarProfesor(String documento) {
        return academia.buscarProfesor(documento);
    }

    public List<Profesor> listarProfesores() {
        return academia.getListProfesores();
    }
}
