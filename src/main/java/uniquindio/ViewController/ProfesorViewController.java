package uniquindio.ViewController;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import uniquindio.Controller.ProfesorController;
import uniquindio.Model.Idioma;
import uniquindio.Model.Profesor;

public class ProfesorViewController {

    private ProfesorController profesorController;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Idioma> cmbIdioma;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtTarifaSesion;
    @FXML private TextField txtBuscarDocumento;

    @FXML private TableView<Profesor> tablaProfesores;

    @FXML private TableColumn<Profesor, String> colDocumento;
    @FXML private TableColumn<Profesor, String> colNombre;
    @FXML private TableColumn<Profesor, Idioma> colIdioma;
    @FXML private TableColumn<Profesor, String> colTelefono;
    @FXML private TableColumn<Profesor, Double> colTarifaSesion;

    @FXML
    private void initialize() {
        cmbIdioma.setItems(
                FXCollections.observableArrayList(Idioma.values())
        );

        colDocumento.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getDocumentoDeIdentidad()));

        colNombre.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getNombre()));

        colIdioma.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getIdiomaQueEnsenia()));

        colTelefono.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getTelefono()));

        colTarifaSesion.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getTarifaPorSesion()));

        tablaProfesores.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        mostrarEnFormulario(seleccionado);
                    }
                });
        cmbIdioma.setItems(
                FXCollections.observableArrayList(Idioma.values()));
    }

    public void setProfesorController(ProfesorController profesorController) {
        this.profesorController = profesorController;
        actualizarTabla();
    }

    @FXML
    private void registrar() {
        if (!camposCompletos()) {
            return;
        }

        Double tarifa = leerTarifa();
        if (tarifa == null) {
            return;
        }

        boolean registrado = profesorController.registrarProfesor(
                txtNombre.getText().trim(),
                txtDocumento.getText().trim(),
                txtTelefono.getText().trim(),
                cmbIdioma.getValue(),
                tarifa
        );

        if (registrado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Profesor registrado correctamente.");
        } else {
            mostrarMensaje("Ya existe un profesor con ese documento.");
        }
    }

    @FXML
    private void actualizar() {
        Profesor seleccionado =
                tablaProfesores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un profesor de la tabla.");
            return;
        }

        if (!camposCompletos()) {
            return;
        }

        Double tarifa = leerTarifa();
        if (tarifa == null) {
            return;
        }

        Profesor actualizado = new Profesor.Builder()
                .nombre(txtNombre.getText().trim())
                .documentoDeIdentidad(
                        seleccionado.getDocumentoDeIdentidad())
                .telefono(txtTelefono.getText().trim())
                .idiomaQueEnsenia(cmbIdioma.getValue())
                .tarifaPorSesion(tarifa)
                .listaDeCursosProfesor(
                        seleccionado.getListaDeCursosProfesor())
                .listaEstudiantesProfesor(
                        seleccionado.getListaEstudiantesProfesor())
                .build();

        boolean exito = profesorController.actualizarDatosProfesor(
                seleccionado.getDocumentoDeIdentidad(),
                actualizado
        );

        if (exito) {
            tablaProfesores.getSelectionModel().clearSelection();
            actualizarTabla();
            tablaProfesores.refresh();
            limpiar();
            mostrarMensaje("Profesor actualizado correctamente.");
        } else {
            mostrarMensaje("No se encontró el profesor.");
        }
    }

    @FXML
    private void eliminar() {
        Profesor seleccionado =
                tablaProfesores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un profesor de la tabla.");
            return;
        }

        boolean eliminado = profesorController.eliminarProfesor(
                seleccionado.getDocumentoDeIdentidad()
        );

        if (eliminado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Profesor eliminado correctamente.");
        } else {
            mostrarMensaje("No se encontró el profesor.");
        }
    }

    @FXML
    private void buscar() {
        String documento = txtBuscarDocumento.getText().trim();

        if (documento.isEmpty()) {
            mostrarMensaje("Escribe un documento.");
            return;
        }

        Profesor encontrado =
                profesorController.buscarProfesor(documento);

        if (encontrado == null) {
            mostrarMensaje("No se encontró el profesor.");
            return;
        }

        tablaProfesores.setItems(
                FXCollections.observableArrayList(encontrado)
        );
        tablaProfesores.getSelectionModel().select(encontrado);
    }

    @FXML
    private void mostrarTodos() {
        txtBuscarDocumento.clear();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaProfesores.setItems(
                FXCollections.observableArrayList(
                        profesorController.listarProfesores()
                )
        );
    }

    private void mostrarEnFormulario(Profesor profesor) {
        txtDocumento.setText(profesor.getDocumentoDeIdentidad());
        txtDocumento.setEditable(false);

        txtNombre.setText(profesor.getNombre());
        cmbIdioma.setValue(profesor.getIdiomaQueEnsenia());
        txtTelefono.setText(profesor.getTelefono());
        txtTarifaSesion.setText(
                String.valueOf(profesor.getTarifaPorSesion())
        );
    }

    private boolean camposCompletos() {
        if (txtDocumento.getText().isBlank()
                || txtNombre.getText().isBlank()
                || cmbIdioma.getValue() == null
                || txtTelefono.getText().isBlank()
                || txtTarifaSesion.getText().isBlank()) {

            mostrarMensaje("Completa todos los campos.");
            return false;
        }

        return true;
    }

    private Double leerTarifa() {
        try {
            double tarifa = Double.parseDouble(
                    txtTarifaSesion.getText().trim()
            );

            if (!Double.isFinite(tarifa) || tarifa < 0) {
                mostrarMensaje("La tarifa debe ser un número válido.");
                return null;
            }

            return tarifa;

        } catch (NumberFormatException e) {
            mostrarMensaje("La tarifa debe ser un número. Ejemplo: 50000");
            return null;
        }
    }

    @FXML
    private void limpiar() {
        tablaProfesores.getSelectionModel().clearSelection();

        txtDocumento.clear();
        txtDocumento.setEditable(true);
        txtNombre.clear();
        cmbIdioma.setValue(null);
        txtTelefono.clear();
        txtTarifaSesion.clear();
        txtBuscarDocumento.clear();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
