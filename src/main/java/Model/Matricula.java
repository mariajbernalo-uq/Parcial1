package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private String codigo;
    private Estudiante estudiante;
    private LocalDate fechaMatricula;
    private int duracionContratada;
    private double descuento;

    private List<Curso> listaCursos;
    private List<ServicioAdicional> serviciosAdicionales;

    private List<Profesor> profesoresAsignados;

    private double valorTotalMatricula;


    private Matricula(Builder builder) {

        this.codigo = builder.codigo;
        this.estudiante = builder.estudiante;
        this.fechaMatricula = builder.fechaMatricula;
        this.duracionContratada = builder.duracionContratada;
        this.descuento = builder.descuento;

        this.listaCursos =
                new ArrayList<>(builder.listaCursos);

        this.serviciosAdicionales =
                new ArrayList<>(builder.serviciosAdicionales);

        this.profesoresAsignados =
                new ArrayList<>(builder.profesoresAsignados);

        calcularValorMatricula();
    }

    /**
     * Metodo ppara calcular valor total de matricula
     */

    private void calcularValorMatricula() {

        double valorCursos = 0;

        for (Curso curso : listaCursos) {

            valorCursos +=
                    curso.getValorMensual();
        }

        valorCursos =
                valorCursos * duracionContratada;


        double valorServicios = 0;

        for (ServicioAdicional servicio :
                serviciosAdicionales) {

            valorServicios +=
                    servicio.getPrecio();
        }


        valorTotalMatricula =
                valorCursos
                        + valorServicios
                        - descuento;
    }

    /**
     * Agregar cursos a matricula
     * @param curso
     */

    public void agregarCurso(Curso curso) {

        if (curso != null) {

            listaCursos.add(curso);

            calcularValorMatricula();
        }
    }


    public void eliminarCurso(Curso curso) {

        if (curso != null) {

            listaCursos.remove(curso);

            calcularValorMatricula();
        }
    }


    /**
     * Agragar servicio a matricula
     * @param servicio
     */

    public void agregarServicio(
            ServicioAdicional servicio) {

        if (servicio != null) {

            serviciosAdicionales.add(servicio);

            calcularValorMatricula();
        }
    }


    public void eliminarServicio(
            ServicioAdicional servicio) {

        if (servicio != null) {

            serviciosAdicionales.remove(servicio);

            calcularValorMatricula();
        }
    }


    /**
     * Asignar profesor a matricula
     * @param profesor
     */

    public void asignarProfesor(
            Profesor profesor) {

        if (profesor != null) {

            profesoresAsignados.add(profesor);
        }
    }


    public void eliminarProfesor(
            Profesor profesor) {

        if (profesor != null) {

            profesoresAsignados.remove(profesor);
        }
    }







    public static class Builder {

        private String codigo;

        private Estudiante estudiante;

        private LocalDate fechaMatricula =
                LocalDate.now();

        private int duracionContratada;

        private double descuento;

        private List<Curso> listaCursos =
                new ArrayList<>();

        private List<ServicioAdicional>
                serviciosAdicionales =
                new ArrayList<>();

        private List<Profesor>
                profesoresAsignados =
                new ArrayList<>();


        public Builder codigo(String codigo) {

            this.codigo = codigo;

            return this;
        }


        public Builder estudiante(
                Estudiante estudiante) {

            this.estudiante = estudiante;

            return this;
        }


        public Builder fechaMatricula(
                LocalDate fechaMatricula) {

            this.fechaMatricula =
                    fechaMatricula;

            return this;
        }


        public Builder duracionContratada(
                int duracionContratada) {

            this.duracionContratada =
                    duracionContratada;

            return this;
        }


        public Builder descuento(
                double descuento) {

            this.descuento =
                    descuento;

            return this;
        }


        public Builder curso(Curso curso) {

            if (curso != null) {

                this.listaCursos.add(curso);
            }

            return this;
        }


        public Builder cursos(
                List<Curso> cursos) {

            if (cursos != null) {

                this.listaCursos =
                        new ArrayList<>(cursos);
            }

            return this;
        }


        public Builder servicioAdicional(
                ServicioAdicional servicio) {

            if (servicio != null) {

                this.serviciosAdicionales
                        .add(servicio);
            }

            return this;
        }


        public Builder servicios(
                List<ServicioAdicional> servicios) {

            if (servicios != null) {

                this.serviciosAdicionales =
                        new ArrayList<>(servicios);
            }

            return this;
        }


        public Builder profesor(
                Profesor profesor) {

            if (profesor != null) {

                this.profesoresAsignados
                        .add(profesor);
            }

            return this;
        }


        public Builder profesores(
                List<Profesor> profesores) {

            if (profesores != null) {

                this.profesoresAsignados =
                        new ArrayList<>(profesores);
            }

            return this;
        }


        public Matricula build() {

            return new Matricula(this);
        }
    }

    public String getCodigo() {
        return codigo;
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

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public List<ServicioAdicional>
    getServiciosAdicionales() {

        return serviciosAdicionales;
    }

    public List<Profesor> getProfesoresAsignados() {
        return profesoresAsignados;
    }

    public double getValorTotal() {
        return valorTotalMatricula;
    }




    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstudiante(
            Estudiante estudiante) {

        this.estudiante = estudiante;
    }

    public void setFechaMatricula(
            LocalDate fechaMatricula) {

        this.fechaMatricula =
                fechaMatricula;
    }

    public void setDuracionContratada(
            int duracionContratada) {

        this.duracionContratada =
                duracionContratada;

        calcularValorMatricula();
    }

    public void setDescuento(
            double descuento) {

        this.descuento = descuento;

        calcularValorMatricula();
    }
}