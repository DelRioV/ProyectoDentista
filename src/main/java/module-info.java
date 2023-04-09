module com.example.dentista {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dentista to javafx.fxml;
    exports com.example.dentista;
}