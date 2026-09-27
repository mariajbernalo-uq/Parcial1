package Controller;

import Factory.CursoIntensivoFactory;
import Factory.CursoPersonalizadoFactory;
import Factory.CursoRegularFactory;

import Model.*;

import java.util.List;

public class CursoController {

    private Academia academia;

    public CursoController(Academia academia) {
        this.academia = academia;
    }

    // =========================
    // REGISTRAR CURSO REGULAR
    // =========================

    public Curso registrarCursoRegular(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado) {

        CursoRegularFactory factory =
                new CursoRegularFactory(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracionMeses,
                        valorMensual,
                        estado
                );

        return factory.crearCurso();
    }

    // =========================
    // REGISTRAR CURSO INTENSIVO
    // =========================

    public Curso registrarCursoIntensivo(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado) {

        CursoIntensivoFactory factory =
                new CursoIntensivoFactory(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracionMeses,
                        valorMensual,
                        estado
                );

        return factory.crearCurso();
    }

    // =========================
    // REGISTRAR CURSO PERSONALIZADO
    // =========================

    public Curso registrarCursoPersonalizado(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivosEstudiante,
            Profesor profesor) {

        CursoPersonalizadoFactory factory =
                new CursoPersonalizadoFactory(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracionMeses,
                        valorMensual,
                        estado,
                        cantidadSesiones,
                        nivelReferencia,
                        objetivosEstudiante,
                        profesor
                );

        return factory.crearCurso();
    }

    // =========================
    // BUSCAR CURSO
    // =========================

    public Curso buscarCurso(String codigo) {

        for (Curso curso : academia.getListCursos()) {

            if (curso.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return curso;
            }
        }

        return null;
    }

    // =========================
    // ACTUALIZAR CURSO
    // =========================

    public boolean actualizarCurso(
            String codigoOriginal,
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado) {

        Curso curso =
                buscarCurso(codigoOriginal);

        if (curso == null) {
            return false;
        }

        /*
         * Se actualizan los datos básicos
         * utilizando los setters de Curso.
         */

        curso.setCodigo(codigo);
        curso.setNombre(nombre);
        curso.setIdioma(idioma);
        curso.setDescripcion(descripcion);
        curso.setDuracionMeses(duracionMeses);
        curso.setValorMensual(valorMensual);
        curso.setEstado(estado);

        return true;
    }

    // =========================
    // ACTUALIZAR CURSO PERSONALIZADO
    // =========================

    public boolean actualizarCursoPersonalizado(
            String codigoOriginal,
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivosEstudiante) {

        Curso curso =
                buscarCurso(codigoOriginal);

        if (!(curso instanceof CursoPersonalizado personalizado)) {
            return false;
        }

        // Datos generales del curso
        personalizado.setCodigo(codigo);
        personalizado.setNombre(nombre);
        personalizado.setIdioma(idioma);
        personalizado.setDescripcion(descripcion);
        personalizado.setDuracionMeses(duracionMeses);
        personalizado.setValorMensual(valorMensual);
        personalizado.setEstado(estado);

        // Datos propios del curso personalizado
        personalizado.setCantidadSesiones(
                cantidadSesiones
        );

        personalizado.setNivelReferencia(
                nivelReferencia
        );

        personalizado.setObjetivosEstudiante(
                objetivosEstudiante
        );

        return true;
    }

    // =========================
    // ELIMINAR CURSO
    // =========================

    public boolean eliminarCurso(String codigo) {

        Curso curso =
                buscarCurso(codigo);

        if (curso != null) {

            academia.getListCursos()
                    .remove(curso);

            return true;
        }

        return false;
    }

    // =========================
    // LISTAR CURSOS
    // =========================

    public List<Curso> listarCursos() {
        return academia.getListCursos();
    }

    // =========================
    // LISTAR PROFESORES
    // =========================

    public List<Profesor> listarProfesores() {
        return academia.getListProfesores();
    }

    // =========================
    // AGREGAR BENEFICIO
    // =========================

    public void agregarBeneficio(
            String codigoCurso,
            ServicioAdicional beneficio) {

        Curso curso =
                buscarCurso(codigoCurso);

        if (curso != null) {

            curso.agregarBeneficio(
                    beneficio
            );
        }
    }

    // =========================
    // ELIMINAR BENEFICIO
    // =========================

    public void eliminarBeneficio(
            String codigoCurso,
            ServicioAdicional beneficio) {

        Curso curso =
                buscarCurso(codigoCurso);

        if (curso != null) {

            curso.eliminarBeneficio(
                    beneficio
            );
        }
    }
}