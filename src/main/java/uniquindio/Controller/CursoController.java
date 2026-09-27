package uniquindio.Controller;

import java.util.List;

import uniquindio.Model.Academia;
import uniquindio.Model.Beneficio;
import uniquindio.Model.Curso;
import uniquindio.Model.Estado;
import uniquindio.Model.Idioma;
import uniquindio.Model.Nivel;

public class CursoController {

    private final Academia academia;

    public CursoController(Academia academia) {
        this.academia = academia;
    }

    // -------------------- CURSOS --------------------

    public boolean registrarCursoRegular(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            List<Beneficio> beneficiosSeleccionados
    ) {
        return academia.crearCursoRegular(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                beneficiosSeleccionados
        );
    }

    public boolean registrarCursoIntensivo(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            List<Beneficio> beneficiosSeleccionados
    ) {
        return academia.crearCursoIntensivo(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                beneficiosSeleccionados
        );
    }

    public boolean registrarCursoPersonalizado(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante,
            List<Beneficio> beneficiosSeleccionados
    ) {
        return academia.crearCursoPersonalizado(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                cantidadSesiones,
                nivelReferencia,
                objetivoEstudiante,
                beneficiosSeleccionados
        );
    }

    public boolean actualizarCurso(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado
    ) {
        return academia.actualizarCurso(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                estado
        );
    }

    public boolean actualizarDatosCursoPersonalizado(
            String codigo,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante
    ) {
        return academia.actualizarDatosCursoPersonalizado(
                codigo,
                cantidadSesiones,
                nivelReferencia,
                objetivoEstudiante
        );
    }

    public boolean eliminarCurso(String codigo) {
        return academia.eliminarCurso(codigo);
    }

    public Curso buscarCurso(String codigo) {
        return academia.buscarCurso(codigo);
    }

    public List<Curso> listarCursos() {
        return academia.getListCursos();
    }

    // -------------------- BENEFICIOS --------------------

    public List<Beneficio> listarBeneficios() {
        return academia.getListBeneficios();
    }

    public Beneficio buscarBeneficio(String codigo) {
        return academia.buscarBeneficio(codigo);
    }

    public boolean crearBeneficio(
            String codigo,
            String nombre,
            String descripcion
    ) {
        return academia.crearBeneficio(
                codigo,
                nombre,
                descripcion
        );
    }

    public boolean actualizarBeneficiosCurso(
            String codigoCurso,
            List<Beneficio> beneficiosSeleccionados
    ) {
        return academia.actualizarBeneficiosCurso(
                codigoCurso,
                beneficiosSeleccionados
        );
    }
}