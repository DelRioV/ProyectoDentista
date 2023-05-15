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

/**
 * Clase que controla las acciones de la ventana mostrarclientewindow.fxml
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 08/05/2023
 */
public class MostrarClienteController implements Initializable {
    @FXML
    private TextField filterField;
    @FXML
    private TableView tableView;

    /**
     * Método que rellena la tabla conforme se van filtrando los clientes
     */
    @FXML
    public void filtrarCliente() {
        tableView.getItems().clear();
        ArrayList<Cliente> clientes = BuscarController.filtrarCliente(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            tableView.getItems().add(clientes.get(i));
        }
        tableView.refresh();
    }

    /**
     * Método que permite volver a la ventana principal
     *
     * @throws IOException
     */
    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    /**
     * Método que se realiza al cargar la ventana, se encarga de añadir las columnas a la tabla donde se van a mostrar los clientes
     *
     * @param url
     * @param resourceBundle
     */
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

        column4.setPrefWidth(249);

        column4.setCellValueFactory(
                new PropertyValueFactory<>("fechaNacimiento"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        filtrarCliente();
    }
}
