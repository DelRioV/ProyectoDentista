package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MostrarClientesController implements Initializable {
    @FXML
    private TextField filterField;
    @FXML
    private TableView tableView;

    @FXML
    public void filtrarCliente() {
        tableView.getItems().clear();
        ArrayList<Cliente> clientes = BuscarController.filtrarCliente(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            tableView.getItems().add(clientes.get(i));
        }
        tableView.refresh();
    }

    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Cliente, String> column1 =
                new TableColumn<>("Nombre");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));


        TableColumn<Cliente, String> column2 =
                new TableColumn<>("DNI");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("dni"));

        TableColumn<Cliente, String> column3 =
                new TableColumn<>("Telefono");

        column3.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));

        TableColumn<Cliente, String> column4 =
                new TableColumn<>("Fecha de nacimiento");

        column4.setPrefWidth(260);

        column4.setCellValueFactory(
                new PropertyValueFactory<>("fechaNacimiento"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
         filtrarCliente();
    }
}
