package uniquindio.Controller;

import java.time.LocalDate;
import uniquindio.Model.Academia;

public class ReportesController {

    private final Academia academia;

    public ReportesController(Academia academia) {
        this.academia = academia;
    }

    public int contarEstudiantes() {
        return academia.contarEstudiantes();
    }

    public int contarProfesores() {
        return academia.contarProfesores();
    }

    public int contarCursos() {
        return academia.contarCursos();
    }

    public int contarMatriculas() {
        return academia.contarMatriculas();
    }

    public int contarMatriculasDelMes(int anio, int mes) {
        return academia.contarMatriculasDelMes(anio, mes);
    }

    public double calcularValorMatriculasDelMes(int anio, int mes) {
        return academia.calcularValorMatriculasDelMes(anio, mes);
    }

    public int contarMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        return academia.contarMatriculasEntre(
                fechaInicial, fechaFinal
        );
    }

    public double calcularValorMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        return academia.calcularValorMatriculasEntre(
                fechaInicial, fechaFinal
        );
    }
}
