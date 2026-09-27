package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Curso {

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;

    private List<ServicioAdicional> listaBeneficios;




    protected Curso(Builder<?> builder) {

        this.codigo = builder.codigo;
        this.nombre = builder.nombre;
        this.idioma = builder.idioma;
        this.descripcion = builder.descripcion;
        this.duracionMeses = builder.duracionMeses;
        this.valorMensual = builder.valorMensual;
        this.estado = builder.estado;

        this.listaBeneficios =
                builder.listaBeneficios;
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

    public List<ServicioAdicional> getListaBeneficios() {
        return listaBeneficios;
    }



    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setListaBeneficios(
            List<ServicioAdicional> listaBeneficios) {

        this.listaBeneficios =
                listaBeneficios;
    }



    public void agregarBeneficio(
            ServicioAdicional beneficio) {

        if (listaBeneficios == null) {

            listaBeneficios =
                    new ArrayList<>();
        }

        listaBeneficios.add(beneficio);
    }

    public void eliminarBeneficio(
            ServicioAdicional beneficio) {

        if (listaBeneficios != null) {

            listaBeneficios.remove(
                    beneficio
            );
        }
    }




    public static abstract class Builder<T extends Builder<T>> {

        private String codigo;
        private String nombre;
        private String idioma;
        private String descripcion;
        private int duracionMeses;
        private double valorMensual;
        private Estado estado;

        private List<ServicioAdicional> listaBeneficios =
                new ArrayList<>();


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

            this.descripcion =
                    descripcion;

            return self();
        }


        public T duracionMeses(
                int duracionMeses) {

            this.duracionMeses =
                    duracionMeses;

            return self();
        }


        public T valorMensual(
                double valorMensual) {

            this.valorMensual =
                    valorMensual;

            return self();
        }


        public T estado(Estado estado) {

            this.estado = estado;

            return self();
        }


        public T listaBeneficios(
                List<ServicioAdicional> listaBeneficios) {

            this.listaBeneficios =
                    listaBeneficios;

            return self();
        }


        protected abstract T self();

        public abstract Curso build();
    }
}