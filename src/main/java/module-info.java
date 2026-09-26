module Parcial1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens uniquindio to javafx.fxml;
    exports uniquindio ;
    exports uniquindio.ViewController;
    opens uniquindio.ViewController to javafx.fxml;
}