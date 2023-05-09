package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.CitaTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NuevaCitaController implements Initializable {

    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private ComboBox horaComboBox;
    @FXML
    private TextField filterField;
    @FXML
    private DatePicker fechaCitaDtPicker;
    @FXML
    private TextField duracionField;
    @FXML
    private TextArea descripcionTextArea;
    private String nombreSeleccionado;

    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    @FXML
    public void filtrarCliente() {
        clienteComboBox.getItems().clear();
        ArrayList<Cliente> clientes = BuscarController.filtrarCliente(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i).getNombre());
        }
    }

    @FXML
    public void tomarNombre() {
        nombreSeleccionado = clienteComboBox.getValue().toString();
    }

    @FXML
    public boolean comprobarDuracion() {
        boolean comprobado = false;
        if (duracionField.getText().matches("[0-9]+") && duracionField.getText().length() <= 2) {
            comprobado = true;
        }
        return comprobado;
    }

    @FXML
    public boolean comprobarDisponibilidad() {
        boolean disponible = true;
        ArrayList<String> fechasHoras = CitaTable.sacarFechasHoras(fechaCitaDtPicker.getValue(), new DataBaseConnection().getConnection());
        System.out.println(fechasHoras);
        for (int i = 0; i < fechasHoras.size(); i = i + 2) {
            int duracionfield1 = (Integer.parseInt(duracionField.getText()) + (Integer.parseInt(fechasHoras.get(i))));
            int duracionfield2 = (Integer.parseInt(duracionField.getText()) + (Integer.parseInt(fechasHoras.get(i + 1))));
            if (duracionfield2 >= 60) {
                duracionfield1 = Integer.parseInt(String.valueOf(duracionfield1).substring(0, 2)) * 100 + 100;
                duracionfield2 = duracionfield2 - 60;
            }

            System.out.println(duracionfield1 + " - " + duracionfield2);
            if (Integer.parseInt(horaComboBox.getValue().toString().replace(":", "")) <= duracionfield1
                    && Integer.parseInt(horaComboBox.getValue().toString().replace(":", "")) > duracionfield2) {
                disponible = false;
            }
        }
        System.out.println(disponible);
        return disponible;
    }

    @FXML
    public void guardarCita() {
        if (comprobarDisponibilidad()) {
            if (comprobarDuracion()) {
                Cita cita = new Cita(fechaCitaDtPicker.getValue(),
                        BuscarController.filtrarCliente(nombreSeleccionado).get(0).getDni(),
                        descripcionTextArea.getText(), horaComboBox.getValue().toString(),
                        Integer.parseInt(duracionField.getText()));
                if (CitaTable.nuevaCita(cita, new DataBaseConnection().getConnection())) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("REGISTRADO");
                    a.setContentText("SE HA CONFIRMADO EL REGISTRO DE SU CITA");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Solo se pueden introducir dos dígitos numéricos");
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Ya existe una cita durante las fechas señaladas");
            a.show();
        }

    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrarCliente();
        for (int i = 16; i < 21; i++) {
            horaComboBox.getItems().add(i + ":00");
            horaComboBox.getItems().add(i + ":30");
        }
    }
}
