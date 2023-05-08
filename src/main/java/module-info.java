module com.example.dentista {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dentista to javafx.fxml;
    exports com.example.dentista;
    exports com.example.dentista.controller;
    opens com.example.dentista.controller to javafx.fxml;
    opens com.example.dentista.model to javafx.base;
}