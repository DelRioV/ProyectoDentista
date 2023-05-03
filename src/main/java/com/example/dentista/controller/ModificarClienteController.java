package com.example.dentista.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ModificarClienteController {

    @FXML
    private TextField filtroField;
    @FXML
    private TextArea resultadoArea;

    @FXML
    public void filtrarCliente() {
        resultadoArea.setText("");
        ArrayList<String> clientes = BuscarController.filtrarCliente(filtroField.getText());
        for (String cliente : clientes) {
            resultadoArea.setText(resultadoArea.getText() + " || " + cliente);
        }
    }
}
