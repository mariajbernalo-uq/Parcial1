package ViewController;

import App.App;
import Controller.EstudianteController;
import Model.Estudiante;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class EstudianteViewController {

    private App app;
    private EstudianteController estudianteController;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField fechaRegistro;

    @FXML
    private TextField txtBuscarDocumento;

    @FXML
    private TableView<Estudiante> tablaEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> colNombre;

    @FXML
    private TableColumn<Estudiante, String> colDocumento;

    @FXML
    private TableColumn<Estudiante, String> colTelefono;

    @FXML
    private TableColumn<Estudiante, String> colCorreo;

    @FXML
    private TableColumn<Estudiante, Integer> colEdad;

    @FXML
    private TableColumn<Estudiante, LocalDate> colFechaRegistro;


    // ==========================================
    // CONECTAR CON APP
    // ==========================================

    public void setApp(App app) {

        this.app = app;

        // Aquí se crea el Controller de estudiante
        this.estudianteController =
                new EstudianteController(
                        app.getAcademia()
                );

        configurarTabla();

        mostrarTodos();
    }


    // ==========================================
    // CONFIGURAR TABLA
    // ==========================================

    private void configurarTabla() {

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colDocumento.setCellValueFactory(
                new PropertyValueFactory<>("documentoDeIdentidad")
        );

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo")
        );

        colEdad.setCellValueFactory(
                new PropertyValueFactory<>("edad")
        );

        colFechaRegistro.setCellValueFactory(
                new PropertyValueFactory<>("fechaDeRegistro")
        );
    }


    // ==========================================
    // REGISTRAR
    // ==========================================

    @FXML
    private void registrar() {

        try {

            String nombre =
                    txtNombre.getText();

            String documento =
                    txtDocumento.getText();

            String telefono =
                    txtTelefono.getText();

            String correo =
                    txtCorreo.getText();

            int edad =
                    Integer.parseInt(
                            txtEdad.getText()
                    );

            estudianteController.registrarEstudiante(
                    nombre,
                    documento,
                    telefono,
                    correo,
                    edad
            );

            mostrarMensaje(
                    "Estudiante registrado correctamente."
            );

            mostrarTodos();

            limpiar();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "La edad debe ser un número."
            );

        } catch (Exception e) {

            mostrarMensaje(
                    "No fue posible registrar el estudiante."
            );

            e.printStackTrace();
        }
    }


    // ==========================================
    // ACTUALIZAR
    // ==========================================

    @FXML
    private void actualizar() {

        try {

            String documento =
                    txtDocumento.getText();

            Estudiante estudiante =
                    estudianteController.buscarEstudiante(
                            documento
                    );

            if (estudiante == null) {

                mostrarMensaje(
                        "No se encontró el estudiante."
                );

                return;
            }

            estudiante.setNombre(
                    txtNombre.getText()
            );

            estudiante.setTelefono(
                    txtTelefono.getText()
            );

            estudiante.setCorreo(
                    txtCorreo.getText()
            );

            estudiante.setEdad(
                    Integer.parseInt(
                            txtEdad.getText()
                    )
            );

            estudianteController.actualizarDatosEstudiante(
                    documento,
                    estudiante
            );

            mostrarMensaje(
                    "Estudiante actualizado correctamente."
            );

            mostrarTodos();

            limpiar();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "La edad debe ser un número."
            );

        } catch (Exception e) {

            mostrarMensaje(
                    "No fue posible actualizar el estudiante."
            );

            e.printStackTrace();
        }
    }


    // ==========================================
    // ELIMINAR
    // ==========================================

    @FXML
    private void eliminar() {

        String documento =
                txtDocumento.getText();

        if (documento.isEmpty()) {

            mostrarMensaje(
                    "Ingrese el documento del estudiante."
            );

            return;
        }

        estudianteController.eliminarEstudiante(
                documento
        );

        mostrarMensaje(
                "Proceso de eliminación realizado."
        );

        mostrarTodos();

        limpiar();
    }


    // ==========================================
    // BUSCAR
    // ==========================================

    @FXML
    private void buscar() {

        String documento =
                txtBuscarDocumento.getText();

        Estudiante estudiante =
                estudianteController.buscarEstudiante(
                        documento
                );

        if (estudiante == null) {

            mostrarMensaje(
                    "No se encontró el estudiante."
            );

            return;
        }

        cargarDatos(estudiante);
    }


    // ==========================================
    // MOSTRAR TODOS
    // ==========================================

    @FXML
    private void mostrarTodos() {

        if (estudianteController == null) {
            return;
        }

        tablaEstudiantes.setItems(
                FXCollections.observableArrayList(
                        estudianteController.listarEstudiantes()
                )
        );
    }


    // ==========================================
    // CARGAR DATOS
    // ==========================================

    private void cargarDatos(Estudiante estudiante) {

        txtNombre.setText(
                estudiante.getNombre()
        );

        txtDocumento.setText(
                estudiante.getDocumentoDeIdentidad()
        );

        txtTelefono.setText(
                estudiante.getTelefono()
        );

        txtCorreo.setText(
                estudiante.getCorreo()
        );

        txtEdad.setText(
                String.valueOf(
                        estudiante.getEdad()
                )
        );

        if (estudiante.getFechaDeRegistro() != null) {

            fechaRegistro.setText(
                    estudiante.getFechaDeRegistro()
                            .toString()
            );
        }
    }


    // ==========================================
    // LIMPIAR
    // ==========================================

    @FXML
    private void limpiar() {

        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        fechaRegistro.clear();
        txtBuscarDocumento.clear();
    }


    // ==========================================
    // MENSAJE
    // ==========================================

    private void mostrarMensaje(String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle("Estudiantes");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}