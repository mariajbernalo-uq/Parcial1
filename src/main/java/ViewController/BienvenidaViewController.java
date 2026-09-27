package ViewController;

import App.App;
import Controller.BienvenidaController;
import javafx.fxml.FXML;

public class BienvenidaViewController {

    private App app;
    private BienvenidaController bienvenidaController;

    public void setApp(App app) {
        this.app = app;
        this.bienvenidaController =
                new BienvenidaController(app);
    }

    @FXML
    private void onOpenAcademia() {

        bienvenidaController.abrirAcademia();
    }
}