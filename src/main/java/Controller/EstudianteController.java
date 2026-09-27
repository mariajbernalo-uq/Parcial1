package Controller;

import Model.Academia;
import Model.Estudiante;

import java.time.LocalDate;
import java.util.List;

public class EstudianteController {

    private Academia academia;

    public EstudianteController(Academia academia) {
        this.academia = academia;
    }

    public void registrarEstudiante(
            String nombre,
            String documento,
            String telefono,
            String correo,
            int edad
    ) {

        academia.crearEstudiante(
                nombre,
                documento,
                telefono,
                correo,
                edad,
                LocalDate.now()
        );
    }

    public Estudiante buscarEstudiante(String documento) {

        return academia.buscarEstudiante(documento);
    }

    public void actualizarDatosEstudiante(
            String documento,
            Estudiante estudianteActualizado
    ) {

        academia.actualizarDatosEstudiante(
                documento,
                estudianteActualizado
        );
    }

    public void eliminarEstudiante(String documento) {

        academia.eliminarEstudiante(documento);
    }

    public List<Estudiante> listarEstudiantes() {

        return academia.getListEstudiantes();
    }
}