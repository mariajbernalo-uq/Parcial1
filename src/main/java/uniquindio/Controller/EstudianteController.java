package uniquindio.Controller;

import uniquindio.App;
import uniquindio.Model.Academia;
import uniquindio.Model.Estudiante;

import java.time.LocalDate;
import java.util.List;

public class EstudianteController {
    private final Academia academia;

    public EstudianteController(Academia academia){
        this.academia=academia;
    }

    //Métodos de Academia
    public boolean registrarEstudiante(String nombre,
                             String documento,
                             String telefono,
                             String correo,
                             int edad,
                             LocalDate fechaRegistro) {
        return academia.crearEstudiante(
                nombre,
                documento,
                telefono,
                correo,
                edad,
                fechaRegistro
        );
    }
    public boolean actualizarDatosEstudiante(String documento, Estudiante estudiante) {
        return academia.actualizarDatosEstudiante(documento, estudiante);
    }
    public boolean eliminarEstudiante(String documento) {
        return academia.eliminarEstudiante(documento);
    }
    public Estudiante buscarEstudiante(String documento) {
        return academia.buscarEstudiante(documento);
    }
    public List<Estudiante> listarEstudiantes() {
        return academia.getListEstudiantes();
    }

}
