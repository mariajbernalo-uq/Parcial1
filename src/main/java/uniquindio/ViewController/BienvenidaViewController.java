package uniquindio.ViewController;

import javafx.fxml.FXML;
import uniquindio.App;
import uniquindio.Controller.AcademiaController;
import uniquindio.Controller.BienvenidaController;

public class BienvenidaViewController {
    private App app;
    private BienvenidaController bienvenidaController;
    @FXML
    void onOpenAcademia(){
        bienvenidaController.abrirVistaAcademia();
    }

    @FXML
    void initialize(){
    }



public void setApp(App app){
    this.app=app;
    this.bienvenidaController= new BienvenidaController(app);
}
}
