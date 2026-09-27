package Controller;

import Model.Academia;
import Model.Matricula;

import java.time.LocalDate;

public class ReportesController {

    private Academia academia;

    public ReportesController(Academia academia) {
        this.academia = academia;
    }

    // =========================
    // REPORTE GENERAL
    // =========================

    public int contarEstudiantes() {
        return academia.getListEstudiantes().size();
    }

    public int contarProfesores() {
        return academia.getListProfesores().size();
    }

    public int contarCursos() {
        return academia.getListCursos().size();
    }

    public int contarMatriculas() {
        return academia.getListMatriculas().size();
    }

    // =========================
    // REPORTE MENSUAL
    // =========================

    public int contarMatriculasDelMes(int anio, int mes) {

        int contador = 0;

        for (Matricula matricula : academia.getListMatriculas()) {

            LocalDate fecha = matricula.getFechaMatricula();

            if (fecha != null
                    && fecha.getYear() == anio
                    && fecha.getMonthValue() == mes) {

                contador++;
            }
        }

        return contador;
    }

    public double calcularValorMatriculasDelMes(int anio, int mes) {

        double total = 0;

        for (Matricula matricula : academia.getListMatriculas()) {

            LocalDate fecha = matricula.getFechaMatricula();

            if (fecha != null
                    && fecha.getYear() == anio
                    && fecha.getMonthValue() == mes) {

                total += matricula.getValorTotal();
            }
        }

        return total;
    }

    // =========================
    // REPORTE ENTRE FECHAS
    // =========================

    public int contarMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal) {

        int contador = 0;

        for (Matricula matricula : academia.getListMatriculas()) {

            LocalDate fecha = matricula.getFechaMatricula();

            if (fecha != null
                    && !fecha.isBefore(fechaInicial)
                    && !fecha.isAfter(fechaFinal)) {

                contador++;
            }
        }

        return contador;
    }

    public double calcularValorMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal) {

        double total = 0;

        for (Matricula matricula : academia.getListMatriculas()) {

            LocalDate fecha = matricula.getFechaMatricula();

            if (fecha != null
                    && !fecha.isBefore(fechaInicial)
                    && !fecha.isAfter(fechaFinal)) {

                total += matricula.getValorTotal();
            }
        }

        return total;
    }
}