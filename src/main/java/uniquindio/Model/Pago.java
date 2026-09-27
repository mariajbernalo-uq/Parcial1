package uniquindio.Model;

import java.time.LocalDate;

public record Pago(
        String codigo,
        LocalDate fecha,
        double valorTotal,
        String medioPago
) {
    public Pago {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código del pago es obligatorio."
            );
        }

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha del pago es obligatoria."
            );
        }

        if (!Double.isFinite(valorTotal) || valorTotal <= 0) {
            throw new IllegalArgumentException(
                    "El valor del pago debe ser mayor que cero."
            );
        }

        if (medioPago == null || medioPago.isBlank()) {
            throw new IllegalArgumentException(
                    "El medio de pago es obligatorio."
            );
        }

        codigo = codigo.trim();
        medioPago = medioPago.trim();
    }

    // Usa automáticamente la fecha de hoy.
    public Pago(
            String codigo,
            double valorTotal,
            String medioPago
    ) {
        this(codigo, LocalDate.now(), valorTotal, medioPago);
    }
}