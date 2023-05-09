package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NuevaCitaController implements Initializable {

    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private TextField filterField;

    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    @FXML
    public void filtrarCliente() {
        clienteComboBox.getItems().clear();
        ArrayList<String> clientes = BuscarController.filtrarNombre(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i));
        }
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrarCliente();
    }
}
