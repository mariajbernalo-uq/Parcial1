module co.uniquindio.parcial1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens co.uniquindio.parcial1 to javafx.fxml;
    exports co.uniquindio.parcial1;
}