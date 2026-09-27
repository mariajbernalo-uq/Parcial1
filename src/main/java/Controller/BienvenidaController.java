package Controller;

import App.App;

public class BienvenidaController {

    private App app;

    public BienvenidaController(App app) {
        this.app = app;
    }

    public void abrirAcademia() {

        try {
            app.mostrarVistaAcademia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}