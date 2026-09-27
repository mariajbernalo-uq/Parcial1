package Model;

public class Curso {

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;

    protected Curso(Builder<?> builder) {
        this.codigo = builder.codigo;
        this.nombre = builder.nombre;
        this.idioma = builder.idioma;
        this.descripcion = builder.descripcion;
        this.duracionMeses = builder.duracionMeses;
        this.valorMensual = builder.valorMensual;
        this.estado = builder.estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public Estado getEstado() {
        return estado;
    }

    public static abstract class Builder<T extends Builder<T>> {

        private String codigo;
        private String nombre;
        private String idioma;
        private String descripcion;
        private int duracionMeses;
        private double valorMensual;
        private Estado estado;

        public T codigo(String codigo) {
            this.codigo = codigo;
            return self();
        }

        public T nombre(String nombre) {
            this.nombre = nombre;
            return self();
        }

        public T idioma(String idioma) {
            this.idioma = idioma;
            return self();
        }

        public T descripcion(String descripcion) {
            this.descripcion = descripcion;
            return self();
        }

        public T duracionMeses(int duracionMeses) {
            this.duracionMeses = duracionMeses;
            return self();
        }

        public T valorMensual(double valorMensual) {
            this.valorMensual = valorMensual;
            return self();
        }

        public T estado(Estado estado) {
            this.estado = estado;
            return self();
        }

        protected abstract T self();

        public abstract Curso build();
    }
}
