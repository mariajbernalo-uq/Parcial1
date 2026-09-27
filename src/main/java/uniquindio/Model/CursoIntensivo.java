package uniquindio.Model;

public class CursoIntensivo extends Curso {

    public CursoIntensivo(String codigo,
                          String nombre,
                          Idioma idioma,
                          String descripcion,
                          int duracionMeses,
                          double valorMensual,
                          Estado estado) {
        super(codigo, nombre, idioma, descripcion,
                duracionMeses, valorMensual, estado);
    }

    @Override
    public double calcularValor(int duracionContratada) {
        if (duracionContratada <= 0) {
            throw new IllegalArgumentException(
                    "Los meses contratados deben ser mayores que cero."
            );
        }

        return getValorMensual() * duracionContratada;
    }
}