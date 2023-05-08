package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        String abd = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        if (dniField.getText().length() != 9 || abd.indexOf(dniField.getText().substring(8, 9)) == -1 || !dniField.getText().substring(0, 8).matches("[0-9]+")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Solo se admite 8 números y 1 letra al final en la casilla dni");
            a.show();
        } else {
            if (telefonoField.getText().matches("[0-9]+") && telefonoField.getText().length() == 9) {
                Cliente cliente = new Cliente(nombreField.getText(), dniField.getText(), telefonoField.getText(), nacimientoDtPicker.getValue().toString());
                boolean registrado = ClienteTable.nuevoCliente(cliente, new DataBaseConnection().getConnection());
                if (registrado) {
                    System.out.printf("Registrado");
                    volverMain();
                } else {
                    System.out.printf("No registrado");
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Solo se adminten números en la casilla de teléfono");
                a.show();
            }

        }
    }
        @FXML
        public void comprobarDni () {
            if (dniField.getText().length() > 9) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("INFORMACIÓN");
                a.setContentText("Logitud máxima: 9");
                a.show();
                dniField.setText(dniField.getText().substring(0, 9));
            }
        }

        @FXML
        public void comprobarTelefono () {
            if (telefonoField.getText().length() > 9) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("INFORMACIÓN");
                a.setContentText("Logitud máxima: 9");
                a.show();
                telefonoField.setText(telefonoField.getText().substring(0, 9));
            }
        }

    }
