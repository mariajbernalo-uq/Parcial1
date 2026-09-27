module co.uniquindio.parcial1 {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.dynalink;

    // Permitir que JavaFX acceda a los controladores mediante FXML
    opens App to javafx.fxml;
    opens Controller to javafx.fxml;
    opens ViewController to javafx.fxml;
    opens Model to javafx.fxml;
    opens Factory to javafx.fxml;

    // Exportar los paquetes
    exports App;
    exports Controller;
    exports ViewController;
    exports Model;
    exports Factory;
}