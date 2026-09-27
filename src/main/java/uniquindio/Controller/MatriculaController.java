package uniquindio.Controller;

import java.util.List;

import uniquindio.Model.Academia;
import uniquindio.Model.Curso;
import uniquindio.Model.Estudiante;
import uniquindio.Model.Matricula;
import uniquindio.Model.Profesor;
import uniquindio.Model.ServicioAdicional;

public class MatriculaController {

    private final Academia academia;

    public MatriculaController(Academia academia) {
        this.academia = academia;
    }

    // -------------------- DATOS PARA EL FORMULARIO --------------------

    public Estudiante buscarEstudiante(String documento) {
        return academia.buscarEstudiante(documento);
    }

    public List<Curso> listarCursos() {
        return academia.getListCursos();
    }

    public List<Profesor> listarProfesores() {
        return academia.getListProfesores();
    }

    public List<ServicioAdicional> listarServicios() {
        return academia.getListServiciosAdicionales();
    }

    // -------------------- CRUD MATRÍCULAS --------------------

    public boolean registrarMatricula(Matricula matricula) {
        return academia.registrarMatricula(matricula);
    }

    public Matricula buscarMatricula(
            String documentoEstudiante
    ) {
        return academia.buscarMatricula(
                documentoEstudiante
        );
    }

    public List<Matricula> listarMatriculas() {
        return academia.getListMatriculas();
    }

    public boolean eliminarMatricula(
            String documentoEstudiante
    ) {
        return academia.eliminarMatricula(
                documentoEstudiante
        );
    }

    // -------------------- ACTUALIZAR MATRÍCULA --------------------

    public boolean actualizarDescuentoMatricula(
            String documentoEstudiante,
            double descuento
    ) {
        return academia.actualizarDescuentoMatricula(
                documentoEstudiante,
                descuento
        );
    }

    public boolean agregarCursoAMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        return academia.agregarCursoAMatricula(
                documentoEstudiante,
                codigoCurso
        );
    }

    public boolean quitarCursoDeMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        return academia.quitarCursoDeMatricula(
                documentoEstudiante,
                codigoCurso
        );
    }

    public boolean asignarProfesorAMatricula(
            String documentoEstudiante,
            String codigoCurso,
            String documentoProfesor
    ) {
        return academia.asignarProfesorAMatricula(
                documentoEstudiante,
                codigoCurso,
                documentoProfesor
        );
    }

    public boolean retirarProfesorDeMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        return academia.retirarProfesorDeMatricula(
                documentoEstudiante,
                codigoCurso
        );
    }

    public boolean agregarServicioAMatricula(
            String documentoEstudiante,
            ServicioAdicional servicio
    ) {
        return academia.agregarServicioAMatricula(
                documentoEstudiante,
                servicio
        );
    }

    public boolean quitarServicioDeMatricula(
            String documentoEstudiante,
            ServicioAdicional servicio
    ) {
        return academia.quitarServicioDeMatricula(
                documentoEstudiante,
                servicio
        );
    }

    // -------------------- CATÁLOGO DE SERVICIOS --------------------

    public boolean crearServicio(
            String codigo,
            String nombre,
            String descripcion,
            double precio
    ) {
        return academia.crearServicioAdicional(
                codigo,
                nombre,
                descripcion,
                precio
        );
    }

    public boolean eliminarServicio(String codigo) {
        return academia.eliminarServicioAdicional(
                codigo
        );
    }

    public boolean cambiarDisponibilidadServicio(
            String codigo,
            boolean disponible
    ) {
        return academia.cambiarDisponibilidadServicio(
                codigo,
                disponible
        );
    }
}