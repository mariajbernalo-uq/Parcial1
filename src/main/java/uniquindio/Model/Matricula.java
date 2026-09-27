package uniquindio.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matricula {
    private final String codigo;
    private final Estudiante estudiante;
    private final List<Curso> listaCursos;
    private final LocalDate fechaMatricula;
    private final int duracionContratada;
    private double descuento;

    private final List<ServicioAdicional> serviciosAdicionales;
    private final Map<String, Profesor> profesorPorCurso;

    private final List<Pago> pagos;
    private double valorTotalMatricula;

    private Matricula(Builder builder) {
        this.codigo = builder.codigo;
        this.estudiante = builder.estudiante;
        this.listaCursos = new ArrayList<>(builder.listaCursos);
        this.fechaMatricula = builder.fechaMatricula;
        this.duracionContratada = builder.duracionContratada;
        this.descuento = builder.descuento;

        this.serviciosAdicionales =
                new ArrayList<>(builder.serviciosAdicionales);

        this.profesorPorCurso = new HashMap<>();
        this.pagos = new ArrayList<>();

        calcularValorMatricula();
    }

    // -------------------- CURSOS --------------------

    public boolean agregarCurso(Curso curso) {
        if (curso == null || contieneCurso(curso.getCodigo())) {
            return false;
        }

        listaCursos.add(curso);
        calcularValorMatricula();
        return true;
    }

    public boolean eliminarCurso(String codigoCurso) {
        boolean eliminado = listaCursos.removeIf(
                curso -> curso.getCodigo().equals(codigoCurso)
        );

        if (eliminado) {
            profesorPorCurso.remove(codigoCurso);
            calcularValorMatricula();
        }

        return eliminado;
    }

    private boolean contieneCurso(String codigoCurso) {
        for (Curso curso : listaCursos) {
            if (curso.getCodigo().equals(codigoCurso)) {
                return true;
            }
        }
        return false;
    }

    // -------------------- PROFESORES --------------------

    public boolean asignarProfesor(
            String codigoCurso,
            Profesor profesor
    ) {
        Curso curso = buscarCursoEnMatricula(codigoCurso);

        if (!(curso instanceof CursoPersonalizado)
                || profesor == null
                || profesor.getIdiomaQueEnsenia() != curso.getIdioma()) {
            return false;
        }

        profesorPorCurso.put(codigoCurso, profesor);
        calcularValorMatricula();
        return true;
    }

    public boolean retirarProfesor(String codigoCurso) {
        if (profesorPorCurso.remove(codigoCurso) == null) {
            return false;
        }

        calcularValorMatricula();
        return true;
    }

    public Profesor getProfesorAsignado(String codigoCurso) {
        return profesorPorCurso.get(codigoCurso);
    }

    private Curso buscarCursoEnMatricula(String codigoCurso) {
        for (Curso curso : listaCursos) {
            if (curso.getCodigo().equals(codigoCurso)) {
                return curso;
            }
        }
        return null;
    }

    // -------------------- SERVICIOS ADICIONALES --------------------

    public boolean agregarServicio(ServicioAdicional servicio) {
        if (servicio == null) {
            return false;
        }

        serviciosAdicionales.add(servicio);
        calcularValorMatricula();
        return true;
    }

    public boolean eliminarServicio(ServicioAdicional servicio) {
        boolean eliminado = serviciosAdicionales.remove(servicio);

        if (eliminado) {
            calcularValorMatricula();
        }

        return eliminado;
    }

    // -------------------- DESCUENTO --------------------

    public void aplicarDescuento(double descuento) {
        if (!Double.isFinite(descuento) || descuento < 0) {
            throw new IllegalArgumentException(
                    "El descuento no puede ser negativo."
            );
        }

        this.descuento = descuento;
        calcularValorMatricula();
    }

    // -------------------- PAGOS --------------------

    public boolean registrarPago(Pago pago) {
        if (pago == null) {
            return false;
        }

        pagos.add(pago);
        return true;
    }

    // -------------------- CÁLCULOS --------------------

    public double calcularValorMatricula() {
        double valorCursos = 0;
        double valorProfesores = 0;

        for (Curso curso : listaCursos) {
            valorCursos += curso.calcularValor(duracionContratada
            );

            if (curso instanceof CursoPersonalizado personalizado) {
                Profesor profesor =
                        profesorPorCurso.get(curso.getCodigo());

                if (profesor != null) {
                    valorProfesores +=
                            profesor.calcularValorSesiones(
                                    personalizado.getCantidadSesiones()
                            );
                }
            }
        }

        double valorServicios = 0;

        for (ServicioAdicional servicio : serviciosAdicionales) {
            valorServicios += servicio.getPrecio();
        }

        valorTotalMatricula = Math.max(
                0,
                valorCursos + valorProfesores
                        + valorServicios - descuento
        );

        return valorTotalMatricula;
    }

    public double calcularTotal() {
        return calcularValorMatricula();
    }

    // -------------------- GETTERS --------------------

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public List<Curso> getListaCursos() {
        return List.copyOf(listaCursos);
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
        return List.copyOf(serviciosAdicionales);
    }

    public List<Pago> getPagos() {
        return List.copyOf(pagos);
    }

    public double getValorTotal() {
        return calcularValorMatricula();
    }

    public String getCodigo() {
        return codigo;
    }

    // -------------------- BUILDER --------------------

    public static class Builder {

        private Estudiante estudiante;
        private String codigo;
        private final List<Curso> listaCursos = new ArrayList<>();
        private LocalDate fechaMatricula = LocalDate.now();
        private int duracionContratada;
        private double descuento;
        private final List<ServicioAdicional>
                serviciosAdicionales = new ArrayList<>();

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder estudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public Builder curso(Curso curso) {
            if (curso != null) {
                listaCursos.add(curso);
            }
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

        public Builder servicioAdicional(
                ServicioAdicional servicio
        ) {
            if (servicio != null) {
                serviciosAdicionales.add(servicio);
            }
            return this;
        }

        public Matricula build() {
            if (estudiante == null) {
                throw new IllegalArgumentException(
                        "La matrícula necesita un estudiante."
                );
            }

            if (listaCursos.isEmpty()) {
                throw new IllegalArgumentException(
                        "Selecciona al menos un curso."
                );
            }

            if (fechaMatricula == null) {
                throw new IllegalArgumentException(
                        "La fecha de matrícula es obligatoria."
                );
            }

            if (duracionContratada <= 0) {
                throw new IllegalArgumentException(
                        "La duración contratada debe ser mayor que cero."
                );
            }

            if (!Double.isFinite(descuento)
                    || descuento < 0) {
                throw new IllegalArgumentException(
                        "El descuento no puede ser negativo."
                );
            }

            if (codigo == null || codigo.isBlank()) {
                throw new IllegalArgumentException(
                        "El código de la matrícula es obligatorio."
                );
            }

            return new Matricula(this);
        }
    }
}