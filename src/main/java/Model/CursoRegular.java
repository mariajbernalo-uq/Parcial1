package Model;

public class CursoRegular extends Curso {

    private CursoRegular(Builder builder) {
        super(builder);
    }

    public static class Builder extends Curso.Builder<Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CursoRegular build() {
            return new CursoRegular(this);
        }
    }
}
