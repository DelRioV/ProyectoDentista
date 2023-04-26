module com.example.dentista {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dentista to javafx.fxml;
    exports com.example.dentista;
}