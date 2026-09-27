package ViewController;

import App.App;
import Controller.ReportesController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ReportesViewController {

    private App app;
    private ReportesController reportesController;

    @FXML
    private Label lblTotalEstudiantes;

    @FXML
    private Label lblTotalProfesores;

    @FXML
    private Label lblTotalCursos;

    @FXML
    private Label lblTotalMatriculas;

    @FXML
    private ComboBox<Integer> cmbMes;

    @FXML
    private ComboBox<Integer> cmbAnio;

    @FXML
    private Label lblMatriculasMes;

    @FXML
    private Label lblValorMatriculasMes;

    @FXML
    private DatePicker fechaInicial;

    @FXML
    private DatePicker fechaFinal;

    @FXML
    private Label lblMatriculasPeriodo;

    @FXML
    private Label lblValorMatriculasPeriodo;


    // ==========================================
    // INICIALIZACIÓN
    // ==========================================

    public void setApp(App app) {

        this.app = app;

        this.reportesController =
                new ReportesController(app.getAcademia());

        configurarCombos();

        mostrarReporteGeneral();
    }


    // ==========================================
    // CONFIGURAR COMBOS
    // ==========================================

    private void configurarCombos() {

        cmbMes.setItems(
                FXCollections.observableArrayList(
                        1, 2, 3, 4, 5, 6,
                        7, 8, 9, 10, 11, 12
                )
        );

        cmbAnio.setItems(
                FXCollections.observableArrayList(
                        2025, 2026, 2027, 2028, 2029, 2030
                )
        );

        cmbMes.setValue(LocalDate.now().getMonthValue());
        cmbAnio.setValue(LocalDate.now().getYear());
    }


    // ==========================================
    // REPORTE GENERAL
    // ==========================================

    @FXML
    private void mostrarReporteGeneral() {

        int estudiantes =
                reportesController.contarEstudiantes();

        int profesores =
                reportesController.contarProfesores();

        int cursos =
                reportesController.contarCursos();

        int matriculas =
                reportesController.contarMatriculas();

        lblTotalEstudiantes.setText(
                String.valueOf(estudiantes)
        );

        lblTotalProfesores.setText(
                String.valueOf(profesores)
        );

        lblTotalCursos.setText(
                String.valueOf(cursos)
        );

        lblTotalMatriculas.setText(
                String.valueOf(matriculas)
        );
    }


    // ==========================================
    // REPORTE MENSUAL
    // ==========================================

    @FXML
    private void mostrarReporteMensual() {

        if (cmbMes.getValue() == null
                || cmbAnio.getValue() == null) {

            mostrarMensaje(
                    "Debe seleccionar el mes y el año."
            );

            return;
        }

        int mes = cmbMes.getValue();
        int anio = cmbAnio.getValue();

        int cantidad =
                reportesController.contarMatriculasDelMes(
                        anio,
                        mes
                );

        double valor =
                reportesController.calcularValorMatriculasDelMes(
                        anio,
                        mes
                );

        lblMatriculasMes.setText(
                String.valueOf(cantidad)
        );

        lblValorMatriculasMes.setText(
                String.format("$ %.2f", valor)
        );
    }


    // ==========================================
    // REPORTE ENTRE FECHAS
    // ==========================================

    @FXML
    private void mostrarReporteEntreFechas() {

        LocalDate inicio =
                fechaInicial.getValue();

        LocalDate fin =
                fechaFinal.getValue();

        if (inicio == null || fin == null) {

            mostrarMensaje(
                    "Debe seleccionar las dos fechas."
            );

            return;
        }

        if (inicio.isAfter(fin)) {

            mostrarMensaje(
                    "La fecha inicial no puede ser posterior a la fecha final."
            );

            return;
        }

        int cantidad =
                reportesController.contarMatriculasEntre(
                        inicio,
                        fin
                );

        double valor =
                reportesController.calcularValorMatriculasEntre(
                        inicio,
                        fin
                );

        lblMatriculasPeriodo.setText(
                String.valueOf(cantidad)
        );

        lblValorMatriculasPeriodo.setText(
                String.format("$ %.2f", valor)
        );
    }


    // ==========================================
    // MENSAJE
    // ==========================================

    private void mostrarMensaje(String mensaje) {

        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION
        );

        alerta.setTitle("Reportes");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}