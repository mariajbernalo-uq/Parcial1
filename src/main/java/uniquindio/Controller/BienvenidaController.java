package uniquindio.Controller;
import uniquindio.App;


public class BienvenidaController {
    private App app;
    public BienvenidaController(App app){
        this.app=(app);
    }


    public void abrirBienvenida(){
        app.abrirVistaBienvenida();
    }
    public void abrirVistaAcademia(){
        app.abrirVistaAcademia();
    }

}
