package uniquindio.Model;

public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private Nivel nivelReferencia;
    private String objetivoEstudiante;

    public CursoPersonalizado(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante
    ) {
        super(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                estado
        );

        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivoEstudiante = objetivoEstudiante;
    }

    @Override
    public double calcularValor(int duracionContratada) {
        return getValorMensual() * duracionContratada;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
    }

    public Nivel getNivelReferencia() {
        return nivelReferencia;
    }

    public void setNivelReferencia(Nivel nivelReferencia) {
        this.nivelReferencia = nivelReferencia;
    }

    public String getObjetivoEstudiante() {
        return objetivoEstudiante;
    }

    public void setObjetivoEstudiante(String objetivoEstudiante) {
        this.objetivoEstudiante = objetivoEstudiante;
    }
}