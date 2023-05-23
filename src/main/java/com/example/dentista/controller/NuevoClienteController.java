package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Clase que controla las acciones de la ventana nuevoclientewindow.fxml
 *
 * @author: Pablo Salvador Del Río Vergara
 * @version: 26/04/2023
 */
public class NuevoClienteController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField telefonoField;
    @FXML
    private DatePicker nacimientoDtPicker;

    /**
     * Método que permite volver a la ventana principal
     *
     * @throws IOException
     */
    @FXML
    public void volverMain() throws IOException {
        EliminarCitaController.volverMainStatic();
    }
    /**
     * Método de comprobación de selección de fecha de nacimiento
     */
    @FXML
    public void seleccionarFecha() {
        nacimientoDtPicker.setPromptText(nacimientoDtPicker.getValue().toString());
    }

    /**
     * Método que se encarga de realizar la inserción de datos en la tabla "clientes"
     *
     * @throws IOException
     */
    @FXML
    public void guardarCliente() throws IOException {
        if (!nacimientoDtPicker.getPromptText().equals("Elija la fecha...")) {
            String abd = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
            if (dniField.getText().length() != 9 || abd.indexOf(dniField.getText().substring(8, 9)) == -1 || !dniField.getText().substring(0, 8).matches("[0-9]+")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Solo se admite 8 números y 1 letra al final en la casilla dni");
                a.show();
            } else {
                if (telefonoField.getText().matches("[0-9]+") && telefonoField.getText().length() == 9) {
                    Cliente cliente = new Cliente(nombreField.getText(), dniField.getText(), telefonoField.getText(), nacimientoDtPicker.getValue().toString());
                    boolean registrado = ClienteTable.nuevoCliente(cliente, new DataBaseConnection().getConnection());
                    if (registrado) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("INFORMACIÓN");
                        a.setContentText("Cliente registrado con éxito");
                        a.show();
                        App.changeScene("windows/mainwindow.fxml", 641, 288);
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setHeaderText(null);
                        a.setContentText("No se pueden agregar dos clientes con el mismo nombre");
                        a.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setContentText("Solo se adminten números en la casilla de teléfono");
                    a.show();
                }

            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Seleccione una fecha");
            a.show();
        }
    }

    /**
     * Método que se encarga de comprobar si el dni se ha introducido de manera correcta
     */
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

    /**
     * Método que se encarga de comprobar si el número de teléfono se ha introducido de manera correcta
     */
    @FXML
    public void comprobarTelefono() {
        if (telefonoField.getText().length() > 9) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("INFORMACIÓN");
            a.setContentText("Logitud máxima: 9");
            a.show();
            telefonoField.setText(telefonoField.getText().substring(0, 9));
        }
    }

}
