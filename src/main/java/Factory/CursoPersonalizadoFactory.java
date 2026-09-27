package Factory;

import Model.Academia;
import Model.Curso;
import Model.CursoPersonalizado;
import Model.Estado;
import Model.Nivel;
import Model.Profesor;

public class CursoPersonalizadoFactory
        extends CursoFactory {

    private Academia academia;

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;

    private int cantidadSesiones;
    private Nivel nivelReferencia;
    private String objetivosEstudiante;
    private Profesor profesorAsignado;

    public CursoPersonalizadoFactory(
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
            Profesor profesorAsignado) {

        this.academia = Academia.getInstance(
                "Lenguaje Cafetero",
                "123",
                "Fuadadores"
        );

        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;

        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivosEstudiante = objetivosEstudiante;
        this.profesorAsignado = profesorAsignado;
    }

    @Override
    public Curso crearCurso() {

        CursoPersonalizado curso =
                new CursoPersonalizado.Builder()
                        .codigo(codigo)
                        .nombre(nombre)
                        .idioma(idioma)
                        .descripcion(descripcion)
                        .duracionMeses(duracionMeses)
                        .valorMensual(valorMensual)
                        .estado(estado)
                        .cantidadSesiones(cantidadSesiones)
                        .nivelReferencia(nivelReferencia)
                        .objetivosEstudiante(
                                objetivosEstudiante)
                        .profesorAsignado(
                                profesorAsignado)
                        .build();

        academia.agregarCurso(curso);

        return curso;
    }
}