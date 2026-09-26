package Model;

public class Profesor {

    private String nombre;
    private String documentoDeIdentidad;
    private String telefono;
    private Idioma idiomaQueEnsenia;
    private double tarifaPorSesion;


    public Profesor(Builder builder){
        this.nombre = builder.nombre;
        this.documentoDeIdentidad = builder.documentoDeIdentidad;
        this.telefono = builder.telefono;
        this.idiomaQueEnsenia = builder.idiomaQueEnsenia;
        this.tarifaPorSesion = builder.tarifaPorSesion;


    }


    public static class Builder{
        private String nombre;
        private String documentoDeIdentidad;
        private String telefono;
        private Idioma idiomaQueEnsenia;
        private double tarifaPorSesion;

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder documentoDeIdentidad(String documentoDeIdentidad) {
            this.documentoDeIdentidad = documentoDeIdentidad;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder idiomaQueEnsenia(Idioma idiomaQueEnsenia) {
            this.idiomaQueEnsenia = idiomaQueEnsenia;
            return this;
        }

        public Builder tarifaPorSesion(double tarifaPorSesion) {
            this.tarifaPorSesion = tarifaPorSesion;
            return this;
        }


    }


    @Override



    public String toString() {
        return "Profesor{" +
                "nombre='" + nombre + '\'' +
                ", documentoDeIdentidad='" + documentoDeIdentidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", idiomaQueEnsenia='" + idiomaQueEnsenia + '\'' +
                ", tarifaPorSesion=" + tarifaPorSesion +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumentoDeIdentidad() {
        return documentoDeIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public Idioma getIdiomaQueEnsenia() {
        return idiomaQueEnsenia;
    }

    public double getTarifaPorSesion() {
        return tarifaPorSesion;
    }
}
