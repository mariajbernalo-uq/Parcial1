package Model;

import Model.Curso;
import Model.Estudiante;
import Model.ServicioAdicional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private Estudiante estudiante;
    private LocalDate fechaMatricula;
    private int duracionContratada;
    private double descuento;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Curso> listCursosMatricula;
    private double valorTotalMatricula;

    private Matricula(Builder builder) {
        this.estudiante = builder.estudiante;
        this.fechaMatricula = builder.fechaMatricula;
        this.duracionContratada = builder.duracionContratada;
        this.descuento = builder.descuento;
        this.serviciosAdicionales = builder.serviciosAdicionales;

        calcularValorMatricula() ;
    }


    /**
     * Agregar curso a matricula
     * @param curso
     */

    public void agregarCurso(Curso curso) {
        listCursosMatricula.add(curso);
        calcularValorMatricula() ;
    }

    public void eliminarCurso(Curso curso) {
        listCursosMatricula.remove(curso);
        calcularValorMatricula() ;
    }


    /**
     * Agragar servicio a la lista de servicios de la matricula
     * @param servicio
     */

    public void agregarServicio(ServicioAdicional servicio) {
        serviciosAdicionales.add(servicio);
        calcularValorMatricula() ;
    }


    public void eliminarServicio(ServicioAdicional servicio) {
        serviciosAdicionales.remove(servicio);
        calcularValorMatricula() ;
    }

    /**
     * Medoto para calcular valor matricula
     */

    private void calcularValorMatricula() {

        double valorCurso = 0;

        for (Curso curso : listCursosMatricula) {
            valorCurso += curso.getValorMensual();
        }

        valorCurso = valorCurso * duracionContratada;

        double valorServicios = 0;

        for (ServicioAdicional servicio : serviciosAdicionales) {
            valorServicios += servicio.getPrecio();
        }

        valorTotalMatricula = valorCurso + valorServicios - descuento;
    }


    public static class Builder {

        private Estudiante estudiante;
        private Curso curso;
        private LocalDate fechaMatricula = LocalDate.now();
        private int duracionContratada;
        private double descuento;
        private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

        public Builder estudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public Builder curso(Curso curso) {
            this.curso = curso;
            return this;
        }

        public Builder fechaMatricula(LocalDate fechaMatricula) {
            this.fechaMatricula = fechaMatricula;
            return this;
        }

        public Builder duracionContratada(int duracionContratada) {
            this.duracionContratada = duracionContratada;
            return this;
        }

        public Builder descuento(double descuento) {
            this.descuento = descuento;
            return this;
        }

        public Builder servicioAdicional(ServicioAdicional servicio) {
            this.serviciosAdicionales.add(servicio);
            return this;
        }

        public Matricula build() {
            return new Matricula(this);
        }
    }




    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public int getDuracionContratada() {
        return duracionContratada;
    }

    public double getDescuento() {
        return descuento;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public double getValorTotal() {
        return valorTotalMatricula;
    }
}