package Factory;

import Model.*;

public class CursoIntensivoFactory extends CursoFactory {
    Academia academia = Academia.getInstance("Lenguaje Cafetero", "123", "Fuadadores" );
    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;

    public CursoIntensivoFactory(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
    }

    @Override
    public Curso crearCurso() {

        CursoIntensivo curso = new CursoIntensivo.Builder()
                .codigo(codigo)
                .nombre(nombre)
                .idioma(idioma)
                .descripcion(descripcion)
                .duracionMeses(duracionMeses)
                .valorMensual(valorMensual)
                .estado(estado)
                .build();

        academia.agregarCurso(curso);

        return curso;
    }

}
