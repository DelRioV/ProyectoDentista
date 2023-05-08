package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NuevoClienteController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField telefonoField;
    @FXML
    private DatePicker nacimientoDtPicker;

    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    @FXML
    public void guardarCliente() throws IOException {
        Cliente cliente = new Cliente(nombreField.getText(), dniField.getText(), telefonoField.getText(), nacimientoDtPicker.getValue().toString());
        boolean registrado = ClienteTable.nuevoCliente(cliente, new DataBaseConnection().getConnection());
        if (registrado) {
            System.out.printf("Registrado");
            volverMain();
            } else {
            System.out.printf("No registrado");
        }
    }

    @FXML
    public void comprobarDni() {
        if (dniField.getText().length() > 9) {
            dniField.setText(dniField.getText().substring(0, 9));
        }
    }

    @FXML
    public void comprobarTelefono(){
        if (telefonoField.getText().length() > 9) {
            telefonoField.setText(telefonoField.getText().substring(0, 9));
        }
    }

}
