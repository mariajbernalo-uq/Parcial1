package uniquindio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.Model.Academia;
import uniquindio.ViewController.AcademiaViewController;
import uniquindio.ViewController.BienvenidaViewController;


public class App extends Application {
    //Creación de la Academia
    private final Academia academia = Academia.getInstancia(
            "Academia de idiomas",
            "123456789",
            "3000000000",
            "Calle 10",
            "academia@gmail.com",
            "academiaidiomas.com"
    );

    public Academia getAcademia() {
        return academia;
    }
//Atributos de Vista JAVAFX
private Stage primaryStage;

@Override
public void start(Stage stage){
    this.primaryStage= stage;
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

public static void main(String[] args) {
    launch();
    }
}
