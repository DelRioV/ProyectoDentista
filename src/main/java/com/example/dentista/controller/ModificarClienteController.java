package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        ArrayList<String> clientes = BuscarController.filtrarNombre(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i));
        }
    }

    @FXML
    public void cargarCliente() {
        String cliente = clienteComboBox.getValue().toString();
        Connection connection = new DataBaseConnection().getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre,dni,telefono,nacimiento FROM clientes WHERE nombre = '" + cliente + "'");
            while (rs.next()) {
                nombreField.setText(rs.getString(1));
                dniField.setText(rs.getString(2));
                telefonoField.setText(rs.getString(3));
                nacimientoDtPicker.setValue(rs.getDate(4).toLocalDate());
            }
            connection.close();
        } catch (Exception e) {

        }
    }

    @FXML
    public void modificarCliente() throws IOException {
        String abd = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        if(dniField.getText().length() != 9 || abd.indexOf(dniField.getText().substring(8,9)) == -1 || !dniField.getText().substring(0,8).matches("[0-9]+")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Solo se admite 8 números y 1 letra al final en la casilla dni");
            a.show();
        } else{
            if(telefonoField.getText().matches("[0-9]+") && telefonoField.getText().length() == 9){
                Cliente cliente = new Cliente(nombreField.getText(), dniField.getText(), telefonoField.getText(), nacimientoDtPicker.getValue().toString());
                boolean modificado = ClienteTable.modificarCliente(cliente, new DataBaseConnection().getConnection(), clienteComboBox.getValue().toString());
                if (modificado) {
                    System.out.printf("Modificado");
                    App.changeScene("windows/modificarclientewindow.fxml", 490, 280);

                } else {
                    System.out.printf("No modificado");
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
    public void comprobarDni() {
        if (dniField.getText().length() > 9) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("INFORMACIÓN");
            a.setContentText("Logitud máxima: 9");
            a.show();
            dniField.setText(dniField.getText().substring(0, 9));
        }
    }

    @FXML
    public void comprobarTelefono(){
        if (telefonoField.getText().length() > 9) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("INFORMACIÓN");
            a.setContentText("Logitud máxima: 9");
            a.show();
            telefonoField.setText(telefonoField.getText().substring(0, 9));
        }
    }
}
