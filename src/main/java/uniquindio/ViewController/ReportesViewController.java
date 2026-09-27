package uniquindio.ViewController;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import uniquindio.Controller.ReportesController;

public class ReportesViewController {

    private ReportesController reporteController;

    @FXML private Label lblTotalEstudiantes;
    @FXML private Label lblTotalProfesores;
    @FXML private Label lblTotalCursos;
    @FXML private Label lblTotalMatriculas;

    @FXML private ComboBox<Month> cmbMes;
    @FXML private ComboBox<Integer> cmbAnio;
    @FXML private Label lblMatriculasMes;
    @FXML private Label lblValorMatriculasMes;

    @FXML private DatePicker fechaInicial;
    @FXML private DatePicker fechaFinal;
    @FXML private Label lblMatriculasPeriodo;
    @FXML private Label lblValorMatriculasPeriodo;

    private final NumberFormat formatoPesos =
            NumberFormat.getCurrencyInstance(
                    Locale.forLanguageTag("es-CO")
            );

    @FXML
    private void initialize() {
        cmbMes.setItems(
                FXCollections.observableArrayList(
                        Month.values()
                )
        );

        cmbMes.setConverter(new StringConverter<>() {
            @Override
            public String toString(Month mes) {
                if (mes == null) {
                    return "";
                }

                String nombre = mes.getDisplayName(
                        TextStyle.FULL,
                        new Locale("es", "CO")
                );

                return nombre.substring(0, 1).toUpperCase()
                        + nombre.substring(1);
            }

            @Override
            public Month fromString(String texto) {
                return null; // El ComboBox no es editable.
            }
        });

        int anioActual = LocalDate.now().getYear();

        for (int anio = anioActual - 5;
             anio <= anioActual + 1;
             anio++) {
            cmbAnio.getItems().add(anio);
        }

        cmbMes.setValue(LocalDate.now().getMonth());
        cmbAnio.setValue(anioActual);

        fechaInicial.setValue(
                LocalDate.now().withDayOfMonth(1)
        );
        fechaFinal.setValue(LocalDate.now());
    }

    public void setReporteController(
            ReportesController reporteController
    ) {
        this.reporteController = reporteController;
        mostrarReporteGeneral();
    }

    @FXML
    private void mostrarReporteGeneral() {
        if (reporteController == null) {
            return;
        }

        lblTotalEstudiantes.setText(
                String.valueOf(
                        reporteController.contarEstudiantes()
                )
        );
        lblTotalProfesores.setText(
                String.valueOf(
                        reporteController.contarProfesores()
                )
        );
        lblTotalCursos.setText(
                String.valueOf(
                        reporteController.contarCursos()
                )
        );
        lblTotalMatriculas.setText(
                String.valueOf(
                        reporteController.contarMatriculas()
                )
        );
    }

    @FXML
    private void mostrarReporteMensual() {
        Month mes = cmbMes.getValue();
        Integer anio = cmbAnio.getValue();

        if (mes == null || anio == null) {
            mostrarMensaje("Selecciona el mes y el año.");
            return;
        }

        int cantidad = reporteController.contarMatriculasDelMes(
                anio,
                mes.getValue()
        );

        double valor =
                reporteController.calcularValorMatriculasDelMes(
                        anio,
                        mes.getValue()
                );

        lblMatriculasMes.setText(String.valueOf(cantidad));
        lblValorMatriculasMes.setText(
                formatoPesos.format(valor)
        );
    }

    @FXML
    private void mostrarReporteEntreFechas() {
        LocalDate inicio = fechaInicial.getValue();
        LocalDate fin = fechaFinal.getValue();

        if (inicio == null || fin == null) {
            mostrarMensaje("Selecciona ambas fechas.");
            return;
        }

        if (inicio.isAfter(fin)) {
            mostrarMensaje(
                    "La fecha inicial no puede ser posterior "
                            + "a la fecha final."
            );
            return;
        }

        int cantidad =
                reporteController.contarMatriculasEntre(
                        inicio, fin
                );

        double valor =
                reporteController.calcularValorMatriculasEntre(
                        inicio, fin
                );

        lblMatriculasPeriodo.setText(
                String.valueOf(cantidad)
        );
        lblValorMatriculasPeriodo.setText(
                formatoPesos.format(valor)
        );
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
