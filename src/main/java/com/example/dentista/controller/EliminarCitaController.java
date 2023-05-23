package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.CitaTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
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

public class EliminarCitaController implements Initializable {
    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private ComboBox citaComboBox;
    @FXML
    private TextField filterField;

    /**
     * Método que permite volver a la ventana principal
     *
     * @throws IOException
     */
    public static void volverMainStatic() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Seguro que quieres volver al menu principal?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            App.changeScene("windows/mainwindow.fxml", 641, 288);
        }
    }

    @FXML
    public void volverMain() throws IOException {
        volverMainStatic();
    }

    /**
     * Método que se aplica cada vez que ocurre un cambio en el componente "filterField",
     * que va rellenando el combo box con los datos recibidos del método BuscarController.filtrarNombre()
     */
    @FXML
    public void filtrarCliente() {

        clienteComboBox.getItems().clear();
        ArrayList<Cliente> clientes = BuscarController.filtrarCliente(filterField.getText());
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.getItems().add(clientes.get(i).getNombre());
        }
    }

    /**
     * Método que saca las citas del cliente seleccionado en el combo box
     */
    @FXML
    public void sacarCitas() {
        if (clienteComboBox.getValue() != null) {

            citaComboBox.getItems().clear();
            seleccionarCliente();
            if (!clienteComboBox.getPromptText().equals("- Seleccione un cliente -")) {
                ArrayList<String> citasNombres = CitaTable.sacarCitasNombre(clienteComboBox.getValue().toString(), new DataBaseConnection().getConnection());
                for (String cita : citasNombres) {
                    citaComboBox.getItems().add(cita);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Seleccione el cliente para ver las citas del cliente");
                a.show();
            }
        }
    }

    /**
     * Método de comprobación de selección de cliente
     */
    @FXML
    public void seleccionarCliente() {
        if (clienteComboBox.getValue() != null) {
            clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
        }
    }

    @FXML
    public void seleccionarCita() {
        if (citaComboBox.getValue() != null) {
            citaComboBox.setPromptText(citaComboBox.getValue().toString());
        }
    }

    @FXML
    public void eliminarCita() throws IOException {
        if (citaComboBox.getValue() != null) {
            if (CitaTable.eliminarCita(clienteComboBox.getValue().toString(), new DataBaseConnection().getConnection(), citaComboBox.getValue().toString().substring(0, citaComboBox.getValue().toString().indexOf("|")))) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("CONFIRMADO");
                a.setContentText("Se ha eliminado con éxito la cita");
                a.show();
                volverMainStatic();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("No se ha podido eliminar correctamente");
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Seleccione una cita para ser eliminada");
            a.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrarCliente();
    }
}
