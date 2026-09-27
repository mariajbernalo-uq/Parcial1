package ViewController;

import App.App;
import Controller.CursoController;
import Model.Curso;
import Model.CursoIntensivo;
import Model.CursoPersonalizado;
import Model.CursoRegular;
import Model.Estado;
import Model.Nivel;
import Model.ServicioAdicional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class CursoViewController {

    private App app;
    private CursoController cursoController;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbTipoCurso;
    @FXML private ComboBox<String> cmbIdioma;
    @FXML private TextField txtDuracionMeses;
    @FXML private TextField txtValorMensual;
    @FXML private ComboBox<Estado> cmbEstado;
    @FXML private TextArea txtDescripcion;

    @FXML private VBox panelPersonalizado;
    @FXML private TextField txtCantidadSesiones;
    @FXML private ComboBox<Nivel> cmbNivelReferencia;
    @FXML private TextArea txtObjetivoEstudiante;

    @FXML private TextField txtCodigoBeneficio;
    @FXML private TextField txtNombreBeneficio;
    @FXML private TextArea txtDescripcionBeneficio;

    @FXML private TableView<Curso> tablaCursos;
    @FXML private TableColumn<Curso, String> colCodigo;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, String> colTipo;
    @FXML private TableColumn<Curso, String> colIdioma;
    @FXML private TableColumn<Curso, Integer> colDuracion;
    @FXML private TableColumn<Curso, Double> colValorMensual;
    @FXML private TableColumn<Curso, Estado> colEstado;
    @FXML private TableColumn<Curso, Integer> colSesiones;
    @FXML private TableColumn<Curso, Nivel> colNivel;
    @FXML private TableColumn<Curso, String> colObjetivo;
    @FXML private TableColumn<Curso, String> colBeneficios;


    // ==========================================
    // CONECTAR CON APP
    // ==========================================

    public void setApp(App app) {

        this.app = app;

        this.cursoController =
                new CursoController(
                        app.getAcademia()
                );

        configurarCombos();
        configurarTabla();

        panelPersonalizado.setVisible(false);
        panelPersonalizado.setManaged(false);

        mostrarTodos();
    }


    // ==========================================
    // CONFIGURAR COMBOS
    // ==========================================

    private void configurarCombos() {

        cmbTipoCurso.setItems(
                FXCollections.observableArrayList(
                        "Regular",
                        "Intensivo",
                        "Personalizado"
                )
        );

        cmbIdioma.setItems(
                FXCollections.observableArrayList(
                        "INGLES",
                        "FRANCES",
                        "PORTUGUES"
                )
        );

        cmbEstado.setItems(
                FXCollections.observableArrayList(
                        Estado.values()
                )
        );

        cmbNivelReferencia.setItems(
                FXCollections.observableArrayList(
                        Nivel.values()
                )
        );
    }


    // ==========================================
    // CONFIGURAR TABLA
    // ==========================================

    private void configurarTabla() {

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colIdioma.setCellValueFactory(
                new PropertyValueFactory<>("idioma")
        );

        colDuracion.setCellValueFactory(
                new PropertyValueFactory<>("duracionMeses")
        );

        colValorMensual.setCellValueFactory(
                new PropertyValueFactory<>("valorMensual")
        );

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );


        // Tipo de curso

        colTipo.setCellValueFactory(datos -> {

            Curso curso = datos.getValue();

            String tipo = "";

            if (curso instanceof CursoRegular) {
                tipo = "Regular";

            } else if (curso instanceof CursoIntensivo) {
                tipo = "Intensivo";

            } else if (curso instanceof CursoPersonalizado) {
                tipo = "Personalizado";
            }

            return new SimpleStringProperty(tipo);
        });


        // Cantidad de sesiones

        colSesiones.setCellValueFactory(datos -> {

            Curso curso = datos.getValue();

            if (curso instanceof CursoPersonalizado personalizado) {

                return new SimpleObjectProperty<>(
                        personalizado.getCantidadSesiones()
                );
            }

            return new SimpleObjectProperty<>(null);
        });


        // Nivel

        colNivel.setCellValueFactory(datos -> {

            Curso curso = datos.getValue();

            if (curso instanceof CursoPersonalizado personalizado) {

                return new SimpleObjectProperty<>(
                        personalizado.getNivelReferencia()
                );
            }

            return new SimpleObjectProperty<>(null);
        });


        // Objetivos

        colObjetivo.setCellValueFactory(datos -> {

            Curso curso = datos.getValue();

            if (curso instanceof CursoPersonalizado personalizado) {

                return new SimpleStringProperty(
                        personalizado.getObjetivosEstudiante()
                );
            }

            return new SimpleStringProperty("");
        });


        // Beneficios

        colBeneficios.setCellValueFactory(datos -> {

            Curso curso = datos.getValue();

            if (curso.getListaBeneficios() == null
                    || curso.getListaBeneficios().isEmpty()) {

                return new SimpleStringProperty("");
            }

            String beneficios =
                    curso.getListaBeneficios()
                            .stream()
                            .map(
                                    ServicioAdicional::getNombre
                            )
                            .reduce(
                                    (a, b) -> a + ", " + b
                            )
                            .orElse("");

            return new SimpleStringProperty(
                    beneficios
            );
        });
    }


    // ==========================================
    // REGISTRAR
    // ==========================================

    @FXML
    private void registrar() {

        try {

            String codigo =
                    txtCodigo.getText().trim();

            String nombre =
                    txtNombre.getText().trim();

            String idioma =
                    cmbIdioma.getValue();

            String tipo =
                    cmbTipoCurso.getValue();

            String descripcion =
                    txtDescripcion.getText().trim();

            Estado estado =
                    cmbEstado.getValue();


            if (codigo.isBlank()
                    || nombre.isBlank()
                    || idioma == null
                    || tipo == null
                    || estado == null) {

                mostrarMensaje(
                        "Error",
                        "Complete los campos obligatorios."
                );

                return;
            }


            int duracion =
                    Integer.parseInt(
                            txtDuracionMeses.getText()
                    );

            double valor =
                    Double.parseDouble(
                            txtValorMensual.getText()
                    );


            if (duracion <= 0) {

                mostrarMensaje(
                        "Error",
                        "La duración debe ser mayor que cero."
                );

                return;
            }


            if (valor < 0) {

                mostrarMensaje(
                        "Error",
                        "El valor mensual no puede ser negativo."
                );

                return;
            }


            if (tipo.equals("Regular")) {

                cursoController.registrarCursoRegular(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracion,
                        valor,
                        estado
                );

            } else if (tipo.equals("Intensivo")) {

                cursoController.registrarCursoIntensivo(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracion,
                        valor,
                        estado
                );

            } else {

                registrarPersonalizado(
                        codigo,
                        nombre,
                        idioma,
                        descripcion,
                        duracion,
                        valor,
                        estado
                );

                return;
            }


            mostrarTodos();
            limpiar();

            mostrarMensaje(
                    "Éxito",
                    "El curso fue registrado correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "La duración y el valor deben ser números."
            );
        }
    }


    // ==========================================
    // REGISTRAR PERSONALIZADO
    // ==========================================

    private void registrarPersonalizado(
            String codigo,
            String nombre,
            String idioma,
            String descripcion,
            int duracion,
            double valor,
            Estado estado) {

        try {

            int sesiones =
                    Integer.parseInt(
                            txtCantidadSesiones.getText()
                    );

            Nivel nivel =
                    cmbNivelReferencia.getValue();

            String objetivo =
                    txtObjetivoEstudiante
                            .getText()
                            .trim();


            if (sesiones <= 0) {

                mostrarMensaje(
                        "Error",
                        "La cantidad de sesiones debe ser mayor que cero."
                );

                return;
            }


            if (nivel == null) {

                mostrarMensaje(
                        "Error",
                        "Seleccione el nivel."
                );

                return;
            }


            if (objetivo.isBlank()) {

                mostrarMensaje(
                        "Error",
                        "Ingrese los objetivos del estudiante."
                );

                return;
            }


            cursoController.registrarCursoPersonalizado(
                    codigo,
                    nombre,
                    idioma,
                    descripcion,
                    duracion,
                    valor,
                    estado,
                    sesiones,
                    nivel,
                    objetivo,
                    null
            );


            mostrarTodos();
            limpiar();

            mostrarMensaje(
                    "Éxito",
                    "El curso personalizado fue registrado."
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "La cantidad de sesiones debe ser un número."
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
                    txtCodigo.getText().trim();

            if (codigoOriginal.isBlank()) {

                mostrarMensaje(
                        "Error",
                        "Ingrese el código del curso."
                );

                return;
            }


            Curso curso =
                    cursoController.buscarCurso(
                            codigoOriginal
                    );


            if (curso == null) {

                mostrarMensaje(
                        "Error",
                        "No se encontró el curso."
                );

                return;
            }


            String codigo =
                    txtCodigo.getText().trim();

            String nombre =
                    txtNombre.getText().trim();

            String idioma =
                    cmbIdioma.getValue();

            String descripcion =
                    txtDescripcion.getText().trim();

            Estado estado =
                    cmbEstado.getValue();

            String tipo =
                    cmbTipoCurso.getValue();


            int duracion =
                    Integer.parseInt(
                            txtDuracionMeses.getText()
                    );

            double valor =
                    Double.parseDouble(
                            txtValorMensual.getText()
                    );


            boolean actualizado;


            if (tipo.equals("Personalizado")) {

                int sesiones =
                        Integer.parseInt(
                                txtCantidadSesiones.getText()
                        );

                Nivel nivel =
                        cmbNivelReferencia.getValue();

                String objetivo =
                        txtObjetivoEstudiante
                                .getText()
                                .trim();


                actualizado =
                        cursoController
                                .actualizarCursoPersonalizado(
                                        codigoOriginal,
                                        codigo,
                                        nombre,
                                        idioma,
                                        descripcion,
                                        duracion,
                                        valor,
                                        estado,
                                        sesiones,
                                        nivel,
                                        objetivo
                                );

            } else {

                actualizado =
                        cursoController.actualizarCurso(
                                codigoOriginal,
                                codigo,
                                nombre,
                                idioma,
                                descripcion,
                                duracion,
                                valor,
                                estado
                        );
            }


            if (actualizado) {

                mostrarTodos();

                mostrarMensaje(
                        "Éxito",
                        "El curso fue actualizado correctamente."
                );

            } else {

                mostrarMensaje(
                        "Error",
                        "No fue posible actualizar el curso."
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
                txtCodigo.getText().trim();


        if (codigo.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese el código del curso."
            );

            return;
        }


        boolean eliminado =
                cursoController.eliminarCurso(
                        codigo
                );


        if (eliminado) {

            mostrarTodos();
            limpiar();

            mostrarMensaje(
                    "Éxito",
                    "El curso fue eliminado."
            );

        } else {

            mostrarMensaje(
                    "Error",
                    "No se encontró el curso."
            );
        }
    }


    // ==========================================
    // BUSCAR
    // ==========================================

    @FXML
    private void buscar() {

        String codigo =
                txtCodigo.getText().trim();


        if (codigo.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese el código del curso."
            );

            return;
        }


        Curso curso =
                cursoController.buscarCurso(
                        codigo
                );


        if (curso == null) {

            mostrarMensaje(
                    "Resultado",
                    "No se encontró el curso."
            );

            return;
        }


        cargarDatos(curso);
    }


    // ==========================================
    // CARGAR DATOS
    // ==========================================

    private void cargarDatos(Curso curso) {

        txtCodigo.setText(
                curso.getCodigo()
        );

        txtNombre.setText(
                curso.getNombre()
        );

        cmbIdioma.setValue(
                curso.getIdioma()
        );

        txtDescripcion.setText(
                curso.getDescripcion()
        );

        txtDuracionMeses.setText(
                String.valueOf(
                        curso.getDuracionMeses()
                )
        );

        txtValorMensual.setText(
                String.valueOf(
                        curso.getValorMensual()
                )
        );

        cmbEstado.setValue(
                curso.getEstado()
        );


        if (curso instanceof CursoRegular) {

            cmbTipoCurso.setValue(
                    "Regular"
            );

        } else if (curso instanceof CursoIntensivo) {

            cmbTipoCurso.setValue(
                    "Intensivo"
            );

        } else if (curso instanceof CursoPersonalizado personalizado) {

            cmbTipoCurso.setValue(
                    "Personalizado"
            );

            txtCantidadSesiones.setText(
                    String.valueOf(
                            personalizado
                                    .getCantidadSesiones()
                    )
            );

            cmbNivelReferencia.setValue(
                    personalizado
                            .getNivelReferencia()
            );

            txtObjetivoEstudiante.setText(
                    personalizado
                            .getObjetivosEstudiante()
            );
        }


        actualizarPanelPersonalizado();
    }


    // ==========================================
    // MOSTRAR TODOS
    // ==========================================

    @FXML
    private void mostrarTodos() {

        ObservableList<Curso> cursos =
                FXCollections.observableArrayList(
                        cursoController.listarCursos()
                );

        tablaCursos.setItems(cursos);
    }


    // ==========================================
    // PANEL PERSONALIZADO
    // ==========================================

    @FXML
    private void actualizarPanelPersonalizado() {

        boolean personalizado =
                "Personalizado".equals(
                        cmbTipoCurso.getValue()
                );


        panelPersonalizado.setVisible(
                personalizado
        );

        panelPersonalizado.setManaged(
                personalizado
        );
    }


    // ==========================================
    // CREAR BENEFICIO
    // ==========================================

    @FXML
    private void crearBeneficio() {

        String codigoCurso =
                txtCodigo.getText().trim();


        Curso curso =
                cursoController.buscarCurso(
                        codigoCurso
                );


        if (curso == null) {

            mostrarMensaje(
                    "Error",
                    "Primero debe seleccionar un curso."
            );

            return;
        }


        String codigo =
                txtCodigoBeneficio.getText().trim();

        String nombre =
                txtNombreBeneficio.getText().trim();

        String descripcion =
                txtDescripcionBeneficio
                        .getText()
                        .trim();


        if (codigo.isBlank()
                || nombre.isBlank()) {

            mostrarMensaje(
                    "Error",
                    "Ingrese código y nombre del beneficio."
            );

            return;
        }


        ServicioAdicional beneficio =
                new ServicioAdicional.Builder()
                        .codigo(codigo)
                        .nombre(nombre)
                        .descripcion(descripcion)
                        .precio(0)
                        .disponible(true)
                        .build();


        cursoController.agregarBeneficio(
                codigoCurso,
                beneficio
        );


        mostrarTodos();


        txtCodigoBeneficio.clear();
        txtNombreBeneficio.clear();
        txtDescripcionBeneficio.clear();


        mostrarMensaje(
                "Éxito",
                "Beneficio agregado correctamente."
        );
    }


    // ==========================================
    // LIMPIAR
    // ==========================================

    @FXML
    private void limpiar() {

        txtCodigo.clear();
        txtNombre.clear();
        txtDuracionMeses.clear();
        txtValorMensual.clear();
        txtDescripcion.clear();

        txtCantidadSesiones.clear();
        txtObjetivoEstudiante.clear();

        txtCodigoBeneficio.clear();
        txtNombreBeneficio.clear();
        txtDescripcionBeneficio.clear();


        cmbTipoCurso.getSelectionModel()
                .clearSelection();

        cmbIdioma.getSelectionModel()
                .clearSelection();

        cmbEstado.getSelectionModel()
                .clearSelection();

        cmbNivelReferencia.getSelectionModel()
                .clearSelection();


        tablaCursos.getSelectionModel()
                .clearSelection();


        actualizarPanelPersonalizado();
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