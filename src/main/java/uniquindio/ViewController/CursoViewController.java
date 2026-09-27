package uniquindio.ViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uniquindio.Controller.CursoController;
import uniquindio.Model.Beneficio;
import uniquindio.Model.Curso;
import uniquindio.Model.CursoIntensivo;
import uniquindio.Model.CursoPersonalizado;
import uniquindio.Model.Estado;
import uniquindio.Model.Idioma;
import uniquindio.Model.Nivel;

public class CursoViewController {

    private CursoController cursoController;

    private final List<Beneficio> beneficiosSeleccionados =
            new ArrayList<>();

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracionMeses;
    @FXML private TextField txtValorMensual;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtCantidadSesiones;
    @FXML private TextField txtObjetivoEstudiante;
    @FXML private TextField txtBuscarCodigo;

    @FXML private TextField txtCodigoBeneficio;
    @FXML private TextField txtNombreBeneficio;
    @FXML private TextField txtDescripcionBeneficio;

    @FXML private ComboBox<String> cmbTipoCurso;
    @FXML private ComboBox<Idioma> cmbIdioma;
    @FXML private ComboBox<Estado> cmbEstado;
    @FXML private ComboBox<Nivel> cmbNivelReferencia;

    @FXML private MenuButton menuBeneficios;
    @FXML private VBox panelPersonalizado;
    @FXML private TableView<Curso> tablaCursos;

    @FXML private TableColumn<Curso, String> colCodigo;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, String> colTipo;
    @FXML private TableColumn<Curso, Idioma> colIdioma;
    @FXML private TableColumn<Curso, Integer> colDuracion;
    @FXML private TableColumn<Curso, Double> colValorMensual;
    @FXML private TableColumn<Curso, Estado> colEstado;
    @FXML private TableColumn<Curso, Integer> colSesiones;
    @FXML private TableColumn<Curso, Nivel> colNivel;
    @FXML private TableColumn<Curso, String> colObjetivo;
    @FXML private TableColumn<Curso, String> colBeneficios;

    @FXML
    private void initialize() {
        cmbTipoCurso.setItems(FXCollections.observableArrayList(
                "Regular", "Intensivo", "Personalizado"
        ));
        cmbIdioma.setItems(
                FXCollections.observableArrayList(Idioma.values())
        );
        cmbEstado.setItems(
                FXCollections.observableArrayList(Estado.values())
        );
        cmbNivelReferencia.setItems(
                FXCollections.observableArrayList(Nivel.values())
        );

        cmbTipoCurso.valueProperty().addListener(
                (observable, anterior, tipo) -> {
                    boolean personalizado =
                            "Personalizado".equals(tipo);
                    panelPersonalizado.setVisible(personalizado);
                    panelPersonalizado.setManaged(personalizado);
                }
        );

        colCodigo.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getCodigo()
                )
        );
        colNombre.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue().getNombre()
                )
        );
        colTipo.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        obtenerTipo(dato.getValue())
                )
        );
        colIdioma.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getIdioma()
                )
        );
        colDuracion.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getDuracionMeses()
                )
        );
        colValorMensual.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getValorMensual()
                )
        );
        colEstado.setCellValueFactory(dato ->
                new ReadOnlyObjectWrapper<>(
                        dato.getValue().getEstado()
                )
        );

        colSesiones.setCellValueFactory(dato -> {
            if (dato.getValue()
                    instanceof CursoPersonalizado personalizado) {
                return new ReadOnlyObjectWrapper<>(
                        personalizado.getCantidadSesiones()
                );
            }
            return new ReadOnlyObjectWrapper<>(null);
        });

        colNivel.setCellValueFactory(dato -> {
            if (dato.getValue()
                    instanceof CursoPersonalizado personalizado) {
                return new ReadOnlyObjectWrapper<>(
                        personalizado.getNivelReferencia()
                );
            }
            return new ReadOnlyObjectWrapper<>(null);
        });

        colObjetivo.setCellValueFactory(dato -> {
            if (dato.getValue()
                    instanceof CursoPersonalizado personalizado) {
                return new ReadOnlyStringWrapper(
                        personalizado.getObjetivoEstudiante()
                );
            }
            return new ReadOnlyStringWrapper("");
        });

        colBeneficios.setCellValueFactory(dato ->
                new ReadOnlyStringWrapper(
                        dato.getValue()
                                .getListaBeneficios()
                                .stream()
                                .map(Beneficio::getNombre)
                                .collect(Collectors.joining(", "))
                )
        );

        tablaCursos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        mostrarEnFormulario(seleccionado);
                    }
                });

        cmbEstado.setValue(Estado.ACTIVO);
        cmbEstado.setDisable(true);
    }

    public void setCursoController(CursoController cursoController) {
        this.cursoController = cursoController;

        inicializarBeneficiosComunes();
        cargarOpcionesBeneficios();
        actualizarTabla();
    }

    private void inicializarBeneficiosComunes() {
        registrarBeneficioInicial(
                "BEN-001",
                "Acceso a la plataforma virtual",
                "Acceso a contenidos y actividades en línea"
        );
        registrarBeneficioInicial(
                "BEN-002",
                "Material didáctico",
                "Material de apoyo para las clases"
        );
        registrarBeneficioInicial(
                "BEN-003",
                "Clubes de conversación",
                "Espacios para practicar el idioma"
        );
    }

    private void registrarBeneficioInicial(
            String codigo,
            String nombre,
            String descripcion
    ) {
        if (cursoController.buscarBeneficio(codigo) == null) {
            cursoController.crearBeneficio(
                    codigo, nombre, descripcion
            );
        }
    }

    private void cargarOpcionesBeneficios() {
        menuBeneficios.getItems().clear();

        for (Beneficio beneficio : cursoController.listarBeneficios()) {
            CheckMenuItem opcion =
                    new CheckMenuItem(beneficio.getNombre());

            opcion.setSelected(
                    beneficiosSeleccionados.stream().anyMatch(
                            elegido -> elegido.getCodigo().equals(
                                    beneficio.getCodigo()
                            )
                    )
            );

            opcion.selectedProperty().addListener(
                    (observable, anterior, marcado) -> {
                        if (marcado) {
                            boolean yaSeleccionado =
                                    beneficiosSeleccionados.stream()
                                            .anyMatch(elegido ->
                                                    elegido.getCodigo()
                                                            .equals(
                                                                    beneficio.getCodigo()
                                                            )
                                            );

                            if (!yaSeleccionado) {
                                beneficiosSeleccionados.add(
                                        beneficio
                                );
                            }
                        } else {
                            beneficiosSeleccionados.removeIf(
                                    elegido -> elegido.getCodigo()
                                            .equals(
                                                    beneficio.getCodigo()
                                            )
                            );
                        }

                        actualizarTextoBeneficios();
                    }
            );

            menuBeneficios.getItems().add(opcion);
        }

        actualizarTextoBeneficios();
    }

    private void actualizarTextoBeneficios() {
        menuBeneficios.setText(
                beneficiosSeleccionados.isEmpty()
                        ? "Seleccionar beneficios"
                        : "Beneficios: "
                        + beneficiosSeleccionados.size()
        );
    }

    @FXML
    private void crearBeneficio() {
        String codigo = txtCodigoBeneficio.getText().trim();
        String nombre = txtNombreBeneficio.getText().trim();
        String descripcion =
                txtDescripcionBeneficio.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            mostrarMensaje(
                    "Escribe el código y el nombre del beneficio."
            );
            return;
        }

        boolean creado = cursoController.crearBeneficio(
                codigo, nombre, descripcion
        );

        if (!creado) {
            mostrarMensaje(
                    "No se pudo crear: revisa si el código ya existe."
            );
            return;
        }

        // El beneficio nuevo queda seleccionado para este curso.
        Beneficio nuevo =
                cursoController.buscarBeneficio(codigo);

        beneficiosSeleccionados.add(nuevo);
        cargarOpcionesBeneficios();

        txtCodigoBeneficio.clear();
        txtNombreBeneficio.clear();
        txtDescripcionBeneficio.clear();

        mostrarMensaje("Beneficio creado correctamente.");
    }

    @FXML
    private void registrar() {
        if (!validarFormulario()) {
            return;
        }

        Integer duracion = leerEntero(
                txtDuracionMeses, "La duración"
        );
        Double valor = leerValorMensual();

        if (duracion == null || valor == null) {
            return;
        }

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        Idioma idioma = cmbIdioma.getValue();
        String descripcion = txtDescripcion.getText().trim();

        List<Beneficio> beneficios =
                List.copyOf(beneficiosSeleccionados);

        boolean registrado;

        switch (cmbTipoCurso.getValue()) {
            case "Regular" -> registrado =
                    cursoController.registrarCursoRegular(
                            codigo, nombre, idioma, descripcion,
                            duracion, valor, beneficios
                    );

            case "Intensivo" -> registrado =
                    cursoController.registrarCursoIntensivo(
                            codigo, nombre, idioma, descripcion,
                            duracion, valor, beneficios
                    );

            case "Personalizado" -> {
                Integer sesiones = leerEntero(
                        txtCantidadSesiones,
                        "La cantidad de sesiones"
                );

                if (sesiones == null) {
                    return;
                }

                registrado =
                        cursoController.registrarCursoPersonalizado(
                                codigo,
                                nombre,
                                idioma,
                                descripcion,
                                duracion,
                                valor,
                                sesiones,
                                cmbNivelReferencia.getValue(),
                                txtObjetivoEstudiante.getText().trim(),
                                beneficios
                        );
            }

            default -> {
                mostrarMensaje("Selecciona un tipo de curso.");
                return;
            }
        }

        if (registrado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Curso registrado correctamente.");
        } else {
            mostrarMensaje("Ya existe un curso con ese código.");
        }
    }

    @FXML
    private void actualizar() {
        Curso seleccionado =
                tablaCursos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un curso de la tabla.");
            return;
        }

        if (!validarFormulario()) {
            return;
        }

        Integer duracion = leerEntero(
                txtDuracionMeses, "La duración"
        );
        Double valor = leerValorMensual();

        if (duracion == null || valor == null) {
            return;
        }

        Integer sesiones = null;

        if (seleccionado instanceof CursoPersonalizado) {
            sesiones = leerEntero(
                    txtCantidadSesiones,
                    "La cantidad de sesiones"
            );

            if (sesiones == null) {
                return;
            }
        }

        String codigo = seleccionado.getCodigo();

        boolean exito = cursoController.actualizarCurso(
                codigo,
                txtNombre.getText().trim(),
                cmbIdioma.getValue(),
                txtDescripcion.getText().trim(),
                duracion,
                valor,
                cmbEstado.getValue()
        );

        if (exito && seleccionado instanceof CursoPersonalizado) {
            exito = cursoController.actualizarDatosCursoPersonalizado(
                    codigo,
                    sesiones,
                    cmbNivelReferencia.getValue(),
                    txtObjetivoEstudiante.getText().trim()
            );
        }

        if (exito) {
            exito = cursoController.actualizarBeneficiosCurso(
                    codigo,
                    List.copyOf(beneficiosSeleccionados)
            );
        }

        if (exito) {
            tablaCursos.getSelectionModel().clearSelection();
            actualizarTabla();
            limpiar();
            mostrarMensaje("Curso actualizado correctamente.");
        } else {
            mostrarMensaje("No se pudo actualizar el curso.");
        }
    }

    @FXML
    private void eliminar() {
        Curso seleccionado =
                tablaCursos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un curso de la tabla.");
            return;
        }

        boolean eliminado =
                cursoController.eliminarCurso(
                        seleccionado.getCodigo()
                );

        if (eliminado) {
            actualizarTabla();
            limpiar();
            mostrarMensaje("Curso eliminado correctamente.");
        } else {
            mostrarMensaje("No se encontró el curso.");
        }
    }

    @FXML
    private void buscar() {
        String codigo = txtBuscarCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mostrarMensaje("Escribe el código del curso.");
            return;
        }

        Curso encontrado =
                cursoController.buscarCurso(codigo);

        if (encontrado == null) {
            mostrarMensaje("No se encontró el curso.");
            return;
        }

        tablaCursos.setItems(
                FXCollections.observableArrayList(encontrado)
        );
        tablaCursos.getSelectionModel().select(encontrado);
    }

    @FXML
    private void mostrarTodos() {
        txtBuscarCodigo.clear();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaCursos.setItems(
                FXCollections.observableArrayList(
                        cursoController.listarCursos()
                )
        );
        tablaCursos.refresh();
    }

    private void mostrarEnFormulario(Curso curso) {
        txtCodigo.setText(curso.getCodigo());
        txtCodigo.setEditable(false);

        txtNombre.setText(curso.getNombre());

        cmbTipoCurso.setValue(obtenerTipo(curso));
        cmbTipoCurso.setDisable(true);

        cmbIdioma.setValue(curso.getIdioma());
        txtDescripcion.setText(curso.getDescripcion());
        txtDuracionMeses.setText(
                String.valueOf(curso.getDuracionMeses())
        );
        txtValorMensual.setText(
                String.valueOf(curso.getValorMensual())
        );

        cmbEstado.setValue(curso.getEstado());
        cmbEstado.setDisable(false);

        if (curso instanceof CursoPersonalizado personalizado) {
            txtCantidadSesiones.setText(
                    String.valueOf(
                            personalizado.getCantidadSesiones()
                    )
            );
            cmbNivelReferencia.setValue(
                    personalizado.getNivelReferencia()
            );
            txtObjetivoEstudiante.setText(
                    personalizado.getObjetivoEstudiante()
            );
        } else {
            limpiarCamposPersonalizados();
        }

        beneficiosSeleccionados.clear();
        beneficiosSeleccionados.addAll(
                curso.getListaBeneficios()
        );
        cargarOpcionesBeneficios();
    }

    private String obtenerTipo(Curso curso) {
        if (curso instanceof CursoPersonalizado) {
            return "Personalizado";
        }
        if (curso instanceof CursoIntensivo) {
            return "Intensivo";
        }
        return "Regular";
    }

    private boolean validarFormulario() {
        if (txtCodigo.getText().isBlank()
                || txtNombre.getText().isBlank()
                || txtDuracionMeses.getText().isBlank()
                || txtValorMensual.getText().isBlank()
                || cmbTipoCurso.getValue() == null
                || cmbIdioma.getValue() == null
                || cmbEstado.getValue() == null) {

            mostrarMensaje("Completa los datos del curso.");
            return false;
        }

        if ("Personalizado".equals(cmbTipoCurso.getValue())
                && (txtCantidadSesiones.getText().isBlank()
                || cmbNivelReferencia.getValue() == null
                || txtObjetivoEstudiante.getText().isBlank())) {

            mostrarMensaje(
                    "Completa los datos del curso personalizado."
            );
            return false;
        }

        return true;
    }

    private Integer leerEntero(
            TextField campo,
            String nombreCampo
    ) {
        try {
            int valor = Integer.parseInt(
                    campo.getText().trim()
            );

            if (valor <= 0) {
                mostrarMensaje(
                        nombreCampo + " debe ser mayor que cero."
                );
                return null;
            }

            return valor;
        } catch (NumberFormatException e) {
            mostrarMensaje(
                    nombreCampo + " debe ser un número entero."
            );
            return null;
        }
    }

    private Double leerValorMensual() {
        try {
            double valor = Double.parseDouble(
                    txtValorMensual.getText().trim()
            );

            if (!Double.isFinite(valor) || valor < 0) {
                mostrarMensaje(
                        "El valor mensual no puede ser negativo."
                );
                return null;
            }

            return valor;
        } catch (NumberFormatException e) {
            mostrarMensaje(
                    "El valor mensual debe ser un número válido."
            );
            return null;
        }
    }

    @FXML
    private void limpiar() {
        tablaCursos.getSelectionModel().clearSelection();

        txtCodigo.clear();
        txtCodigo.setEditable(true);
        txtNombre.clear();
        txtDuracionMeses.clear();
        txtValorMensual.clear();
        txtDescripcion.clear();
        txtBuscarCodigo.clear();

        cmbTipoCurso.setValue(null);
        cmbTipoCurso.setDisable(false);
        cmbIdioma.setValue(null);

        cmbEstado.setValue(Estado.ACTIVO);
        cmbEstado.setDisable(true);

        limpiarCamposPersonalizados();

        beneficiosSeleccionados.clear();
        cargarOpcionesBeneficios();
    }

    private void limpiarCamposPersonalizados() {
        txtCantidadSesiones.clear();
        cmbNivelReferencia.setValue(null);
        txtObjetivoEstudiante.clear();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}