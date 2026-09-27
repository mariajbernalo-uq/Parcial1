package uniquindio.Model;
import javax.swing.*;
import java.time.LocalDate;
import java.time.YearMonth;
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
        private List<Curso> listCursos;
        private List<Beneficio> listBeneficios;
        private List<ServicioAdicional> listServiciosAdicionales;

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
            listCursos = new ArrayList<>();
            listBeneficios = new ArrayList<>();
            listServiciosAdicionales= new ArrayList<>();

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
    // --------------------------------------------------
// CRUD CURSOS
// --------------------------------------------------

    public Curso buscarCurso(String codigo) {
        for (Curso curso : listCursos) {
            if (curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public boolean crearCursoRegular(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            List<Beneficio> beneficiosSeleccionados
    ) {
        if (buscarCurso(codigo) != null) {
            return false;
        }

        Curso curso = FabricaCursos.crearCursoRegular(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual
        );
        for (Beneficio beneficio : beneficiosSeleccionados) {
            curso.agregarBeneficio(beneficio);
        }

        listCursos.add(curso);
        return true;
    }

    public boolean crearCursoIntensivo(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            List<Beneficio> beneficiosSeleccionados
    ) {
        if (buscarCurso(codigo) != null) {
            return false;
        }

        Curso curso = FabricaCursos.crearCursoIntensivo(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual
        );
        for (Beneficio beneficio : beneficiosSeleccionados) {
            curso.agregarBeneficio(beneficio);
        }
        listCursos.add(curso);
        return true;
    }

    public boolean crearCursoPersonalizado(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante,
            List<Beneficio> beneficiosSeleccionados

    ) {
        if (buscarCurso(codigo) != null) {
            return false;
        }

        CursoPersonalizado curso = FabricaCursos.crearCursoPersonalizado(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                cantidadSesiones,
                nivelReferencia,
                objetivoEstudiante
        );
        for (Beneficio beneficio : beneficiosSeleccionados) {
                curso.agregarBeneficio(beneficio);
            }
        listCursos.add(curso);
        return true;
    }

    public boolean actualizarCurso(
            String codigo,
            String nombre,
            Idioma idioma,
            String descripcion,
            int duracionMeses,
            double valorMensual,
            Estado estado
    ) {
        Curso curso = buscarCurso(codigo);

        if (curso == null) {
            return false;
        }

        curso.setNombre(nombre);
        curso.setIdioma(idioma);
        curso.setDescripcion(descripcion);
        curso.setDuracionMeses(duracionMeses);
        curso.setValorMensual(valorMensual);
        curso.setEstado(estado);

        return true;
    }

    public boolean actualizarDatosCursoPersonalizado(
            String codigo,
            int cantidadSesiones,
            Nivel nivelReferencia,
            String objetivoEstudiante
    ) {
        Curso curso = buscarCurso(codigo);

        if (!(curso instanceof CursoPersonalizado personalizado)) {
            return false;
        }

        personalizado.setCantidadSesiones(cantidadSesiones);
        personalizado.setNivelReferencia(nivelReferencia);
        personalizado.setObjetivoEstudiante(objetivoEstudiante);

        return true;
    }


    public boolean eliminarCurso(String codigo) {
        return listCursos.removeIf(
                curso -> curso.getCodigo().equals(codigo)
        );
    }

    public List<Curso> getListCursos() {
        return List.copyOf(listCursos);
    }

    //Beneficios
    public List<Beneficio> getListBeneficios() {
        return List.copyOf(listBeneficios);
    }

    public Beneficio buscarBeneficio(String codigo) {
        for (Beneficio beneficio : listBeneficios) {
            if (beneficio.getCodigo().equals(codigo)) {
                return beneficio;
            }
        }
        return null;
    }

    public boolean crearBeneficio(
            String codigo,
            String nombre,
            String descripcion
    ) {
        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || buscarBeneficio(codigo.trim()) != null) {
            return false;
        }

        listBeneficios.add(new Beneficio(
                codigo.trim(),
                nombre.trim(),
                descripcion == null ? "" : descripcion.trim()
        ));

        return true;
    }
    public boolean actualizarBeneficiosCurso(
            String codigoCurso,
            List<Beneficio> beneficiosSeleccionados
    ) {
        Curso curso = buscarCurso(codigoCurso);

        if (curso == null || beneficiosSeleccionados == null) {
            return false;
        }
        for (Beneficio beneficio : beneficiosSeleccionados) {
            if (beneficio == null
                    || buscarBeneficio(beneficio.getCodigo()) == null) {
                return false;
            }
        }

        List<Beneficio> beneficiosAnteriores =
                new ArrayList<>(curso.getListaBeneficios());

        for (Beneficio beneficio : beneficiosAnteriores) {
            curso.eliminarBeneficio(beneficio);
        }

        for (Beneficio beneficio : beneficiosSeleccionados) {
            // Usar el objeto que está registrado en Academia.
            Beneficio registrado =
                    buscarBeneficio(beneficio.getCodigo());

            curso.agregarBeneficio(registrado);
        }

        return true;
    }


//MATRICULAS

    public Matricula buscarMatricula(String documentoEstudiante) {
        for (Matricula matricula : listMatriculas) {
            if (matricula.getEstudiante()
                    .getDocumentoDeIdentidad()
                    .equals(documentoEstudiante)) {
                return matricula;
            }
        }

        return null;
    }
    public Matricula buscarMatriculaPorCodigo(String codigo) {
        for (Matricula matricula : listMatriculas) {
            if (matricula.getCodigo().equals(codigo)) {
                return matricula;
            }
        }
        return null;
    }

    public List<Matricula> buscarMatriculasPorEstudiante(
            String documento
    ) {
        List<Matricula> resultado = new ArrayList<>();

        for (Matricula matricula : listMatriculas) {
            if (matricula.getEstudiante()
                    .getDocumentoDeIdentidad()
                    .equals(documento)) {
                resultado.add(matricula);
            }
        }

        return resultado;
    }

    public boolean registrarMatricula(Matricula matricula) {
        if (matricula == null || matricula.getEstudiante() == null) {
            return false;
        }

        String documento = matricula.getEstudiante()
                .getDocumentoDeIdentidad();

        if (buscarMatriculaPorCodigo(matricula.getCodigo()) != null
                || buscarEstudiante(documento) == null) {
            return false;
        }

        for (Curso curso : matricula.getListaCursos()) {
            if (buscarCurso(curso.getCodigo()) == null) {
                return false;
            }
        }

        listMatriculas.add(matricula);
        return true;
    }

    public boolean eliminarMatricula(String documentoEstudiante) {
        return listMatriculas.removeIf(matricula ->
                matricula.getEstudiante()
                        .getDocumentoDeIdentidad()
                        .equals(documentoEstudiante)
        );
    }

    public List<Matricula> getListMatriculas() {
        return List.copyOf(listMatriculas);
    }

    public boolean actualizarDescuentoMatricula(
            String documentoEstudiante,
            double descuento
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null
                || !Double.isFinite(descuento)
                || descuento < 0) {
            return false;
        }

        matricula.aplicarDescuento(descuento);
        return true;
    }

    public boolean agregarCursoAMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);
        Curso curso = buscarCurso(codigoCurso);

        if (matricula == null || curso == null) {
            return false;
        }

        return matricula.agregarCurso(curso);
    }

    public boolean quitarCursoDeMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null) {
            return false;
        }

        // La matrícula siempre debe conservar al menos un curso.
        if (matricula.getListaCursos().size() <= 1) {
            return false;
        }

        return matricula.eliminarCurso(codigoCurso);
    }

    public boolean asignarProfesorAMatricula(
            String documentoEstudiante,
            String codigoCurso,
            String documentoProfesor
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);
        Profesor profesor =
                buscarProfesor(documentoProfesor);

        if (matricula == null || profesor == null) {
            return false;
        }

        // Matricula valida el tipo de curso y el idioma.
        return matricula.asignarProfesor(
                codigoCurso,
                profesor
        );
    }

    public boolean retirarProfesorDeMatricula(
            String documentoEstudiante,
            String codigoCurso
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null) {
            return false;
        }

        return matricula.retirarProfesor(codigoCurso);
    }

    public boolean agregarServicioAMatricula(
            String documentoEstudiante,
            ServicioAdicional servicio
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null || servicio == null) {
            return false;
        }

        ServicioAdicional registrado =
                buscarServicioAdicional(
                        servicio.getCodigo()
                );

        if (registrado == null || !registrado.isDisponible()) {
            return false;
        }

        for (ServicioAdicional actual :
                matricula.getServiciosAdicionales()) {
            if (actual.getCodigo().equals(
                    registrado.getCodigo()
            )) {
                return false;
            }
        }

        return matricula.agregarServicio(registrado);
    }

    public boolean quitarServicioDeMatricula(
            String documentoEstudiante,
            ServicioAdicional servicio
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null || servicio == null) {
            return false;
        }

        // Usar el objeto guardado en la matrícula, porque
        // eliminarServicio(...) recibe un objeto ServicioAdicional.
        for (ServicioAdicional actual :
                matricula.getServiciosAdicionales()) {
            if (actual.getCodigo().equals(
                    servicio.getCodigo()
            )) {
                return matricula.eliminarServicio(actual);
            }
        }

        return false;
    }

    public boolean registrarPagoDeMatricula(
            String documentoEstudiante,
            Pago pago
    ) {
        Matricula matricula =
                buscarMatricula(documentoEstudiante);

        if (matricula == null || pago == null) {
            return false;
        }

        return matricula.registrarPago(pago);
    }


    //SERVICIOS ADICIONALES
    public List<ServicioAdicional> getListServiciosAdicionales() {
        return List.copyOf(listServiciosAdicionales);
    }

    public ServicioAdicional buscarServicioAdicional(String codigo) {
        for (ServicioAdicional servicio : listServiciosAdicionales) {
            if (servicio.getCodigo().equals(codigo)) {
                return servicio;
            }
        }

        return null;
    }

    public boolean crearServicioAdicional(
            String codigo,
            String nombre,
            String descripcion,
            double precio
    ) {
        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || !Double.isFinite(precio)
                || precio < 0) {
            return false;
        }

        codigo = codigo.trim();

        if (buscarServicioAdicional(codigo) != null) {
            return false;
        }

        ServicioAdicional servicio =
                new ServicioAdicional.Builder()
                        .codigo(codigo)
                        .nombre(nombre.trim())
                        .descripcion(
                                descripcion == null
                                        ? ""
                                        : descripcion.trim()
                        )
                        .precio(precio)
                        .build();

        listServiciosAdicionales.add(servicio);
        return true;
    }

    public boolean cambiarDisponibilidadServicio(
            String codigo,
            boolean disponible
    ) {
        ServicioAdicional servicio =
                buscarServicioAdicional(codigo);

        if (servicio == null) {
            return false;
        }

        servicio.cambiarDisponibilidad(disponible);
        return true;
    }

    public boolean eliminarServicioAdicional(String codigo) {
        ServicioAdicional servicio =
                buscarServicioAdicional(codigo);

        if (servicio == null) {
            return false;
        }
        for (Matricula matricula : listMatriculas) {
            for (ServicioAdicional usado :
                    matricula.getServiciosAdicionales()) {
                if (usado.getCodigo().equals(codigo)) {
                    return false;
                }
            }
        }

        return listServiciosAdicionales.remove(servicio);
    }
    // -------------------- REPORTES GENERALES --------------------

    public int contarEstudiantes() {
        return listEstudiantes.size();
    }

    public int contarProfesores() {
        return listProfesores.size();
    }

    public int contarCursos() {
        return listCursos.size();
    }

    public int contarMatriculas() {
        return listMatriculas.size();
    }
// -------------------- REPORTES POR PERÍODO --------------------

    public int contarMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        validarPeriodo(fechaInicial, fechaFinal);

        int cantidad = 0;

        for (Matricula matricula : listMatriculas) {
            if (estaEnPeriodo(
                    matricula.getFechaMatricula(),
                    fechaInicial,
                    fechaFinal
            )) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public double calcularValorMatriculasEntre(
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        validarPeriodo(fechaInicial, fechaFinal);

        double total = 0;

        for (Matricula matricula : listMatriculas) {
            if (estaEnPeriodo(
                    matricula.getFechaMatricula(),
                    fechaInicial,
                    fechaFinal
            )) {
                total += matricula.calcularTotal();
            }
        }

        return total;
    }

    public double calcularValorMatriculasDelMes(
            int anio,
            int mes
    ) {
        YearMonth periodo = YearMonth.of(anio, mes);

        return calcularValorMatriculasEntre(
                periodo.atDay(1),
                periodo.atEndOfMonth()
        );
    }

    public int contarMatriculasDelMes(
            int anio,
            int mes
    ) {
        YearMonth periodo = YearMonth.of(anio, mes);

        return contarMatriculasEntre(
                periodo.atDay(1),
                periodo.atEndOfMonth()
        );
    }

    private boolean estaEnPeriodo(
            LocalDate fecha,
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        return fecha != null
                && !fecha.isBefore(fechaInicial)
                && !fecha.isAfter(fechaFinal);
    }

    private void validarPeriodo(
            LocalDate fechaInicial,
            LocalDate fechaFinal
    ) {
        if (fechaInicial == null || fechaFinal == null) {
            throw new IllegalArgumentException(
                    "Selecciona ambas fechas."
            );
        }

        if (fechaInicial.isAfter(fechaFinal)) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser posterior a la final."
            );
        }
    }


}
