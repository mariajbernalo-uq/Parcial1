package Factory;

import Model.Academia;
import Model.Curso;
import Model.CursoPersonalizado;
import Model.Estado;

public class CursoPersonalizadoFactory extends CursoFactory {

    Academia academia = Academia.getInstance("Lenguaje Cafetero", "123", "Fuadadores" );


    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;

    private int cantidadSesiones;
    private String nivelReferencia;
    private String objetivosEstudiante;

    public CursoPersonalizadoFactory(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado,
            int cantidadSesiones,
            String nivelReferencia,
            String objetivosEstudiante) {

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
    }

    @Override
    public Curso crearCurso() {

        CursoPersonalizado curso = new CursoPersonalizado.Builder()
                .codigo(codigo)
                .nombre(nombre)
                .idioma(idioma)
                .descripcion(descripcion)
                .duracionMeses(duracionMeses)
                .valorMensual(valorMensual)
                .estado(estado)
                .cantidadSesiones(cantidadSesiones)
                .nivelReferencia(nivelReferencia)
                .objetivosEstudiante(objetivosEstudiante)
                .build();

       academia.agregarCurso(curso);

        return curso;
    }
}

