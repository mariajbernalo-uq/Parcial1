package Model;

public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private Nivel nivelReferencia;
    private String objetivosEstudiante;
    private Profesor profesorAsignado;
    private double costoCursoPersonalizado;

    private CursoPersonalizado(Builder builder) {

        super(builder);

        this.cantidadSesiones = builder.cantidadSesiones;
        this.nivelReferencia = builder.nivelReferencia;
        this.objetivosEstudiante = builder.objetivosEstudiante;
        this.profesorAsignado = builder.profesorAsignado;

        calcularCursoPersonalizado();
    }

    public void calcularCursoPersonalizado() {

        if (profesorAsignado != null) {

            costoCursoPersonalizado =
                    profesorAsignado.getTarifaPorSesion()
                            * cantidadSesiones;

        } else {

            costoCursoPersonalizado = 0;
        }
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
        calcularCursoPersonalizado();
    }

    public Nivel getNivelReferencia() {
        return nivelReferencia;
    }

    public void setNivelReferencia(Nivel nivelReferencia) {
        this.nivelReferencia = nivelReferencia;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(
            String objetivosEstudiante) {

        this.objetivosEstudiante = objetivosEstudiante;
    }

    public Profesor getProfesorAsignado() {
        return profesorAsignado;
    }

    public void setProfesorAsignado(
            Profesor profesorAsignado) {

        this.profesorAsignado = profesorAsignado;
        calcularCursoPersonalizado();
    }

    public double getCostoCursoPersonalizado() {
        return costoCursoPersonalizado;
    }

    public static class Builder
            extends Curso.Builder<Builder> {

        private int cantidadSesiones;
        private Nivel nivelReferencia;
        private String objetivosEstudiante;
        private Profesor profesorAsignado;

        public Builder cantidadSesiones(
                int cantidadSesiones) {

            this.cantidadSesiones = cantidadSesiones;
            return this;
        }

        public Builder nivelReferencia(
                Nivel nivelReferencia) {

            this.nivelReferencia = nivelReferencia;
            return this;
        }

        public Builder objetivosEstudiante(
                String objetivosEstudiante) {

            this.objetivosEstudiante =
                    objetivosEstudiante;

            return this;
        }

        public Builder profesorAsignado(
                Profesor profesorAsignado) {

            this.profesorAsignado =
                    profesorAsignado;

            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CursoPersonalizado build() {
            return new CursoPersonalizado(this);
        }
    }
}