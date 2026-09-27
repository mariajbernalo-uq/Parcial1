package uniquindio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.Model.*;
import uniquindio.ViewController.AcademiaViewController;
import uniquindio.ViewController.BienvenidaViewController;

import java.time.LocalDate;
import java.util.List;


public class App extends Application {
    //Creación de la Academia
    private final Academia academia = Academia.getInstancia(
            "Academia LenguajeCafetero",
            "123456789",
            "3000000000",
            "Calle 10",
            "academiaLenguajeCafetero@gmail.com",
            "academialenguajecafetero.com.co"
    );

    public Academia getAcademia() {
        return academia;
    }
//Atributos de Vista JAVAFX
private Stage primaryStage;

@Override
public void start(Stage stage){
    this.primaryStage= stage;
    cargarDatosPrueba();
    abrirVistaBienvenida();
}


//-----JAVA FX VISTAS---------------
    //-Bienvenida
public void abrirVistaBienvenida(){
try{
    FXMLLoader loader=
            new FXMLLoader(App.class.getResource("Bienvenida.fxml"));
    Scene scene= new Scene(loader.load());

    BienvenidaViewController controller= loader.getController();
    controller.setApp(this);

    primaryStage.setScene(scene);
    primaryStage.show();
} catch (Exception e){
    e.printStackTrace();
}
}
//-Academia
public void abrirVistaAcademia(){
        try{
            FXMLLoader loader=
                    new FXMLLoader(App.class.getResource("Academia.fxml"));
            Scene scene= new Scene(loader.load());

            AcademiaViewController controller= loader.getController();
            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void cargarDatosPrueba() {
        // Evita duplicarlos si el método se llama otra vez.
        if (!academia.getListEstudiantes().isEmpty()) {
            return;
        }

        // -------------------- 3 ESTUDIANTES --------------------

        academia.crearEstudiante(
                "Ana Gómez", "1001", "3001111111",
                "ana@correo.com", 22, LocalDate.now()
        );
        academia.crearEstudiante(
                "Luis Pérez", "1002", "3002222222",
                "luis@correo.com", 28, LocalDate.now()
        );
        academia.crearEstudiante(
                "Sofía Ramírez", "1003", "3003333333",
                "sofia@correo.com", 19, LocalDate.now()
        );

        // -------------------- 3 PROFESORES --------------------

        academia.crearProfesor(
                "Laura Torres", "2001", "3101111111",
                Idioma.INGLES, 60_000
        );
        academia.crearProfesor(
                "Pierre Martin", "2002", "3102222222",
                Idioma.FRANCES, 70_000
        );
        academia.crearProfesor(
                "Carlos Méndez", "2003", "3103333333",
                Idioma.PORTUGUES, 55_000
        );

        // -------------------- SERVICIOS ADICIONALES --------------------

        academia.crearServicioAdicional(
                "SER-001",
                "Simulacro de examen de certificación",
                "Prueba de práctica con resultados",
                95_000
        );
        academia.crearServicioAdicional(
                "SER-002",
                "Tutoría de refuerzo",
                "Sesión adicional de acompañamiento",
                50_000
        );
        academia.crearServicioAdicional(
                "SER-003",
                "Material impreso",
                "Guías y ejercicios físicos",
                35_000
        );
        academia.crearServicioAdicional(
                "SER-004",
                "Talleres de conversación",
                "Práctica conversacional en grupo",
                45_000
        );

        // -------------------- 5 CURSOS --------------------

        academia.crearCursoRegular(
                "CUR-001", "Inglés básico", Idioma.INGLES,
                "Fundamentos de inglés", 6, 180_000,
                List.of()
        );
        academia.crearCursoIntensivo(
                "CUR-002", "Francés intensivo", Idioma.FRANCES,
                "Francés de práctica intensiva", 3, 290_000,
                List.of()
        );
        academia.crearCursoRegular(
                "CUR-003", "Portugués básico", Idioma.PORTUGUES,
                "Fundamentos de portugués", 6, 170_000,
                List.of()
        );
        academia.crearCursoPersonalizado(
                "CUR-004", "Inglés personalizado",
                Idioma.INGLES,
                "Preparación individual en inglés",
                4, 210_000, 8, Nivel.B1,
                "Preparar una entrevista laboral",
                List.of()
        );
        academia.crearCursoPersonalizado(
                "CUR-005", "Francés personalizado",
                Idioma.FRANCES,
                "Preparación individual en francés",
                4, 230_000, 6, Nivel.A2,
                "Mejorar la conversación",
                List.of()
        );

        // -------------------- 5 MATRÍCULAS --------------------

        Matricula m1 = new Matricula.Builder()
                .codigo("MAT-001")
                .estudiante(academia.buscarEstudiante("1001"))
                .curso(academia.buscarCurso("CUR-001"))
                .servicioAdicional(
                        academia.buscarServicioAdicional("SER-003")
                )
                .duracionContratada(6)
                .descuento(20_000)
                .build();
        academia.registrarMatricula(m1);

        Matricula m2 = new Matricula.Builder()
                .codigo("MAT-002")
                .estudiante(academia.buscarEstudiante("1002"))
                .curso(academia.buscarCurso("CUR-002"))
                .servicioAdicional(
                        academia.buscarServicioAdicional("SER-001")
                )
                .duracionContratada(3)
                .descuento(0)
                .build();
        academia.registrarMatricula(m2);

        Matricula m3 = new Matricula.Builder()
                .codigo("MAT-003")
                .estudiante(academia.buscarEstudiante("1003"))
                .curso(academia.buscarCurso("CUR-003"))
                .duracionContratada(6)
                .descuento(15_000)
                .build();
        academia.registrarMatricula(m3);

        Matricula m4 = new Matricula.Builder()
                .codigo("MAT-004")
                .estudiante(academia.buscarEstudiante("1001"))
                .curso(academia.buscarCurso("CUR-004"))
                .servicioAdicional(
                        academia.buscarServicioAdicional("SER-002")
                )
                .duracionContratada(4)
                .descuento(0)
                .build();
        m4.asignarProfesor(
                "CUR-004",
                academia.buscarProfesor("2001")
        );
        academia.registrarMatricula(m4);

        Matricula m5 = new Matricula.Builder()
                .codigo("MAT-005")
                .estudiante(academia.buscarEstudiante("1002"))
                .curso(academia.buscarCurso("CUR-005"))
                .servicioAdicional(
                        academia.buscarServicioAdicional("SER-004")
                )
                .duracionContratada(4)
                .descuento(25_000)
                .build();
        m5.asignarProfesor(
                "CUR-005",
                academia.buscarProfesor("2002")
        );
        academia.registrarMatricula(m5);

        Pago pago = new Pago(
                "PAG-001",
                150_000,
                "Transferencia"
        );

        m1.registrarPago(pago);
    }


public static void main(String[] args) {
    launch();
    }
}
