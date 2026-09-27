package Model;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Academia {





    private String nombre;
    private String nit;
    private String telefono;

    private List<Profesor> listProfesores;
    private List<Estudiante> listEstudiantes;
    private List<Matricula> listMatriculas;
    private List<Curso> listCursos;

    private Academia(String nombre, String nit, String telefono){
        this.nombre = nombre;
        this.nit = nit;
        this.telefono =telefono;

        listProfesores =new ArrayList<>();
        listEstudiantes = new ArrayList<>();
        listMatriculas = new ArrayList<>();
        listCursos = new ArrayList<>();

    }
// Patron singleton

    private static Academia instancia;

    public static  Academia getInstance(String nombre, String nit, String telefono){
        if (instancia == null){
            instancia = new Academia(nombre, nit, telefono);
        }

        return instancia;
    }


//--------------------------------------------------
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

    /**
     * Metodo para crear estudiante e ingresarlo a la lista de estudiantes, no necesita todos los atributos aún no se le asignó fecha de registro
     * @param nombre
     * @param documentoDeIdentidad
     * @param telefono
     * @param correo
     * @param edad
     */

    public void crearEstudiante(String nombre,
                                String documentoDeIdentidad,
                                String telefono,
                                String correo,
                                int edad,
                                LocalDate fechaRegistro){

        if(validarExistenciaEstudiante(documentoDeIdentidad)==true){
            JOptionPane.showMessageDialog(null,"El estudiante con documento: "+ documentoDeIdentidad+ " ya está registrado");
        } else{
            Estudiante nuevoEstudiante = new Estudiante.Builder()
                    .nombre(nombre)
                    .documentoDeIdentidad(documentoDeIdentidad)
                    .telefono(telefono)
                    .correo(correo)
                    .edad(edad)
                    .fechaDeRegistro(fechaRegistro)
                    .build();

            listEstudiantes.add(nuevoEstudiante);
            JOptionPane.showMessageDialog(null,"Se registró a " + nombre +"En la basé de datos");

        }



    }

    /**
     * Sirve para actualizar los datos del estudiante, se debe crear un estudiante nuevo, se puede usar builder para hacerlo y este metodo cambia los atributos del que ya estaba
     * @param documento
     * @param estudianteActualizado
     * @return bolleano en confimación de la actualización para activar el botton del controlador
     */

    public boolean actualizarDatosEstudiante(String documento, Estudiante estudianteActualizado){
        boolean bandera = false;
        Estudiante estudianteRegistrado = null;
        for (Estudiante est : listEstudiantes) {
            if (est.getDocumentoDeIdentidad().equals(documento)) {
                estudianteRegistrado = (Estudiante) est;

                estudianteRegistrado.setNombre(estudianteActualizado.getNombre());
                estudianteRegistrado.setTelefono(estudianteActualizado.getTelefono());
                estudianteRegistrado.setCorreo(estudianteActualizado.getCorreo());
                estudianteRegistrado.setEdad(estudianteActualizado.getEdad());
                estudianteRegistrado.setDocumentoDeIdentidad(estudianteActualizado.getDocumentoDeIdentidad());
                bandera =true;
                JOptionPane.showMessageDialog(null, "El estudiante " + nombre + "Se actualizó");
                break;


            }

        }
        return bandera;
    }

    /**
     * Eliminar estudiante de la lista de estudiantes con el número de documento
     * @param documento
     * @return
     */
    public boolean eliminarEstudiante(String documento){
        boolean bandera = false;
        Estudiante estudianteABorrar =null;

        for(Estudiante est : listEstudiantes){
            if(est.getDocumentoDeIdentidad().equals(documento)){
                JOptionPane.showMessageDialog(null,"Se eliminó a " + est.getNombre() +" de la basé de datos");
                listEstudiantes.remove(est);
                bandera =true;

            }
        }

        return  bandera;

    }
//--------------------------------------------------
//CRUD Profesor

    /**
     * Validar si un profesor exite en la lista de profesores
     * @param id
     * @return
     */

    public boolean validarExistenciaProfesor(String id){
        boolean bandera = false;

        for (Profesor pro : listProfesores ){

            if (pro.getDocumentoDeIdentidad().equals(id)){
                bandera = true;
                break;
            }
        }
        if ( bandera == false){
            JOptionPane.showMessageDialog(null, "El Profesor no existe");
        }
        return bandera;
    }

    /**
     * Busca y regresa un objeto de tipo Profesor de la lista de profesores
     * @param id
     * @return
     */


    public Profesor buscarProfesor(String id){
        Profesor profesorBuscado = null;

        for (Profesor prof : listProfesores ){

            if (prof.getDocumentoDeIdentidad().equals(id)){
                profesorBuscado = prof;
                break;
            }
        }

        if (profesorBuscado == null){
            JOptionPane.showMessageDialog(null, "El Profesor no existe");
        }

        return profesorBuscado;
    }

    /**
     * Sirve para crear profesor con todos los atributos
     * @param nombre
     * @param documentoDeIdentidad
     * @param telefono
     * @param idiomaQueEnsenia
     * @param tarifaPorSesion
     */

    public void crearProfesor(String nombre,
                              String documentoDeIdentidad,
                              String telefono,
                              Idioma idiomaQueEnsenia,
                              double tarifaPorSesion){

        if(validarExistenciaProfesor(documentoDeIdentidad)==true){
            JOptionPane.showMessageDialog(null,"El profesor con el documento: "+ documentoDeIdentidad+ " ya está registrado");
        } else{
            Profesor nuevoProfesor = new Profesor.Builder()
                    .nombre(nombre)
                    .documentoDeIdentidad(documentoDeIdentidad)
                    .telefono(telefono)
                    .idiomaQueEnsenia(idiomaQueEnsenia)
                    .tarifaPorSesion(tarifaPorSesion)
                    .build();

            listProfesores.add(nuevoProfesor);
            JOptionPane.showMessageDialog(null,"Se registró a " + nombre +"En la basé de datos");

        }



    }

    /**
     * Metodo para actualizar los datos de un profesor en la lista, se crea otro objeto en el controler y cambia los atributos por los de este
     * @param documento
     * @param profesorActualizado
     * @return boolean confimación de que se actualizó
     */

    public boolean actualizarProfesor(String documento, Profesor profesorActualizado){
        boolean bandera = false;
        Profesor profesorRegistrado = null;
        for (Profesor prof : listProfesores) {
            if (prof.getDocumentoDeIdentidad().equals(documento)) {
                profesorRegistrado = (Profesor) prof;

                profesorRegistrado.setNombre(profesorActualizado.getNombre());
                profesorRegistrado.setTelefono(profesorActualizado.getTelefono());
                profesorRegistrado.setIdiomaQueEnsenia(profesorActualizado.getIdiomaQueEnsenia());
                profesorRegistrado.setTarifaPorSesion(profesorActualizado.getTarifaPorSesion());
                profesorRegistrado.setDocumentoDeIdentidad(profesorActualizado.getDocumentoDeIdentidad());
                bandera =true;
                JOptionPane.showMessageDialog(null, "El profesor " + nombre + "Se actualizó");
                break;


            }

        }
        return bandera;
    }


    /**
     * Metodo para eliminar estudiante
     *
     * @param documento
     * @return
     */
    public boolean eliminarProfesor(String documento){
        boolean bandera = false;
                for(Profesor prof : listProfesores){
            if(prof.getDocumentoDeIdentidad().equals(documento)){
                JOptionPane.showMessageDialog(null,"Se eliminó a " + prof.getNombre() +" de la basé de datos");
                listProfesores.remove(prof);
                bandera =true;

            }
        }

        return  bandera;

    }



    //Metodos para agragar a listas

    public void agregarCurso(Curso curso){
        listCursos.add(curso);
    }

    public void agregarMatricula(Matricula matricula){
        listMatriculas.add(matricula);
    }

    public void agregarEstudiante(Estudiante estudiante){
        listEstudiantes.add(estudiante);
    }

    public void agregarProfesor(Profesor profesor){
        listProfesores.add(profesor);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Profesor> getListProfesores() {
        return listProfesores;
    }

    public void setListProfesores(List<Profesor> listProfesores) {
        this.listProfesores = listProfesores;
    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public List<Matricula> getListMatriculas() {
        return listMatriculas;
    }

    public void setListMatriculas(List<Matricula> listMatriculas) {
        this.listMatriculas = listMatriculas;
    }

    public List<Curso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    public static Academia getInstancia() {
        return instancia;
    }

    public static void setInstancia(Academia instancia) {
        Academia.instancia = instancia;
    }
}













