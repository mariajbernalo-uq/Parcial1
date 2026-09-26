package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Academia {

    private String nombre;
    private String nit;
    private String telefono;

    private List<Profesor> lisProfesores;
    private List<Estudiante> listEstudiantes;
    private List<Matricula> listMatriculas;

    public Academia(String nombre, String nit, String telefono){
        this.nombre = nombre;
        this.nit = nit;
        this.telefono =telefono;

        lisProfesores =new ArrayList<>();
        listEstudiantes = new ArrayList<>();
        listMatriculas = new ArrayList<>();

    }



    //CRUD ESTUDIANTE

    /**
     * Metodo para validar existencia de un Estudiante
     * @param id
     * @return un booleano
     */

    public boolean validarExistenciaEstudiante(String id){
        boolean bandera = false;

        for (Estudiante est : listEstudiantes ){

            if (est.getDocumentoDeIdentidad().equals(id)){
                bandera = true;
                break;
            }
        }
        if ( bandera == false){
            JOptionPane.showMessageDialog(null, "El estudiante no existe");
        }
        return bandera;
    }

    /**
     * Metodo para buscar estudiante
     * @param id
     * @return un objeto persona
     */

    public Estudiante buscarEstudiante(String id){
        Estudiante estudianteBuscado = null;

        for (Estudiante est : listEstudiantes ){

            if (est.getDocumentoDeIdentidad().equals(id)){
                estudianteBuscado = est;
                break;
            }
        }

        if (estudianteBuscado == null){
            JOptionPane.showMessageDialog(null, "El estudiante no existe");
        }

        return estudianteBuscado;
    }






}
