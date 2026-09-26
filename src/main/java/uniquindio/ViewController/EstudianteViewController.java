package uniquindio.ViewController;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import uniquindio.Controller.EstudianteController;
import uniquindio.Model.Estudiante;

import java.time.LocalDate;

public class EstudianteViewController {

    private EstudianteController estudianteController;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;
    @FXML private TextField fechaRegistro;
    @FXML private TextField txtBuscarDocumento;

    @FXML private TableView<Estudiante> tablaEstudiantes;

    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colDocumento;
    @FXML private TableColumn<Estudiante, String> colTelefono;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, Integer> colEdad;
    @FXML private TableColumn<Estudiante, LocalDate> colFechaRegistro;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getNombre()));

        colDocumento.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getDocumentoDeIdentidad()));

        colTelefono.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getTelefono()));

        colCorreo.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getCorreo()));

        colEdad.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getEdad()));

        colFechaRegistro.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getFechaDeRegistro()));

        tablaEstudiantes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        mostrarEnFormulario(seleccionado);
                    }
                });

        fechaRegistro.setText(LocalDate.now().toString());
    }

    public void setEstudianteController(
            EstudianteController estudianteController
    ) {
        this.estudianteController = estudianteController;
        actualizarTabla();
    }

    @FXML
    private void registrar() {
        if (!camposCompletos()) {
            return;
        }

        Integer edad = leerEdad();
        if (edad == null) {
            return;
        }

        boolean registrado =
                estudianteController.registrarEstudiante(
                        txtNombre.getText().trim(),
                        txtDocumento.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtCorreo.getText().trim(),
                        edad,
                        LocalDate.now()
                );

        if (registrado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Estudiante registrado correctamente.");
        } else {
            mostrarMensaje("Ya existe un estudiante con ese documento.");
        }
    }

    @FXML
    private void actualizar() {
        Estudiante seleccionado =
                tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un estudiante de la tabla.");
            return;
        }

        if (!camposCompletos()) {
            return;
        }

        Integer edad = leerEdad();
        if (edad == null) {
            return;
        }

        // Se conserva el documento y la fecha originales.
        Estudiante actualizado = new Estudiante.Builder()
                .nombre(txtNombre.getText().trim())
                .documentoDeIdentidad(
                        seleccionado.getDocumentoDeIdentidad())
                .telefono(txtTelefono.getText().trim())
                .correo(txtCorreo.getText().trim())
                .edad(edad)
                .fechaDeRegistro(
                        seleccionado.getFechaDeRegistro())
                .build();

        boolean exito =
                estudianteController.actualizarDatosEstudiante(
                        seleccionado.getDocumentoDeIdentidad(),
                        actualizado
                );

        if (exito) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Estudiante actualizado correctamente.");
        } else {
            mostrarMensaje("No se encontró el estudiante.");
        }
    }

    @FXML
    private void eliminar() {
        Estudiante seleccionado =
                tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un estudiante de la tabla.");
            return;
        }

        boolean eliminado =
                estudianteController.eliminarEstudiante(
                        seleccionado.getDocumentoDeIdentidad()
                );

        if (eliminado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Estudiante eliminado correctamente.");
        } else {
            mostrarMensaje("No se encontró el estudiante.");
        }
    }

    private void actualizarTabla() {
        tablaEstudiantes.setItems(
                FXCollections.observableArrayList(
                        estudianteController.listarEstudiantes()
                )
        );
    }
    @FXML
    private void buscar() {
        String documento = txtBuscarDocumento.getText().trim();

        if (documento.isEmpty()) {
            mostrarMensaje("Escribe un documento.");
            return;
        }

        Estudiante encontrado =
                estudianteController.buscarEstudiante(documento);

        if (encontrado == null) {
            mostrarMensaje("No se encontró el estudiante.");
            return;
        }

        tablaEstudiantes.setItems(
                FXCollections.observableArrayList(encontrado)
        );
        tablaEstudiantes.getSelectionModel().select(encontrado);
    }

    @FXML
    private void mostrarTodos() {
        txtBuscarDocumento.clear();
        actualizarTabla();
    }
    private void mostrarEnFormulario(Estudiante estudiante) {
        txtNombre.setText(estudiante.getNombre());

        txtDocumento.setText(
                estudiante.getDocumentoDeIdentidad());
        txtDocumento.setEditable(false);

        txtTelefono.setText(estudiante.getTelefono());
        txtCorreo.setText(estudiante.getCorreo());
        txtEdad.setText(String.valueOf(estudiante.getEdad()));

        fechaRegistro.setText(
                estudiante.getFechaDeRegistro().toString());
    }

    private boolean camposCompletos() {
        if (txtNombre.getText().isBlank()
                || txtDocumento.getText().isBlank()
                || txtTelefono.getText().isBlank()
                || txtCorreo.getText().isBlank()
                || txtEdad.getText().isBlank()) {

            mostrarMensaje("Completa todos los campos.");
            return false;
        }

        return true;
    }

    private Integer leerEdad() {
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());

            if (edad < 0) {
                mostrarMensaje("La edad no puede ser negativa.");
                return null;
            }

            return edad;

        } catch (NumberFormatException e) {
            mostrarMensaje("La edad debe ser un número entero.");
            return null;
        }
    }

    @FXML
    private void limpiar() {
        tablaEstudiantes.getSelectionModel().clearSelection();

        txtNombre.clear();
        txtDocumento.clear();
        txtDocumento.setEditable(true);
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();

        fechaRegistro.setText(LocalDate.now().toString());
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
