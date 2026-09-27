package ViewController;

import App.App;
import Controller.ProfesorController;
import Model.Idioma;
import Model.Profesor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProfesorViewController {

    private App app;
    private ProfesorController profesorController;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<Idioma> cmbIdioma;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTarifaSesion;

    @FXML
    private TextField txtBuscarDocumento;

    @FXML
    private TableView<Profesor> tablaProfesores;

    @FXML
    private TableColumn<Profesor, String> colDocumento;

    @FXML
    private TableColumn<Profesor, String> colNombre;

    @FXML
    private TableColumn<Profesor, Idioma> colIdioma;

    @FXML
    private TableColumn<Profesor, String> colTelefono;

    @FXML
    private TableColumn<Profesor, Double> colTarifaSesion;


    public void setApp(App app) {

        this.app = app;

        this.profesorController =
                new ProfesorController(
                        app.getAcademia()
                );

        configurarIdioma();

        configurarTabla();

        mostrarTodos();
    }


    private void configurarIdioma() {

        cmbIdioma.setItems(
                FXCollections.observableArrayList(
                        Idioma.values()
                )
        );
    }


    private void configurarTabla() {

        colDocumento.setCellValueFactory(
                new PropertyValueFactory<>("documentoDeIdentidad")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colIdioma.setCellValueFactory(
                new PropertyValueFactory<>("idiomaQueEnsenia")
        );

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        colTarifaSesion.setCellValueFactory(
                new PropertyValueFactory<>("tarifaPorSesion")
        );
    }


    @FXML
    private void registrar() {

        try {

            String documento =
                    txtDocumento.getText();

            String nombre =
                    txtNombre.getText();

            String telefono =
                    txtTelefono.getText();

            Idioma idioma =
                    cmbIdioma.getValue();

            double tarifa =
                    Double.parseDouble(
                            txtTarifaSesion.getText()
                    );

            if (documento.isBlank()
                    || nombre.isBlank()
                    || telefono.isBlank()
                    || idioma == null) {

                mostrarMensaje(
                        "Error",
                        "Complete todos los campos."
                );

                return;
            }

            profesorController.registrarProfesor(
                    nombre,
                    documento,
                    telefono,
                    idioma,
                    tarifa
            );

            mostrarTodos();

            limpiar();

            mostrarMensaje(
                    "Éxito",
                    "El profesor fue registrado correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "La tarifa debe ser un número."
            );
        }
    }


    @FXML
    private void actualizar() {

        try {

            String documento =
                    txtDocumento.getText();

            Profesor profesorActual =
                    profesorController.buscarProfesor(
                            documento
                    );

            if (profesorActual == null) {

                mostrarMensaje(
                        "Error",
                        "No se encontró el profesor."
                );

                return;
            }

            String nombre =
                    txtNombre.getText();

            String telefono =
                    txtTelefono.getText();

            Idioma idioma =
                    cmbIdioma.getValue();

            double tarifa =
                    Double.parseDouble(
                            txtTarifaSesion.getText()
                    );

            Profesor profesorActualizado =
                    new Profesor.Builder()
                            .nombre(nombre)
                            .documentoDeIdentidad(
                                    profesorActual
                                            .getDocumentoDeIdentidad()
                            )
                            .telefono(telefono)
                            .idiomaQueEnsenia(idioma)
                            .tarifaPorSesion(tarifa)
                            .listaDeCursosProfesor(
                                    profesorActual
                                            .getListaDeCursosProfesor()
                            )
                            .listaEstudiantesProfesor(
                                    profesorActual
                                            .getListaEstudiantesProfesor()
                            )
                            .build();

            profesorController.actualizarProfesor(
                    documento,
                    profesorActualizado
            );

            mostrarTodos();

            mostrarMensaje(
                    "Éxito",
                    "Los datos del profesor fueron actualizados."
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "La tarifa debe ser un número."
            );
        }
    }


    @FXML
    private void eliminar() {

        String documento =
                txtDocumento.getText();

        if (documento.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese el documento del profesor."
            );

            return;
        }

        profesorController.eliminarProfesor(
                documento
        );

        mostrarTodos();

        limpiar();
    }


    @FXML
    private void limpiar() {

        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtTarifaSesion.clear();
        txtBuscarDocumento.clear();

        cmbIdioma.getSelectionModel()
                .clearSelection();

        tablaProfesores.getSelectionModel()
                .clearSelection();
    }


    @FXML
    private void buscar() {

        String documento =
                txtBuscarDocumento.getText();

        if (documento.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese un documento para buscar."
            );

            return;
        }

        Profesor profesor =
                profesorController.buscarProfesor(
                        documento
                );

        if (profesor == null) {

            mostrarMensaje(
                    "Resultado",
                    "No se encontró el profesor."
            );

            return;
        }

        cargarDatos(profesor);
    }


    @FXML
    private void mostrarTodos() {

        ObservableList<Profesor> profesores =
                FXCollections.observableArrayList(
                        profesorController.listarProfesores()
                );

        tablaProfesores.setItems(profesores);
    }


    private void cargarDatos(Profesor profesor) {

        txtDocumento.setText(
                profesor.getDocumentoDeIdentidad()
        );

        txtNombre.setText(
                profesor.getNombre()
        );

        txtTelefono.setText(
                profesor.getTelefono()
        );

        txtTarifaSesion.setText(
                String.valueOf(
                        profesor.getTarifaPorSesion()
                )
        );

        cmbIdioma.setValue(
                profesor.getIdiomaQueEnsenia()
        );
    }


    private void mostrarMensaje(
            String titulo,
            String mensaje
    ) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}