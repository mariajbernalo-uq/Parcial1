package uniquindio.Model;

import java.util.List;

public class Profesor {

    private String nombre;
    private String documentoDeIdentidad;
    private String telefono;
    private Idioma idiomaQueEnsenia;
    private double tarifaPorSesion;

    private List<Curso> listaDeCursosProfesor;
    private List<Estudiante> listaEstudiantesProfesor;


    public Profesor(Builder builder){
        this.nombre = builder.nombre;
        this.documentoDeIdentidad = builder.documentoDeIdentidad;
        this.telefono = builder.telefono;
        this.idiomaQueEnsenia = builder.idiomaQueEnsenia;
        this.tarifaPorSesion = builder.tarifaPorSesion;
        this.listaDeCursosProfesor = builder.listaDeCursosProfesor;
        this.listaEstudiantesProfesor = builder.listaEstudiantesProfesor;



    }

    public static class Builder{
        private String nombre;
        private String documentoDeIdentidad;
        private String telefono;
        private Idioma idiomaQueEnsenia;
        private double tarifaPorSesion;
        private List<Curso> listaDeCursosProfesor;
        private List<Estudiante> listaEstudiantesProfesor;

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

        public Builder listaDeCursosProfesor(List<Curso> listaDeCursosProfesor) {
            this.listaDeCursosProfesor = listaDeCursosProfesor;
            return this;
        }

        public Builder listaEstudiantesProfesor(List<Estudiante> listaEstudiantesProfesor) {
            this.listaEstudiantesProfesor = listaEstudiantesProfesor;
            return this;
        }

        public Profesor build(){
            return new Profesor(this);
        }


    }
    //Método para calcular sesiones
    public double calcularValorSesiones(int cantidadSesiones) {
        if (cantidadSesiones < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de sesiones no puede ser negativa."
            );
        }

        return tarifaPorSesion * cantidadSesiones;
    }


    @Override
    public String toString() {
        return nombre + " (" + idiomaQueEnsenia + ")";
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

    public List<Curso> getListaDeCursosProfesor() {
        return listaDeCursosProfesor;
    }

    public List<Estudiante> getListaEstudiantesProfesor() {
        return listaEstudiantesProfesor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumentoDeIdentidad(String documentoDeIdentidad) {
        this.documentoDeIdentidad = documentoDeIdentidad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdiomaQueEnsenia(Idioma idiomaQueEnsenia) {
        this.idiomaQueEnsenia = idiomaQueEnsenia;
    }

    public void setTarifaPorSesion(double tarifaPorSesion) {
        this.tarifaPorSesion = tarifaPorSesion;
    }

    public void setListaDeCursosProfesor(List<Curso> listaDeCursosProfesor) {
        this.listaDeCursosProfesor = listaDeCursosProfesor;
    }

    public void setListaEstudiantesProfesor(List<Estudiante> listaEstudiantesProfesor) {
        this.listaEstudiantesProfesor = listaEstudiantesProfesor;
    }
}
