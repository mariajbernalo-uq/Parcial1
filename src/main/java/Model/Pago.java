package Model;

import jdk.dynalink.beans.StaticClass;

import java.time.LocalDate;
import java.util.List;

public record Pago() {
    static Academia academia = Academia.getInstance("Lenguaje Cafetero", "123", "Fuadadores" );
    static  List<Matricula> matriculaEstudiante = academia.getListMatriculas();

    public String calcularPAgoIndividual(String domumento){
        double valorAPagar = 0;
        String mensajeSalida=" ";

        for (Matricula mt : academia.getListMatriculas()){
            if (mt.getEstudiante().getDocumentoDeIdentidad().equals(domumento)){
                valorAPagar = mt.getValorTotal();

                mensajeSalida = "----Pago de metricula--\n" +
                        "Cliente:  " + mt.getEstudiante().getNombre()+ "\n" +
                        "Documento:  " + mt.getEstudiante().getDocumentoDeIdentidad()+ "\n" +
                        "Valor a pagar:  " + valorAPagar+ "\n";

                break;
            } else {
                mensajeSalida = "----Pago de metricula--\n" +
                        "No exite registro de matricual";
            }
        }

        return mensajeSalida;

    }

    public String calcularPagosIntervaloDeTiempo(LocalDate fechaInicio, LocalDate fechaFinal ){
        double valorAPagar = 0;
        String mensajeSalida=" ";
        int numMatriculas =0;

        for (Matricula mt : academia.getListMatriculas()){
            if (mt.getFechaMatricula().isAfter(fechaInicio) && mt.getFechaMatricula().isBefore(fechaFinal) ){
                valorAPagar += mt.getValorTotal();
                numMatriculas+=1;


            }
        }

        if (numMatriculas!=0){

            mensajeSalida = "----Pago de matriculas entre fechas--\n" +
                    "Num:  " + numMatriculas+ "\n" +
                    "Valor pagado:  " + valorAPagar+ "\n";

        } else {

            mensajeSalida = "----No hay matriculas en estas fechas--\n";

        }

        return mensajeSalida;

    }


}
