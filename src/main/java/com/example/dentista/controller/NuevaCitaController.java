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

        return comprobado;
    }

    @FXML
    public boolean comprobarDisponibilidad() {
        boolean disponible = false;

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
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("REGISTRADO");
                    a.setContentText("SE HA CONFIRMADO EL REGISTRO DE SU CITA");
                    a.show();
                }
            } else {

            }
        } else {

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
