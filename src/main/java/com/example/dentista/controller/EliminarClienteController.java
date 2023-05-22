package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que controla las acciones de la ventana eliminarclientewindow.fxml
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 08/05/2023
 */
public class EliminarClienteController implements Initializable {

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
        EliminarCitaController.volverMain();
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
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("INFORMACIÓN");
                a.setContentText("Cliente eliminado con éxito");
                a.show();
                App.changeScene("windows/eliminarclientewindow.fxml", 469, 130);
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Seleccione un cliente");
            a.show();
        }
    }

    /**
     * Método que se usa como comprobación de que ha seleccionado un cliente
     */
    @FXML
    public void seleccionarCliente() {
        if (clienteComboBox.getValue() != null) {
            clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrarCliente();
    }
}
