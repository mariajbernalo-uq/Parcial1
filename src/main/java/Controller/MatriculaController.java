package Controller;

import Model.*;

import java.time.LocalDate;
import java.util.List;

public class MatriculaController {

    private Academia academia;

    public MatriculaController(
            Academia academia) {

        this.academia = academia;
    }


    // ==========================================
    // BUSCAR ESTUDIANTE
    // ==========================================

    public Estudiante buscarEstudiante(
            String documento) {

        return academia.buscarEstudiante(
                documento
        );
    }


    // ==========================================
    // LISTAR CURSOS
    // ==========================================

    public List<Curso> listarCursos() {

        return academia.getListCursos();
    }


    // ==========================================
    // LISTAR SERVICIOS
    // ==========================================

    public List<ServicioAdicional>
    listarServicios() {

        /*
         * Los servicios todavía no tienen
         * una lista propia en Academia.
         *
         * Por ahora se obtendrán desde
         * los cursos registrados.
         */

        return obtenerServiciosDeCursos();
    }


    private List<ServicioAdicional>
    obtenerServiciosDeCursos() {

        java.util.ArrayList<ServicioAdicional>
                servicios =
                new java.util.ArrayList<>();

        for (Curso curso :
                academia.getListCursos()) {

            if (curso.getListaBeneficios() != null) {

                for (ServicioAdicional servicio :
                        curso.getListaBeneficios()) {

                    if (!servicios.contains(servicio)) {

                        servicios.add(servicio);
                    }
                }
            }
        }

        return servicios;
    }


    // ==========================================
    // LISTAR PROFESORES
    // ==========================================

    public List<Profesor>
    listarProfesores() {

        return academia.getListProfesores();
    }


    // ==========================================
    // REGISTRAR MATRÍCULA
    // ==========================================

    public Matricula registrarMatricula(
            String codigo,
            Estudiante estudiante,
            int duracion,
            double descuento,
            List<Curso> cursos,
            List<ServicioAdicional> servicios) {

        Matricula.Builder builder =
                new Matricula.Builder()
                        .codigo(codigo)
                        .estudiante(estudiante)
                        .fechaMatricula(
                                LocalDate.now()
                        )
                        .duracionContratada(
                                duracion
                        )
                        .descuento(
                                descuento
                        )
                        .cursos(cursos)
                        .servicios(servicios);


        Matricula matricula =
                builder.build();


        academia.agregarMatricula(
                matricula
        );


        return matricula;
    }


    // ==========================================
    // BUSCAR MATRÍCULA
    // ==========================================

    public Matricula buscarMatricula(
            String codigo) {

        for (Matricula matricula :
                academia.getListMatriculas()) {

            if (matricula.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return matricula;
            }
        }

        return null;
    }


    // ==========================================
    // ACTUALIZAR MATRÍCULA
    // ==========================================

    public boolean actualizarMatricula(
            String codigoOriginal,
            String codigo,
            Estudiante estudiante,
            int duracion,
            double descuento,
            List<Curso> cursos,
            List<ServicioAdicional> servicios) {

        Matricula matricula =
                buscarMatricula(
                        codigoOriginal
                );


        if (matricula == null) {

            return false;
        }


        matricula.setCodigo(codigo);

        matricula.setEstudiante(
                estudiante
        );

        matricula.setDuracionContratada(
                duracion
        );

        matricula.setDescuento(
                descuento
        );


        matricula.getListaCursos()
                .clear();

        if (cursos != null) {

            matricula.getListaCursos()
                    .addAll(cursos);
        }


        matricula.getServiciosAdicionales()
                .clear();

        if (servicios != null) {

            matricula.getServiciosAdicionales()
                    .addAll(servicios);
        }


        return true;
    }


    // ==========================================
    // ELIMINAR MATRÍCULA
    // ==========================================

    public boolean eliminarMatricula(
            String codigo) {

        Matricula matricula =
                buscarMatricula(codigo);


        if (matricula != null) {

            academia.getListMatriculas()
                    .remove(matricula);

            return true;
        }

        return false;
    }


    // ==========================================
    // ASIGNAR PROFESOR
    // ==========================================

    public boolean asignarProfesor(
            String codigoMatricula,
            Profesor profesor) {

        Matricula matricula =
                buscarMatricula(
                        codigoMatricula
                );


        if (matricula == null
                || profesor == null) {

            return false;
        }


        matricula.asignarProfesor(
                profesor
        );

        return true;
    }


    // ==========================================
    // LISTAR MATRÍCULAS
    // ==========================================

    public List<Matricula>
    listarMatriculas() {

        return academia.getListMatriculas();
    }
}