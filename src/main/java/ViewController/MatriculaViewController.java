package ViewController;

import App.App;
import Controller.MatriculaController;
import Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatriculaViewController {

    private App app;
    private MatriculaController matriculaController;

    private Estudiante estudianteActual;

    private List<Curso> cursosSeleccionados =
            new ArrayList<>();

    private List<ServicioAdicional> serviciosSeleccionados =
            new ArrayList<>();


    // ==========================================
    // CAMPOS DE ESTUDIANTE
    // ==========================================

    @FXML private TextField txtCodigoMatricula;
    @FXML private TextField txtDocumentoEstudiante;
    @FXML private TextField txtNombreEstudiante;
    @FXML private TextField txtCorreoEstudiante;

    @FXML private TextField txtDuracionContratada;
    @FXML private TextField txtDescuento;


    // ==========================================
    // CURSOS
    // ==========================================

    @FXML private MenuButton menuCursos;
    @FXML private Label lblCursosSeleccionados;


    // ==========================================
    // SERVICIOS
    // ==========================================

    @FXML private MenuButton menuServicios;
    @FXML private Label lblServiciosSeleccionados;


    // ==========================================
    // CREAR SERVICIO
    // ==========================================

    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtNombreServicio;
    @FXML private TextArea txtDescripcionServicio;
    @FXML private TextField txtPrecioServicio;


    // ==========================================
    // GESTIÓN DE SERVICIOS
    // ==========================================

    @FXML private Button btnCambiarDisponibilidad;
    @FXML private ComboBox<ServicioAdicional> cmbServicioGestion;
    @FXML private Label lblDisponibilidadServicio;


    // ==========================================
    // ASIGNACIÓN DE PROFESOR
    // ==========================================

    @FXML private VBox panelAsignacionProfesor;
    @FXML private ComboBox<CursoPersonalizado> cmbCursoPersonalizado;
    @FXML private ComboBox<Profesor> cmbProfesor;
    @FXML private Label lblProfesoresAsignados;


    // ==========================================
    // TABLA
    // ==========================================

    @FXML private TableView<Matricula> tablaMatriculas;

    @FXML private TableColumn<Matricula, String>
            colCodigoMatricula;

    @FXML private TableColumn<Matricula, String>
            colDocumento;

    @FXML private TableColumn<Matricula, String>
            colEstudiante;

    @FXML private TableColumn<Matricula, LocalDate>
            colFecha;

    @FXML private TableColumn<Matricula, String>
            colCursos;

    @FXML private TableColumn<Matricula, String>
            colServicios;

    @FXML private TableColumn<Matricula, String>
            colProfesores;

    @FXML private TableColumn<Matricula, Double>
            colDescuento;

    @FXML private TableColumn<Matricula, Double>
            colValorTotal;


    // ==========================================
    // CONECTAR APP
    // ==========================================

    public void setApp(App app) {

        this.app = app;

        this.matriculaController =
                new MatriculaController(
                        app.getAcademia()
                );

        configurarTabla();
        configurarProfesores();

        mostrarTodas();
    }


    // ==========================================
    // CONFIGURAR TABLA
    // ==========================================

    private void configurarTabla() {

        colCodigoMatricula.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colDocumento.setCellValueFactory(
                datos -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue()
                                .getEstudiante()
                                .getDocumentoDeIdentidad()
                )
        );

        colEstudiante.setCellValueFactory(
                datos -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue()
                                .getEstudiante()
                                .getNombre()
                )
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fechaMatricula")
        );

        colDescuento.setCellValueFactory(
                new PropertyValueFactory<>("descuento")
        );

        colValorTotal.setCellValueFactory(
                new PropertyValueFactory<>("valorTotal")
        );


        colCursos.setCellValueFactory(datos -> {

            Matricula matricula =
                    datos.getValue();

            String cursos =
                    matricula.getListaCursos()
                            .stream()
                            .map(Curso::getNombre)
                            .reduce(
                                    (a, b) ->
                                            a + ", " + b
                            )
                            .orElse("");

            return new javafx.beans.property
                    .SimpleStringProperty(cursos);
        });


        colServicios.setCellValueFactory(datos -> {

            Matricula matricula =
                    datos.getValue();

            String servicios =
                    matricula
                            .getServiciosAdicionales()
                            .stream()
                            .map(
                                    ServicioAdicional::getNombre
                            )
                            .reduce(
                                    (a, b) ->
                                            a + ", " + b
                            )
                            .orElse("");

            return new javafx.beans.property
                    .SimpleStringProperty(servicios);
        });


        colProfesores.setCellValueFactory(datos -> {

            Matricula matricula =
                    datos.getValue();

            String profesores =
                    matricula
                            .getProfesoresAsignados()
                            .stream()
                            .map(
                                    Profesor::getNombre
                            )
                            .reduce(
                                    (a, b) ->
                                            a + ", " + b
                            )
                            .orElse("");

            return new javafx.beans.property
                    .SimpleStringProperty(profesores);
        });
    }


    // ==========================================
    // BUSCAR ESTUDIANTE
    // ==========================================

    @FXML
    private void buscarEstudiante() {

        String documento =
                txtDocumentoEstudiante
                        .getText()
                        .trim();


        if (documento.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese el documento del estudiante."
            );

            return;
        }


        estudianteActual =
                matriculaController
                        .buscarEstudiante(documento);


        if (estudianteActual == null) {

            mostrarMensaje(
                    "Resultado",
                    "No se encontró el estudiante."
            );

            limpiarDatosEstudiante();

            return;
        }


        txtNombreEstudiante.setText(
                estudianteActual.getNombre()
        );

        txtCorreoEstudiante.setText(
                estudianteActual.getCorreo()
        );
    }


    // ==========================================
    // CARGAR CURSOS
    // ==========================================

    private void cargarCursos() {

        menuCursos.getItems().clear();


        for (Curso curso :
                matriculaController.listarCursos()) {

            CheckMenuItem item =
                    new CheckMenuItem(
                            curso.getCodigo()
                                    + " - "
                                    + curso.getNombre()
                    );


            item.setOnAction(event -> {

                if (item.isSelected()) {

                    if (!cursosSeleccionados
                            .contains(curso)) {

                        cursosSeleccionados
                                .add(curso);
                    }

                } else {

                    cursosSeleccionados
                            .remove(curso);
                }


                actualizarTextoCursos();
                actualizarCursosPersonalizados();
            });


            menuCursos.getItems()
                    .add(item);
        }
    }


    // ==========================================
    // ACTUALIZAR TEXTO CURSOS
    // ==========================================

    private void actualizarTextoCursos() {

        if (cursosSeleccionados.isEmpty()) {

            lblCursosSeleccionados.setText(
                    "Ningún curso seleccionado"
            );

            return;
        }


        String texto =
                cursosSeleccionados
                        .stream()
                        .map(Curso::getNombre)
                        .reduce(
                                (a, b) ->
                                        a + ", " + b
                        )
                        .orElse("");


        lblCursosSeleccionados.setText(
                texto
        );
    }


    // ==========================================
    // CARGAR SERVICIOS
    // ==========================================

    private void cargarServicios() {

        menuServicios.getItems().clear();

        cmbServicioGestion.getItems().clear();


        List<ServicioAdicional> servicios =
                matriculaController
                        .listarServicios();


        for (ServicioAdicional servicio :
                servicios) {

            CheckMenuItem item =
                    new CheckMenuItem(
                            servicio.getCodigo()
                                    + " - "
                                    + servicio.getNombre()
                    );


            item.setDisable(
                    !servicio.isDisponible()
            );


            item.setOnAction(event -> {

                if (item.isSelected()) {

                    if (!serviciosSeleccionados
                            .contains(servicio)) {

                        serviciosSeleccionados
                                .add(servicio);
                    }

                } else {

                    serviciosSeleccionados
                            .remove(servicio);
                }


                actualizarTextoServicios();
            });


            menuServicios.getItems()
                    .add(item);

            cmbServicioGestion
                    .getItems()
                    .add(servicio);
        }
    }


    // ==========================================
    // ACTUALIZAR TEXTO SERVICIOS
    // ==========================================

    private void actualizarTextoServicios() {

        if (serviciosSeleccionados.isEmpty()) {

            lblServiciosSeleccionados.setText(
                    "Ningún servicio seleccionado"
            );

            return;
        }


        String texto =
                serviciosSeleccionados
                        .stream()
                        .map(
                                ServicioAdicional::getNombre
                        )
                        .reduce(
                                (a, b) ->
                                        a + ", " + b
                        )
                        .orElse("");


        lblServiciosSeleccionados.setText(
                texto
        );
    }


    // ==========================================
    // CREAR SERVICIO
    // ==========================================

    @FXML
    private void crearServicio() {

        try {

            String codigo =
                    txtCodigoServicio
                            .getText()
                            .trim();

            String nombre =
                    txtNombreServicio
                            .getText()
                            .trim();

            String descripcion =
                    txtDescripcionServicio
                            .getText()
                            .trim();

            double precio =
                    Double.parseDouble(
                            txtPrecioServicio
                                    .getText()
                    );


            if (codigo.isBlank()
                    || nombre.isBlank()) {

                mostrarMensaje(
                        "Error",
                        "Ingrese código y nombre."
                );

                return;
            }


            ServicioAdicional servicio =
                    new ServicioAdicional.Builder()
                            .codigo(codigo)
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .precio(precio)
                            .disponible(true)
                            .build();


            /*
             * Actualmente los servicios se
             * almacenan como beneficios dentro
             * de los cursos.
             *
             * Por eso este método necesita
             * un curso seleccionado para poder
             * incorporarlo al catálogo actual.
             */

            if (cursosSeleccionados.isEmpty()) {

                mostrarMensaje(
                        "Información",
                        "Seleccione primero un curso para asociar el servicio."
                );

                return;
            }


            for (Curso curso :
                    cursosSeleccionados) {

                curso.agregarBeneficio(
                        servicio
                );
            }


            cargarServicios();


            txtCodigoServicio.clear();
            txtNombreServicio.clear();
            txtDescripcionServicio.clear();
            txtPrecioServicio.clear();


            mostrarMensaje(
                    "Éxito",
                    "Servicio creado correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "El precio debe ser un número."
            );
        }
    }


    // ==========================================
    // ELIMINAR SERVICIO DEL CATÁLOGO
    // ==========================================

    @FXML
    private void eliminarServicioCatalogo() {

        ServicioAdicional servicio =
                cmbServicioGestion.getValue();


        if (servicio == null) {

            mostrarMensaje(
                    "Error",
                    "Seleccione un servicio."
            );

            return;
        }


        for (Curso curso :
                matriculaController.listarCursos()) {

            curso.eliminarBeneficio(
                    servicio
            );
        }


        cargarServicios();


        mostrarMensaje(
                "Éxito",
                "Servicio eliminado del catálogo."
        );
    }


    // ==========================================
    // CAMBIAR DISPONIBILIDAD
    // ==========================================

    @FXML
    private void cambiarDisponibilidadServicio() {

        ServicioAdicional servicio =
                cmbServicioGestion.getValue();


        if (servicio == null) {

            mostrarMensaje(
                    "Error",
                    "Seleccione un servicio."
            );

            return;
        }


        servicio.setDisponible(
                !servicio.isDisponible()
        );


        mostrarDisponibilidadServicio(
                servicio
        );


        cargarServicios();
    }


    private void mostrarDisponibilidadServicio(
            ServicioAdicional servicio) {

        if (servicio.isDisponible()) {

            lblDisponibilidadServicio
                    .setText("Disponible");

        } else {

            lblDisponibilidadServicio
                    .setText("No disponible");
        }
    }


    // ==========================================
    // PROFESORES
    // ==========================================

    private void configurarProfesores() {

        cmbProfesor.setItems(
                FXCollections.observableArrayList(
                        matriculaController
                                .listarProfesores()
                )
        );


        cmbCursoPersonalizado
                .setItems(
                        FXCollections.observableArrayList()
                );


        actualizarCursosPersonalizados();
    }


    private void actualizarCursosPersonalizados() {

        ObservableList<CursoPersonalizado>
                personalizados =
                FXCollections.observableArrayList();


        for (Curso curso :
                cursosSeleccionados) {

            if (curso instanceof CursoPersonalizado personalizado) {

                personalizados.add(
                        personalizado
                );
            }
        }


        cmbCursoPersonalizado
                .setItems(personalizados);


        panelAsignacionProfesor
                .setVisible(
                        !personalizados.isEmpty()
                );

        panelAsignacionProfesor
                .setManaged(
                        !personalizados.isEmpty()
                );
    }


    // ==========================================
    // ASIGNAR PROFESOR
    // ==========================================

    @FXML
    private void asignarProfesor() {

        if (estudianteActual == null) {

            mostrarMensaje(
                    "Error",
                    "Primero busque un estudiante."
            );

            return;
        }


        CursoPersonalizado curso =
                cmbCursoPersonalizado
                        .getValue();

        Profesor profesor =
                cmbProfesor.getValue();


        if (curso == null
                || profesor == null) {

            mostrarMensaje(
                    "Error",
                    "Seleccione curso y profesor."
            );

            return;
        }


        /*
         * La asignación se realiza después
         * de crear la matrícula.
         *
         * Por ahora se guarda temporalmente
         * en la interfaz.
         */

        lblProfesoresAsignados.setText(
                profesor.getNombre()
        );


        mostrarMensaje(
                "Información",
                "El profesor seleccionado quedará asignado al curso personalizado."
        );
    }


    // ==========================================
    // REGISTRAR MATRÍCULA
    // ==========================================

    @FXML
    private void registrar() {

        try {

            if (estudianteActual == null) {

                mostrarMensaje(
                        "Error",
                        "Primero busque un estudiante."
                );

                return;
            }


            String codigo =
                    txtCodigoMatricula
                            .getText()
                            .trim();


            if (codigo.isBlank()) {

                mostrarMensaje(
                        "Error",
                        "Ingrese el código de matrícula."
                );

                return;
            }


            int duracion =
                    Integer.parseInt(
                            txtDuracionContratada
                                    .getText()
                    );


            double descuento = 0;

            if (!txtDescuento
                    .getText()
                    .isBlank()) {

                descuento =
                        Double.parseDouble(
                                txtDescuento
                                        .getText()
                        );
            }


            if (duracion <= 0) {

                mostrarMensaje(
                        "Error",
                        "La duración debe ser mayor que cero."
                );

                return;
            }


            Matricula matricula =
                    matriculaController
                            .registrarMatricula(
                                    codigo,
                                    estudianteActual,
                                    duracion,
                                    descuento,
                                    cursosSeleccionados,
                                    serviciosSeleccionados
                            );


            mostrarTodas();


            mostrarMensaje(
                    "Éxito",
                    "La matrícula fue registrada correctamente.\n"
                            + "Valor total: "
                            + matricula.getValorTotal()
            );


            limpiar();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "Revise los valores numéricos."
            );
        }
    }


    // ==========================================
    // ACTUALIZAR
    // ==========================================

    @FXML
    private void actualizar() {

        try {

            String codigoOriginal =
                    txtCodigoMatricula
                            .getText()
                            .trim();


            Matricula matricula =
                    matriculaController
                            .buscarMatricula(
                                    codigoOriginal
                            );


            if (matricula == null) {

                mostrarMensaje(
                        "Error",
                        "No se encontró la matrícula."
                );

                return;
            }


            if (estudianteActual == null) {

                estudianteActual =
                        matricula.getEstudiante();
            }


            int duracion =
                    Integer.parseInt(
                            txtDuracionContratada
                                    .getText()
                    );


            double descuento = 0;

            if (!txtDescuento
                    .getText()
                    .isBlank()) {

                descuento =
                        Double.parseDouble(
                                txtDescuento
                                        .getText()
                        );
            }


            boolean actualizado =
                    matriculaController
                            .actualizarMatricula(
                                    codigoOriginal,
                                    codigoOriginal,
                                    estudianteActual,
                                    duracion,
                                    descuento,
                                    cursosSeleccionados,
                                    serviciosSeleccionados
                            );


            if (actualizado) {

                mostrarTodas();

                mostrarMensaje(
                        "Éxito",
                        "La matrícula fue actualizada."
                );

            } else {

                mostrarMensaje(
                        "Error",
                        "No fue posible actualizar la matrícula."
                );
            }

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "Revise los valores numéricos."
            );
        }
    }


    // ==========================================
    // ELIMINAR
    // ==========================================

    @FXML
    private void eliminar() {

        String codigo =
                txtCodigoMatricula
                        .getText()
                        .trim();


        if (codigo.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese el código de matrícula."
            );

            return;
        }


        boolean eliminado =
                matriculaController
                        .eliminarMatricula(
                                codigo
                        );


        if (eliminado) {

            mostrarTodas();
            limpiar();

            mostrarMensaje(
                    "Éxito",
                    "La matrícula fue eliminada."
            );

        } else {

            mostrarMensaje(
                    "Error",
                    "No se encontró la matrícula."
            );
        }
    }


    // ==========================================
    // BUSCAR MATRÍCULA
    // ==========================================

    @FXML
    private void buscar() {

        String codigo =
                txtCodigoMatricula
                        .getText()
                        .trim();


        Matricula matricula =
                matriculaController
                        .buscarMatricula(
                                codigo
                        );


        if (matricula == null) {

            mostrarMensaje(
                    "Resultado",
                    "No se encontró la matrícula."
            );

            return;
        }


        cargarMatricula(
                matricula
        );
    }


    // ==========================================
    // CARGAR MATRÍCULA
    // ==========================================

    private void cargarMatricula(
            Matricula matricula) {

        txtCodigoMatricula.setText(
                matricula.getCodigo()
        );

        estudianteActual =
                matricula.getEstudiante();


        txtDocumentoEstudiante.setText(
                estudianteActual
                        .getDocumentoDeIdentidad()
        );

        txtNombreEstudiante.setText(
                estudianteActual
                        .getNombre()
        );

        txtCorreoEstudiante.setText(
                estudianteActual
                        .getCorreo()
        );


        txtDuracionContratada.setText(
                String.valueOf(
                        matricula
                                .getDuracionContratada()
                )
        );

        txtDescuento.setText(
                String.valueOf(
                        matricula.getDescuento()
                )
        );


        cursosSeleccionados =
                new ArrayList<>(
                        matricula.getListaCursos()
                );


        serviciosSeleccionados =
                new ArrayList<>(
                        matricula
                                .getServiciosAdicionales()
                );


        actualizarTextoCursos();
        actualizarTextoServicios();
        actualizarCursosPersonalizados();
    }


    // ==========================================
    // MOSTRAR TODAS
    // ==========================================

    @FXML
    private void mostrarTodas() {

        ObservableList<Matricula>
                matriculas =
                FXCollections.observableArrayList(
                        matriculaController
                                .listarMatriculas()
                );


        tablaMatriculas.setItems(
                matriculas
        );


        cargarCursos();
        cargarServicios();
    }


    // ==========================================
    // LIMPIAR
    // ==========================================

    @FXML
    private void limpiar() {

        txtCodigoMatricula.clear();

        txtDocumentoEstudiante.clear();

        txtNombreEstudiante.clear();

        txtCorreoEstudiante.clear();

        txtDuracionContratada.clear();

        txtDescuento.clear();


        estudianteActual = null;


        cursosSeleccionados.clear();

        serviciosSeleccionados.clear();


        lblCursosSeleccionados.setText(
                "Ningún curso seleccionado"
        );

        lblServiciosSeleccionados.setText(
                "Ningún servicio seleccionado"
        );

        lblProfesoresAsignados.setText(
                ""
        );


        cmbCursoPersonalizado
                .getSelectionModel()
                .clearSelection();

        cmbProfesor
                .getSelectionModel()
                .clearSelection();


        panelAsignacionProfesor
                .setVisible(false);

        panelAsignacionProfesor
                .setManaged(false);


        tablaMatriculas
                .getSelectionModel()
                .clearSelection();
    }


    private void limpiarDatosEstudiante() {

        estudianteActual = null;

        txtNombreEstudiante.clear();

        txtCorreoEstudiante.clear();
    }


    // ==========================================
    // MENSAJES
    // ==========================================

    private void mostrarMensaje(
            String titulo,
            String mensaje) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}