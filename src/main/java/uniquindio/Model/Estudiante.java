package uniquindio.Model;

import java.time.LocalDate;

public class Estudiante {

    private String nombre;
    private String documentoDeIdentidad;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaDeRegistro;

    private Estudiante(Builder builder) {
        this.nombre = builder.nombre;
        this.documentoDeIdentidad = builder.documentoDeIdentidad;
        this.telefono = builder.telefono;
        this.correo = builder.correo;
        this.edad = builder.edad;
        this.fechaDeRegistro = builder.fechaDeRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumentoDeIdentidad() {
        return documentoDeIdentidad;
    }

    public void setDocumentoDeIdentidad(String documentoDeIdentidad) {
        this.documentoDeIdentidad = documentoDeIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public void setFechaDeRegistro(LocalDate fechaDeRegistro) {
        this.fechaDeRegistro = fechaDeRegistro;
    }

    public static class Builder {

        private String nombre;
        private String documentoDeIdentidad;
        private String telefono;
        private String correo;
        private int edad;
        private LocalDate fechaDeRegistro;

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

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder edad(int edad) {
            this.edad = edad;
            return this;
        }

        public Builder fechaDeRegistro(LocalDate fechaDeRegistro) {
            this.fechaDeRegistro = fechaDeRegistro;
            return this;
        }

        public Estudiante build() {
            return new Estudiante(this);
        }
    }
}
