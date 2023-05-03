package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ModificarClienteController {

    @FXML
    private TextField filterField;

    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField telefonoField;
    @FXML
    private DatePicker nacimientoDtPicker;

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
    public void cargarCliente() {
        String cliente = clienteComboBox.getValue().toString();
        DataBaseConnection dataB = new DataBaseConnection();
        Connection connection = dataB.getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre,dni,telefono,nacimiento FROM clientes WHERE nombre = '" + cliente + "'");
            while (rs.next()) {
                nombreField.setText(rs.getString(1));
                dniField.setText(rs.getString(2));
                telefonoField.setText(rs.getString(3));
                nacimientoDtPicker.setPromptText(rs.getString(4));
            }
            connection.close();
        } catch (Exception e) {

        }
    }
}
