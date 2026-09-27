package Model;

public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private Nivel nivelReferencia;
    private String objetivosEstudiante;

    private CursoPersonalizado(Builder builder) {
        super(builder);

        this.cantidadSesiones = builder.cantidadSesiones;
        this.nivelReferencia = builder.nivelReferencia;
        this.objetivosEstudiante = builder.objetivosEstudiante;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public Nivel getNivelReferencia() {
        return nivelReferencia;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public static class Builder extends Curso.Builder<Builder> {

        private int cantidadSesiones;
        private Nivel nivelReferencia;
        private String objetivosEstudiante;

        public Builder cantidadSesiones(int cantidadSesiones) {
            this.cantidadSesiones = cantidadSesiones;
            return this;
        }

        public Builder nivelReferencia(Nivel nivelReferencia) {
            this.nivelReferencia = nivelReferencia;
            return this;
        }

        public Builder objetivosEstudiante(String objetivosEstudiante) {
            this.objetivosEstudiante = objetivosEstudiante;
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