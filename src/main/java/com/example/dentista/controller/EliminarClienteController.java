package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que controla las acciones de la ventana eliminarclientewindow.fxml
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 08/05/2023
 */
public class EliminarClienteController {

    @FXML
    private TextField filterField;

    @FXML
    private ComboBox clienteComboBox;

    /**
     * Método que permite volver a la ventana principal
     *
     * @throws IOException
     */
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }

    /**
     * Método que se aplica cada vez que ocurre un cambio en el componente "filterField",
     * que va rellenando el combo box con los datos recibidos del método BuscarController.filtrarNombre()
     */
    @FXML
    public void filtrarCliente() {
        clienteComboBox.getItems().clear();
        ArrayList<String> clientes = BuscarController.filtrarNombre(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i));
        }
    }

    /**
     * Método que se ejecuta al dar al botón de eliminar, elimina los registros del cliente
     *
     * @throws IOException
     */
    @FXML
    public void eliminarCliente() throws IOException {
        if (!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -")) {
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

    /**
     * Método que se usa como comprobación de que ha seleccionado un cliente
     */
    @FXML
    public void seleccionarCliente() {
        clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
    }
}
