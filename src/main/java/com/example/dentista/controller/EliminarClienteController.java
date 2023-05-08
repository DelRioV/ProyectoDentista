package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class EliminarClienteController {
    @FXML
    private TextField filterField;

    @FXML
    private ComboBox clienteComboBox;

    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    @FXML
    public void filtrarCliente() {
        clienteComboBox.getItems().clear();
        ArrayList<String> clientes = BuscarController.filtrarCliente(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i));
        }
    }

    @FXML
    public void eliminarCliente() throws IOException {
        if(!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -")) {
            boolean eliminado = ClienteTable.eliminarCliente(new DataBaseConnection().getConnection(), clienteComboBox.getValue().toString());
            if (eliminado) {
                System.out.printf("Eliminado");
                App.changeScene("windows/eliminarclientewindow.fxml", 469, 130);
            } else {
                System.out.printf("No eliminado");
            }
        } else {
            System.out.println("Rellene el cliente");
        }
    }

    @FXML
    public void seleccionarCliente(){
        clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
    }
}
