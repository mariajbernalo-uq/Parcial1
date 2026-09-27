package Model;

public class ServicioAdicional {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    private ServicioAdicional(Builder builder) {
        this.codigo = builder.codigo;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.precio = builder.precio;
        this.disponible = builder.disponible;
    }

    public static class Builder {

        private String codigo;
        private String nombre;
        private String descripcion;
        private double precio;
        private boolean disponible;

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder precio(double precio) {
            this.precio = precio;
            return this;
        }

        public Builder disponible(boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public ServicioAdicional build() {
            return new ServicioAdicional(this);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
