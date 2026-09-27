package App;

import Model.Academia;
import ViewController.AcademiaViewController;
import ViewController.BienvenidaViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Academia academia;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;

        // Se obtiene la instancia única de la academia
        academia = Academia.getInstance(
                "Lenguaje Cafetero",
                "123",
                "Fundadores"
        );

        mostrarVistaBienvenida();
    }

    // ==========================================
    // VISTA DE BIENVENIDA
    // ==========================================

    public void mostrarVistaBienvenida() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/co/uniquindio/parcial1/Bienvenida.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        BienvenidaViewController controller =
                loader.getController();

        controller.setApp(this);

        stage.setTitle("Lenguaje Cafetero");
        stage.setScene(scene);
        stage.show();
    }


    // ==========================================
    // VISTA PRINCIPAL DE LA ACADEMIA
    // ==========================================

    public void mostrarVistaAcademia() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/co/uniquindio/parcial1/Academia.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        AcademiaViewController controller =
                loader.getController();

        controller.setApp(this);

        stage.setTitle("Lenguaje Cafetero - Academia");
        stage.setScene(scene);
        stage.show();
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public Academia getAcademia() {
        return academia;
    }

    public Stage getStage() {
        return stage;
    }


    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args) {
        launch();
    }
}