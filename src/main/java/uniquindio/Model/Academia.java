package uniquindio.Model;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Academia {
        private static Academia instancia;
        private String nombre;
        private String nit;
        private String telefono;
        private String direccion;
        private String correo;
      private String paginaWeb;

        private List<Profesor> listProfesores;
        private List<Estudiante> listEstudiantes;
        private List<Matricula> listMatriculas;

        private Academia(String nombre, String nit, String telefono, String direccion, String correo, String paginaWeb){
            this.nombre = nombre;
            this.nit = nit;
            this.telefono =telefono;
            this.direccion=direccion;
            this.correo=correo;
            this.paginaWeb=paginaWeb;

            listProfesores =new ArrayList<>();
            listEstudiantes = new ArrayList<>();
            listMatriculas = new ArrayList<>();

        }
    public static Academia getInstancia(
            String nombre, String nit, String telefono,String direccion, String correo, String paginaWeb
    ) {
        if (instancia == null) {
            instancia = new Academia(nombre, nit, telefono,direccion, correo,paginaWeb);
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
            return bandera;
        }

        /**
         * Metodo para buscar estudiante
         * @param documento
         * @return un objeto persona
         */

        public Estudiante buscarEstudiante(String documento) {
            for (Estudiante estudiante : listEstudiantes) {
                if (estudiante.getDocumentoDeIdentidad().equals(documento)) {
                    return estudiante;
                }
            }
            return null;
        }

        /**
         * Metodo para crear estudiante e ingresarlo a la lista de estudiantes, no necesita todos los atributos aún no se le asignó fecha de registro
         * @param nombre
         * @param documentoDeIdentidad
         * @param telefono
         * @param correo
         * @param edad
         */

        public boolean crearEstudiante(String nombre,
                                    String documentoDeIdentidad,
                                    String telefono,
                                    String correo,
                                    int edad,
                                    LocalDate fechaRegistro){

            if(buscarEstudiante(documentoDeIdentidad) != null){
                return false;
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
                return true;
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
        public boolean eliminarEstudiante(String documento) {
            return listEstudiantes.removeIf(estudiante ->
                    estudiante.getDocumentoDeIdentidad().equals(documento)
            );
        }

        //Getter Listas
    public List<Estudiante> getListEstudiantes() {
        return List.copyOf(listEstudiantes);
    }

    // CRUD PROFESOR

    public List<Profesor> getListProfesores() {
        return List.copyOf(listProfesores);
    }

    public Profesor buscarProfesor(String documento) {
        for (Profesor profesor : listProfesores) {
            if (profesor.getDocumentoDeIdentidad().equals(documento)) {
                return profesor;
            }
        }
        return null;
    }

    public boolean validarExistenciaProfesor(String documento) {
        return buscarProfesor(documento) != null;
    }

    public boolean crearProfesor(String nombre,
                                 String documentoDeIdentidad,
                                 String telefono,
                                 Idioma idiomaQueEnsenia,
                                 double tarifaPorSesion) {
        if (validarExistenciaProfesor(documentoDeIdentidad)) {
            return false;
        }

        Profesor profesor = new Profesor.Builder()
                .nombre(nombre)
                .documentoDeIdentidad(documentoDeIdentidad)
                .telefono(telefono)
                .idiomaQueEnsenia(idiomaQueEnsenia)
                .tarifaPorSesion(tarifaPorSesion)
                .build();

        listProfesores.add(profesor);
        return true;
    }

    public boolean actualizarDatosProfesor(String documentoOriginal,
                                           Profesor datosActualizados) {
        Profesor registrado = buscarProfesor(documentoOriginal);

        if (registrado == null) {
            return false;
        }

        registrado.setNombre(datosActualizados.getNombre());
        registrado.setTelefono(datosActualizados.getTelefono());
        registrado.setIdiomaQueEnsenia(
                datosActualizados.getIdiomaQueEnsenia()
        );
        registrado.setTarifaPorSesion(
                datosActualizados.getTarifaPorSesion()
        );

        return true;
    }

    public boolean eliminarProfesor(String documento) {
        return listProfesores.removeIf(profesor ->
                profesor.getDocumentoDeIdentidad().equals(documento)
        );
    }
}
