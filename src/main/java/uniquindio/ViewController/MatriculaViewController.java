package uniquindio.ViewController;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uniquindio.Controller.MatriculaController;
import uniquindio.Model.Curso;
import uniquindio.Model.CursoPersonalizado;
import uniquindio.Model.Estudiante;
import uniquindio.Model.Matricula;
import uniquindio.Model.Profesor;
import uniquindio.Model.ServicioAdicional;

public class MatriculaViewController {

    private MatriculaController matriculaController;
    private Estudiante estudianteEncontrado;

    private final List<Curso> cursosSeleccionados =
            new ArrayList<>();

    private final List<ServicioAdicional> serviciosSeleccionados =
            new ArrayList<>();

    // Código del curso personalizado -> profesor elegido.
    private final Map<String, Profesor> profesoresAsignados =
            new HashMap<>();

    private final NumberFormat formatoMoneda =
            NumberFormat.getCurrencyInstance(
                    Locale.forLanguageTag("es-CO")
            );

    @FXML private TextField txtCodigoMatricula;
    @FXML private TextField txtDocumentoEstudiante;
    @FXML private TextField txtNombreEstudiante;
    @FXML private TextField txtCorreoEstudiante;
    @FXML private TextField txtDuracionContratada;
    @FXML private TextField txtDescuento;
    @FXML private TextField txtBuscarDocumento;

    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtNombreServicio;
    @FXML private TextField txtDescripcionServicio;
    @FXML private TextField txtPrecioServicio;


    @FXML private MenuButton menuCursos;
    @FXML private MenuButton menuServicios;

    @FXML private Label lblCursosSeleccionados;
    @FXML private Label lblServiciosSeleccionados;
    @FXML private Label lblProfesoresAsignados;
    @FXML private Label lblDisponibilidadServicio;
    @FXML private Label lblValorTotal;

    @FXML private VBox panelAsignacionProfesor;

    @FXML private ComboBox<CursoPersonalizado>
            cmbCursoPersonalizado;
    @FXML private ComboBox<Profesor> cmbProfesor;
    @FXML private ComboBox<ServicioAdicional>
            cmbServicioGestion;

    @FXML private Button btnCambiarDisponibilidad;

    @FXML private TableView<Matricula> tablaMatriculas;

    @FXML private TableColumn<Matricula, String> colDocumento;
    @FXML private TableColumn<Matricula, String> colEstudiante;
    @FXML private TableColumn<Matricula, String> colFecha;
    @FXML private TableColumn<Matricula, String> colCursos;
    @FXML private TableColumn<Matricula, String> colServicios;
    @FXML private TableColumn<Matricula, String> colProfesores;
    @FXML private TableColumn<Matricula, Double> colDescuento;
    @FXML private TableColumn<Matricula, Double> colValorTotal;
    @FXML private TableColumn<Matricula, String> colCodigoMatricula;

    @FXML
    private void initialize() {
        txtDescuento.setText("0");
        colCodigoMatricula.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getCodigo()
                )
        );
        colDocumento.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getEstudiante()
                                .getDocumentoDeIdentidad()
                )
        );

        colEstudiante.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getEstudiante()
                                .getNombre()
                )
        );

        colFecha.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getFechaMatricula()
                                .toString()
                )
        );

        colCursos.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getListaCursos()
                                .stream()
                                .map(Curso::getNombre)
                                .collect(Collectors.joining(", "))
                )
        );

        colServicios.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getServiciosAdicionales()
                                .stream()
                                .map(ServicioAdicional::getNombre)
                                .collect(Collectors.joining(", "))
                )
        );

        colProfesores.setCellValueFactory(dato -> {
            Matricula matricula = dato.getValue();
            List<String> nombres = new ArrayList<>();

            for (Curso curso : matricula.getListaCursos()) {
                if (curso instanceof CursoPersonalizado) {
                    Profesor profesor = matricula
                            .getProfesorAsignado(
                                    curso.getCodigo()
                            );

                    if (profesor != null) {
                        nombres.add(
                                curso.getNombre() + ": "
                                        + profesor.getNombre()
                        );
                    }
                }
            }

            return new ReadOnlyStringWrapper(
                    String.join(", ", nombres)
            );
        });

        colDescuento.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getDescuento()
                )
        );

        colValorTotal.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getValorTotal()
                )
        );

        cmbCursoPersonalizado.valueProperty().addListener(
                (observable, anterior, curso) ->
                        cargarProfesoresDelCurso(curso)
        );

        cmbServicioGestion.valueProperty().addListener(
                (observable, anterior, servicio) ->
                        mostrarDisponibilidad(servicio)
        );

        tablaMatriculas.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionada) -> {
                    if (seleccionada != null) {
                        mostrarEnFormulario(seleccionada);
                    }
                });
    }

    public void setMatriculaController(
            MatriculaController matriculaController
    ) {
        this.matriculaController = matriculaController;

        cargarCursos();
        cargarServicios();
        actualizarTabla();
    }

    // -------------------- ESTUDIANTE --------------------

    @FXML
    private void buscarEstudiante() {
        String documento =
                txtDocumentoEstudiante.getText().trim();

        estudianteEncontrado = null;
        txtNombreEstudiante.clear();
        txtCorreoEstudiante.clear();

        if (documento.isEmpty()) {
            mostrarMensaje("Escribe el documento del estudiante.");
            return;
        }

        estudianteEncontrado =
                matriculaController.buscarEstudiante(documento);

        if (estudianteEncontrado == null) {
            mostrarMensaje("No se encontró el estudiante.");
            return;
        }

        txtNombreEstudiante.setText(
                estudianteEncontrado.getNombre()
        );
        txtCorreoEstudiante.setText(
                estudianteEncontrado.getCorreo()
        );
    }

    // -------------------- CURSOS --------------------

    private void cargarCursos() {
        menuCursos.getItems().clear();

        for (Curso curso : matriculaController.listarCursos()) {
            CheckMenuItem opcion =
                    new CheckMenuItem(curso.getNombre());

            opcion.setSelected(
                    contieneCursoSeleccionado(curso.getCodigo())
            );

            opcion.selectedProperty().addListener(
                    (observable, anterior, marcado) -> {
                        if (marcado) {
                            if (!contieneCursoSeleccionado(
                                    curso.getCodigo()
                            )) {
                                cursosSeleccionados.add(curso);
                            }
                        } else {
                            cursosSeleccionados.removeIf(actual ->
                                    actual.getCodigo().equals(
                                            curso.getCodigo()
                                    )
                            );

                            profesoresAsignados.remove(
                                    curso.getCodigo()
                            );
                        }

                        actualizarCursosPersonalizados();
                        actualizarTextoSeleccionados();
                    }
            );

            menuCursos.getItems().add(opcion);
        }

        actualizarCursosPersonalizados();
        actualizarTextoSeleccionados();
    }

    private boolean contieneCursoSeleccionado(String codigo) {
        return cursosSeleccionados.stream().anyMatch(
                curso -> curso.getCodigo().equals(codigo)
        );
    }

    private void actualizarCursosPersonalizados() {
        CursoPersonalizado seleccionadoAntes =
                cmbCursoPersonalizado.getValue();

        List<CursoPersonalizado> personalizados =
                cursosSeleccionados.stream()
                        .filter(CursoPersonalizado.class::isInstance)
                        .map(CursoPersonalizado.class::cast)
                        .toList();

        cmbCursoPersonalizado.setItems(
                FXCollections.observableArrayList(
                        personalizados
                )
        );

        if (seleccionadoAntes != null) {
            cmbCursoPersonalizado.getItems().stream()
                    .filter(curso -> curso.getCodigo().equals(
                            seleccionadoAntes.getCodigo()
                    ))
                    .findFirst()
                    .ifPresent(cmbCursoPersonalizado::setValue);
        }

        boolean mostrar = !personalizados.isEmpty();
        panelAsignacionProfesor.setVisible(mostrar);
        panelAsignacionProfesor.setManaged(mostrar);

        if (!mostrar) {
            cmbCursoPersonalizado.setValue(null);
            cmbProfesor.setItems(
                    FXCollections.observableArrayList()
            );
        }

        actualizarTextoProfesores();
    }

    private void cargarProfesoresDelCurso(
            CursoPersonalizado curso
    ) {
        cmbProfesor.setValue(null);

        if (curso == null) {
            cmbProfesor.setItems(
                    FXCollections.observableArrayList()
            );
            return;
        }

        List<Profesor> compatibles =
                matriculaController.listarProfesores()
                        .stream()
                        .filter(profesor ->
                                profesor.getIdiomaQueEnsenia()
                                        == curso.getIdioma()
                        )
                        .toList();

        cmbProfesor.setItems(
                FXCollections.observableArrayList(
                        compatibles
                )
        );

        Profesor asignado =
                profesoresAsignados.get(curso.getCodigo());

        if (asignado != null) {
            cmbProfesor.setValue(asignado);
        }
    }

    @FXML
    private void asignarProfesor() {
        CursoPersonalizado curso =
                cmbCursoPersonalizado.getValue();
        Profesor profesor = cmbProfesor.getValue();

        if (curso == null || profesor == null) {
            mostrarMensaje(
                    "Selecciona el curso personalizado "
                            + "y un profesor."
            );
            return;
        }

        if (profesor.getIdiomaQueEnsenia()
                != curso.getIdioma()) {
            mostrarMensaje(
                    "El profesor debe enseñar "
                            + "el idioma del curso."
            );
            return;
        }

        profesoresAsignados.put(
                curso.getCodigo(),
                profesor
        );
        actualizarTextoProfesores();
        mostrarMensaje("Profesor asignado al curso.");
    }

    private void actualizarTextoProfesores() {
        if (profesoresAsignados.isEmpty()) {
            lblProfesoresAsignados.setText(
                    "No hay profesores asignados"
            );
            return;
        }

        List<String> asignaciones = new ArrayList<>();

        for (Curso curso : cursosSeleccionados) {
            Profesor profesor =
                    profesoresAsignados.get(
                            curso.getCodigo()
                    );

            if (profesor != null) {
                asignaciones.add(
                        curso.getNombre() + ": "
                                + profesor.getNombre()
                );
            }
        }

        lblProfesoresAsignados.setText(
                String.join(" | ", asignaciones)
        );
    }

    // -------------------- SERVICIOS --------------------

    private void cargarServicios() {
        menuServicios.getItems().clear();

        List<ServicioAdicional> servicios =
                matriculaController.listarServicios();

        cmbServicioGestion.setItems(
                FXCollections.observableArrayList(
                        servicios
                )
        );

        for (ServicioAdicional servicio : servicios) {
            if (!servicio.isDisponible()) {
                continue;
            }

            CheckMenuItem opcion =
                    new CheckMenuItem(
                            servicio.getNombre()
                    );

            opcion.setSelected(
                    contieneServicioSeleccionado(
                            servicio.getCodigo()
                    )
            );

            opcion.selectedProperty().addListener(
                    (observable, anterior, marcado) -> {
                        if (marcado) {
                            if (!contieneServicioSeleccionado(
                                    servicio.getCodigo()
                            )) {
                                serviciosSeleccionados.add(
                                        servicio
                                );
                            }
                        } else {
                            serviciosSeleccionados.removeIf(
                                    actual -> actual.getCodigo()
                                            .equals(
                                                    servicio.getCodigo()
                                            )
                            );
                        }

                        actualizarTextoSeleccionados();
                    }
            );

            menuServicios.getItems().add(opcion);
        }

        actualizarTextoSeleccionados();
    }

    private boolean contieneServicioSeleccionado(
            String codigo
    ) {
        return serviciosSeleccionados.stream().anyMatch(
                servicio -> servicio.getCodigo().equals(
                        codigo
                )
        );
    }

    @FXML
    private void crearServicio() {
        String codigo = txtCodigoServicio.getText().trim();
        String nombre = txtNombreServicio.getText().trim();
        String descripcion =
                txtDescripcionServicio.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()
                || txtPrecioServicio.getText().isBlank()) {
            mostrarMensaje(
                    "Completa código, nombre y precio."
            );
            return;
        }

        Double precio = leerNumero(
                txtPrecioServicio.getText(),
                "El precio"
        );

        if (precio == null || precio < 0) {
            mostrarMensaje(
                    "El precio debe ser mayor o igual a cero."
            );
            return;
        }

        boolean creado = matriculaController.crearServicio(
                codigo,
                nombre,
                descripcion,
                precio
        );

        if (!creado) {
            mostrarMensaje(
                    "No se pudo crear el servicio. "
                            + "Revisa si el código ya existe."
            );
            return;
        }

        txtCodigoServicio.clear();
        txtNombreServicio.clear();
        txtDescripcionServicio.clear();
        txtPrecioServicio.clear();

        cargarServicios();
        mostrarMensaje("Servicio creado correctamente.");
    }

    @FXML
    private void eliminarServicioCatalogo() {
        ServicioAdicional servicio =
                cmbServicioGestion.getValue();

        if (servicio == null) {
            mostrarMensaje("Selecciona un servicio.");
            return;
        }

        boolean eliminado =
                matriculaController.eliminarServicio(
                        servicio.getCodigo()
                );

        if (!eliminado) {
            mostrarMensaje(
                    "No se pudo eliminar el servicio."
            );
            return;
        }

        serviciosSeleccionados.removeIf(actual ->
                actual.getCodigo().equals(
                        servicio.getCodigo()
                )
        );

        cargarServicios();
        mostrarMensaje("Servicio eliminado del catálogo.");
    }

    @FXML
    private void cambiarDisponibilidadServicio() {
        ServicioAdicional servicio =
                cmbServicioGestion.getValue();

        if (servicio == null) {
            mostrarMensaje("Selecciona un servicio.");
            return;
        }

        boolean nuevaDisponibilidad =
                !servicio.isDisponible();

        boolean actualizado =
                matriculaController.cambiarDisponibilidadServicio(
                        servicio.getCodigo(),
                        nuevaDisponibilidad
                );

        if (!actualizado) {
            mostrarMensaje(
                    "No se pudo cambiar la disponibilidad."
            );
            return;
        }

        if (!nuevaDisponibilidad) {
            serviciosSeleccionados.removeIf(actual ->
                    actual.getCodigo().equals(
                            servicio.getCodigo()
                    )
            );
        }

        cargarServicios();
        cmbServicioGestion.setValue(servicio);
        mostrarDisponibilidad(servicio);
    }

    private void mostrarDisponibilidad(
            ServicioAdicional servicio
    ) {
        if (servicio == null) {
            lblDisponibilidadServicio.setText(
                    "Disponibilidad: selecciona un servicio"
            );
            return;
        }

        lblDisponibilidadServicio.setText(
                "Disponibilidad de "
                        + servicio.getNombre() + ": "
                        + (servicio.isDisponible()
                        ? "Disponible"
                        : "No disponible")
        );
    }

    private void actualizarTextoSeleccionados() {
        lblCursosSeleccionados.setText(
                cursosSeleccionados.isEmpty()
                        ? "Ningún curso seleccionado"
                        : cursosSeleccionados.stream()
                        .map(Curso::getNombre)
                        .collect(
                                Collectors.joining(", ")
                        )
        );

        lblServiciosSeleccionados.setText(
                serviciosSeleccionados.isEmpty()
                        ? "Ningún servicio seleccionado"
                        : serviciosSeleccionados.stream()
                        .map(ServicioAdicional::getNombre)
                        .collect(
                                Collectors.joining(", ")
                        )
        );

        menuCursos.setText(
                "Cursos: " + cursosSeleccionados.size()
        );
        menuServicios.setText(
                "Servicios: " + serviciosSeleccionados.size()
        );
    }

    // -------------------- CRUD MATRÍCULA --------------------

    @FXML
    private void registrar() {
        buscarEstudiante();
        String codigo = txtCodigoMatricula.getText().trim();

        if (codigo.isEmpty()) {
            mostrarMensaje("Escribe el código de la matrícula.");
            return;
        }
        if (estudianteEncontrado == null) {
            return;
        }

        if (cursosSeleccionados.isEmpty()) {
            mostrarMensaje(
                    "Selecciona al menos un curso."
            );
            return;
        }

        Integer duracion = leerDuracion();
        Double descuento = leerDescuento();

        if (duracion == null || descuento == null) {
            return;
        }

        Matricula.Builder builder =
                new Matricula.Builder()
                        .codigo(codigo)
                        .estudiante(estudianteEncontrado)
                        .duracionContratada(duracion)
                        .descuento(descuento);

        for (Curso curso : cursosSeleccionados) {
            builder.curso(curso);
        }

        for (ServicioAdicional servicio :
                serviciosSeleccionados) {
            builder.servicioAdicional(servicio);
        }

        Matricula matricula;

        try {
            matricula = builder.build();
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
            return;
        }

        for (Map.Entry<String, Profesor> asignacion :
                profesoresAsignados.entrySet()) {
            if (!matricula.asignarProfesor(
                    asignacion.getKey(),
                    asignacion.getValue()
            )) {
                mostrarMensaje(
                        "No se pudo asignar un profesor."
                );
                return;
            }
        }

        boolean registrada =
                matriculaController.registrarMatricula(
                        matricula
                );

        if (registrada) {
            actualizarTabla();
            limpiar();
            mostrarMensaje(
                    "Matrícula registrada correctamente."
            );
        } else {
            mostrarMensaje(
                    "El estudiante ya tiene una matrícula "
                            + "o algún dato no está registrado."
            );
        }
    }

    @FXML
    private void actualizar() {
        Matricula seleccionada =
                tablaMatriculas.getSelectionModel()
                        .getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje(
                    "Selecciona una matrícula de la tabla."
            );
            return;
        }

        Double descuento = leerDescuento();

        if (descuento == null
                || cursosSeleccionados.isEmpty()) {
            if (cursosSeleccionados.isEmpty()) {
                mostrarMensaje(
                        "Selecciona al menos un curso."
                );
            }
            return;
        }

        String documento =
                seleccionada.getEstudiante()
                        .getDocumentoDeIdentidad();

        // Quitar cursos que ya no están marcados.
        for (Curso anterior :
                List.copyOf(seleccionada.getListaCursos())) {
            if (!contieneCursoSeleccionado(
                    anterior.getCodigo()
            )) {
                matriculaController.quitarCursoDeMatricula(
                        documento,
                        anterior.getCodigo()
                );
            }
        }

        // Agregar cursos recién marcados.
        for (Curso nuevo : cursosSeleccionados) {
            boolean yaEsta =
                    seleccionada.getListaCursos()
                            .stream()
                            .anyMatch(actual ->
                                    actual.getCodigo().equals(
                                            nuevo.getCodigo()
                                    )
                            );

            if (!yaEsta) {
                matriculaController.agregarCursoAMatricula(
                        documento,
                        nuevo.getCodigo()
                );
            }
        }

        // Quitar servicios desmarcados.
        for (ServicioAdicional anterior :
                List.copyOf(
                        seleccionada.getServiciosAdicionales()
                )) {
            if (!contieneServicioSeleccionado(
                    anterior.getCodigo()
            )) {
                matriculaController.quitarServicioDeMatricula(
                        documento,
                        anterior
                );
            }
        }

        // Agregar servicios recién marcados.
        for (ServicioAdicional nuevo :
                serviciosSeleccionados) {
            boolean yaEsta =
                    seleccionada.getServiciosAdicionales()
                            .stream()
                            .anyMatch(actual ->
                                    actual.getCodigo().equals(
                                            nuevo.getCodigo()
                                    )
                            );

            if (!yaEsta) {
                matriculaController.agregarServicioAMatricula(
                        documento,
                        nuevo
                );
            }
        }

        for (Map.Entry<String, Profesor> asignacion :
                profesoresAsignados.entrySet()) {
            matriculaController.asignarProfesorAMatricula(
                    documento,
                    asignacion.getKey(),
                    asignacion.getValue()
                            .getDocumentoDeIdentidad()
            );
        }

        matriculaController.actualizarDescuentoMatricula(
                documento,
                descuento
        );

        tablaMatriculas.getSelectionModel().clearSelection();
        actualizarTabla();
        limpiar();
        mostrarMensaje(
                "Matrícula actualizada correctamente."
        );
    }

    @FXML
    private void eliminar() {
        Matricula seleccionada =
                tablaMatriculas.getSelectionModel()
                        .getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje(
                    "Selecciona una matrícula de la tabla."
            );
            return;
        }

        String documento =
                seleccionada.getEstudiante()
                        .getDocumentoDeIdentidad();

        boolean eliminada =
                matriculaController.eliminarMatricula(
                        documento
                );

        if (eliminada) {
            actualizarTabla();
            limpiar();
            mostrarMensaje(
                    "Matrícula eliminada correctamente."
            );
        } else {
            mostrarMensaje(
                    "No se encontró la matrícula."
            );
        }
    }

    @FXML
    private void buscar() {
        String documento =
                txtBuscarDocumento.getText().trim();

        if (documento.isEmpty()) {
            mostrarMensaje("Escribe un documento.");
            return;
        }

        Matricula encontrada =
                matriculaController.buscarMatricula(
                        documento
                );

        if (encontrada == null) {
            mostrarMensaje(
                    "No se encontró la matrícula."
            );
            return;
        }

        tablaMatriculas.setItems(
                FXCollections.observableArrayList(
                        encontrada
                )
        );
        tablaMatriculas.getSelectionModel()
                .select(encontrada);
    }

    @FXML
    private void mostrarTodas() {
        txtBuscarDocumento.clear();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaMatriculas.setItems(
                FXCollections.observableArrayList(
                        matriculaController.listarMatriculas()
                )
        );
        tablaMatriculas.refresh();
    }

    private void mostrarEnFormulario(
            Matricula matricula
    ) {
        txtCodigoMatricula.setText(matricula.getCodigo());
        txtCodigoMatricula.setEditable(false);

        estudianteEncontrado =
                matricula.getEstudiante();

        txtDocumentoEstudiante.setText(
                estudianteEncontrado
                        .getDocumentoDeIdentidad()
        );
        txtDocumentoEstudiante.setEditable(false);

        txtNombreEstudiante.setText(
                estudianteEncontrado.getNombre()
        );
        txtCorreoEstudiante.setText(
                estudianteEncontrado.getCorreo()
        );

        txtDuracionContratada.setText(
                String.valueOf(
                        matricula.getDuracionContratada()
                )
        );

        // En el modelo actual la duración no se puede
        // cambiar después de crear la matrícula.
        txtDuracionContratada.setEditable(false);

        txtDescuento.setText(
                String.valueOf(matricula.getDescuento())
        );

        cursosSeleccionados.clear();
        cursosSeleccionados.addAll(
                matricula.getListaCursos()
        );
        cargarCursos();

        serviciosSeleccionados.clear();
        serviciosSeleccionados.addAll(
                matricula.getServiciosAdicionales()
        );
        cargarServicios();

        profesoresAsignados.clear();

        for (Curso curso : matricula.getListaCursos()) {
            if (curso instanceof CursoPersonalizado) {
                Profesor profesor =
                        matricula.getProfesorAsignado(
                                curso.getCodigo()
                        );

                if (profesor != null) {
                    profesoresAsignados.put(
                            curso.getCodigo(),
                            profesor
                    );
                }
            }
        }

        actualizarCursosPersonalizados();
        actualizarTextoProfesores();

        lblValorTotal.setText(
                "Valor total de la matrícula: "
                        + formatoMoneda.format(
                        matricula.getValorTotal()
                )
        );
    }

    @FXML
    private void limpiar() {
        tablaMatriculas.getSelectionModel()
                .clearSelection();

        estudianteEncontrado = null;
        txtCodigoMatricula.clear();
        txtCodigoMatricula.setEditable(true);
        txtDocumentoEstudiante.clear();
        txtDocumentoEstudiante.setEditable(true);
        txtNombreEstudiante.clear();
        txtCorreoEstudiante.clear();

        txtDuracionContratada.clear();
        txtDuracionContratada.setEditable(true);
        txtDescuento.setText("0");
        txtBuscarDocumento.clear();

        cursosSeleccionados.clear();
        serviciosSeleccionados.clear();
        profesoresAsignados.clear();

        cargarCursos();
        cargarServicios();

        cmbCursoPersonalizado.setValue(null);
        cmbProfesor.setValue(null);
        cmbServicioGestion.setValue(null);

        lblValorTotal.setText(
                "Valor total de la matrícula: 0"
        );
    }

    private Integer leerDuracion() {
        try {
            int duracion = Integer.parseInt(
                    txtDuracionContratada.getText().trim()
            );

            if (duracion <= 0) {
                mostrarMensaje(
                        "La duración debe ser mayor que cero."
                );
                return null;
            }

            return duracion;
        } catch (NumberFormatException e) {
            mostrarMensaje(
                    "La duración debe ser un número entero."
            );
            return null;
        }
    }

    private Double leerDescuento() {
        String texto = txtDescuento.getText().trim();

        if (texto.isEmpty()) {
            return 0.0;
        }

        Double valor = leerNumero(
                texto,
                "El descuento"
        );

        if (valor != null && valor < 0) {
            mostrarMensaje(
                    "El descuento no puede ser negativo."
            );
            return null;
        }

        return valor;
    }

    private Double leerNumero(
            String texto,
            String nombreCampo
    ) {
        try {
            double valor = Double.parseDouble(
                    texto.trim()
            );

            if (!Double.isFinite(valor)) {
                mostrarMensaje(
                        nombreCampo + " no es válido."
                );
                return null;
            }

            return valor;
        } catch (NumberFormatException e) {
            mostrarMensaje(
                    nombreCampo
                            + " debe ser un número válido."
            );
            return null;
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION
        );
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}