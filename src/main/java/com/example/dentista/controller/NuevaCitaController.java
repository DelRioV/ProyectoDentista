package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.CitaTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Clase que controla las acciones de la ventana nuevacitawindow.fxml
 *
 * @author: Pablo Salvador Del Río Vergara
 * @version: 09/05/2023
 */
public class NuevaCitaController implements Initializable {

    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private ComboBox horaComboBox;
    @FXML
    private TextField filterField;
    @FXML
    private DatePicker fechaCitaDtPicker;
    @FXML
    private TextField duracionField;
    @FXML
    private TextArea descripcionTextArea;
    private String nombreSeleccionado;

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
     * Método que toma el nombre del cliente elegido al seleccionarlo en "clienteComboBox"
     */
    @FXML
    public void tomarNombre() {
        nombreSeleccionado = clienteComboBox.getValue().toString();
        seleccionarCliente();
    }

    /**
     * Método que comprueba si la duración introducida es correcta
     *
     * @return boolean -> true - si es correcta
     * -> false - si no es correcta
     */
    @FXML
    public boolean comprobarDuracion() {
        boolean comprobado = false;
        if (duracionField.getText().matches("[0-9]+") && duracionField.getText().length() <= 2) {
            comprobado = true;
        }
        return comprobado;
    }

    /**
     * Método que comprueba si es posible registrar la cita para la hora determinada con la duración estimada
     *
     * @return -> true - si es correcta
     * -> false - si no es correcta
     */
    @FXML
    public boolean comprobarDisponibilidad() {
        boolean disponible = true;
        ArrayList<String> fechasHoras = CitaTable.sacarFechasHoras(fechaCitaDtPicker.getValue(), new DataBaseConnection().getConnection());
        System.out.println(fechasHoras);
        for (int i = 0; i < fechasHoras.size(); i = i + 2) {

        }
        return disponible;
    }

    /**
     * Método que se encarga de registrar la cita en la tabla "citas"
     *
     * @throws IOException
     */
    @FXML
    public void guardarCita() throws IOException {
        if (!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -") && !horaComboBox.getPromptText().toString().equals("- Hora de la cita -") && !fechaCitaDtPicker.getPromptText().equals("Elija la fecha...")) {
            if (comprobarDisponibilidad()) {
                if (comprobarDuracion()) {
                    Cita cita = new Cita(fechaCitaDtPicker.getValue(),
                            BuscarController.filtrarCliente(nombreSeleccionado).get(0).getDni(),
                            descripcionTextArea.getText(), horaComboBox.getValue().toString(),
                            Integer.parseInt(duracionField.getText()));
                    if (CitaTable.nuevaCita(cita, new DataBaseConnection().getConnection())) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("REGISTRADO");
                        a.setContentText("SE HA CONFIRMADO EL REGISTRO DE SU CITA");
                        a.show();
                        App.changeScene("windows/nuevacitawindow.fxml", 493, 297);
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setContentText("Solo se pueden introducir dos dígitos numéricos");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Ya existe una cita durante las fechas señaladas");
                a.show();
            }

        }
    }

    /**
     * Método de comprobación de selección de cliente
     */
    @FXML
    public void seleccionarCliente() {
        clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
    }

    /**
     * Método de comprobación de selección de hora
     */
    @FXML
    public void seleccionarHora() {
        horaComboBox.setPromptText(horaComboBox.getValue().toString());
    }

    /**
     * Método de comprobación de selección de hora
     */
    @FXML
    public void seleccionarFecha() {
        fechaCitaDtPicker.setPromptText(fechaCitaDtPicker.getValue().toString());
    }

    /**
     * Método que se realiza al cargar la ventana y carga las horas en su comboBox
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrarCliente();
        for (int i = 16; i < 21; i++) {
            horaComboBox.getItems().add(i + ":00");
            horaComboBox.getItems().add(i + ":30");
        }
    }
}
