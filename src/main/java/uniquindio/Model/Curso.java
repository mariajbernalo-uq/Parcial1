package uniquindio.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Curso {

    private String codigo;
    private String nombre;
    private Idioma idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private Estado estado;
    private List<Beneficio> beneficios;

    protected Curso(String codigo,
                    String nombre,
                    Idioma idioma,
                    String descripcion,
                    int duracionMeses,
                    double valorMensual,
                    Estado estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
        this.beneficios = new ArrayList<>();
    }

    public abstract double calcularValor(int duracionContratada);

    public boolean estaActivo() {
        return estado == Estado.ACTIVO;
    }

    public boolean agregarBeneficio(Beneficio beneficio) {
        if (beneficio == null || beneficios.contains(beneficio)) {
            return false;
        }
        return beneficios.add(beneficio);
    }

    public boolean eliminarBeneficio(Beneficio beneficio) {
        return beneficios.remove(beneficio);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Beneficio> getListaBeneficios() {
        return List.copyOf(beneficios);
    }
}
