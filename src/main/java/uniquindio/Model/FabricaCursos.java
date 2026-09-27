package uniquindio.Model;

public final class FabricaCursos {

    private FabricaCursos() {
        // Evita crear instancias de la fábrica.
    }

    public static CursoRegular crearCursoRegular(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual
    ) {
        validarDatos(codigo, nombre, idioma,
                duracionMeses, valorMensual);

        return new CursoRegular(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                Estado.ACTIVO
        );
    }

    public static CursoIntensivo crearCursoIntensivo(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual
    ) {
        validarDatos(codigo, nombre, idioma,
                duracionMeses, valorMensual);

        return new CursoIntensivo(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                Estado.ACTIVO
        );
    }

    public static CursoPersonalizado crearCursoPersonalizado(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante
    ) {
        validarDatos(codigo, nombre, idioma,
                duracionMeses, valorMensual);

        if (cantidadSesiones <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de sesiones debe ser mayor que cero."
            );
        }

        if (nivelReferencia == null) {
            throw new IllegalArgumentException(
                    "Selecciona un nivel de referencia."
            );
        }

        return new CursoPersonalizado(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                Estado.ACTIVO,
                cantidadSesiones,
                nivelReferencia,
                objetivoEstudiante
        );
    }

    private static void validarDatos(
            String codigo,
            String nombre,
            Idioma idioma,
            int duracionMeses,
            double valorMensual
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código del curso es obligatorio."
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del curso es obligatorio."
            );
        }

        if (idioma == null) {
            throw new IllegalArgumentException(
                    "Selecciona un idioma."
            );
        }

        if (duracionMeses <= 0) {
            throw new IllegalArgumentException(
                    "La duración debe ser mayor que cero."
            );
        }

        if (!Double.isFinite(valorMensual) || valorMensual < 0) {
            throw new IllegalArgumentException(
                    "El valor mensual no es válido."
            );
        }
    }
}