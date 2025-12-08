module com.example.practica1javafx_afm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;


    opens com.practica.javafx to javafx.fxml;
    exports com.practica.javafx;

    opens com.practica.javafx.controller to javafx.fxml;
}
