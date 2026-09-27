package Model;

public class CursoIntensivo extends Curso {

    private CursoIntensivo(Builder builder) {
        super(builder);
    }

    public static class Builder extends Curso.Builder<Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CursoIntensivo build() {
            return new CursoIntensivo(this);
        }
    }
}
