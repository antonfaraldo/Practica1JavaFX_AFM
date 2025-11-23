module com.example.practica1javafx_afm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.practica1javafx_afm to javafx.fxml;
    exports com.example.practica1javafx_afm;
}