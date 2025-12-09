module com.example.practica1javafx_afm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;


    opens com.practica.javafx to javafx.fxml;
    opens com.practica.javafx.controller to javafx.fxml;
    exports com.practica.javafx;

    opens com.practica.javafx.dao to javafx.fxml;
    opens com.practica.javafx.model to javafx.fxml;
    opens com.practica.javafx.db to javafx.fxml;


    opens com.vistas to javafx.fxml, javafx.graphics;
}
