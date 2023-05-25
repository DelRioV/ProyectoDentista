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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ComboBox horafinCBox;
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
        EliminarCitaController.volverMainStatic();
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
        if (clienteComboBox.getValue() != null) {

            nombreSeleccionado = clienteComboBox.getValue().toString();
            seleccionarCliente();
        }
    }

    /**
     * Método que comprueba si la duración introducida es correcta
     *
     * @return boolean -> true - si es correcta
     * -> false - si no es correcta
     */
    public boolean comprobarDuracion() {
        boolean comprobado = false;
        if (Integer.parseInt(horaComboBox.getValue().toString().replace(":", "")) < Integer.parseInt(horafinCBox.getValue().toString().replace(":", ""))) {
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
    public boolean comprobarDisponibilidad() throws ParseException {
        boolean disponible = true;
        ArrayList<String> fechasHoras = CitaTable.sacarFechasHoras(fechaCitaDtPicker.getValue(), new DataBaseConnection().getConnection());
        System.out.println(fechasHoras);
        for (int i = 0; i < fechasHoras.size(); i = i + 2) {
            Date inicioGuardado = new SimpleDateFormat("HH:mm").parse(fechasHoras.get(i));
            Date finalGuardado = new SimpleDateFormat("HH:mm").parse(fechasHoras.get(i + 1));
            Date inicioCita = new SimpleDateFormat("HH:mm").parse(horaComboBox.getValue().toString());
            Date finalCita = new SimpleDateFormat("HH:mm").parse(horafinCBox.getValue().toString());

            if (finalCita.after(finalGuardado) && inicioCita.before(inicioGuardado)
                    || inicioCita.before(finalGuardado) && finalCita.after(finalGuardado)
                    || inicioCita.before(inicioGuardado) && finalCita.before(finalGuardado)
                    || inicioCita.equals(inicioGuardado)) {
                disponible = false;
            }
        }
        return disponible;
    }

    /**
     * Método que se encarga de registrar la cita en la tabla "citas"
     *
     * @throws IOException
     */
    @FXML
    public void guardarCita() throws IOException, ParseException {
        if (!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -") && clienteComboBox.getValue() != null && !horaComboBox.getPromptText().toString().equals("- Hora de la cita -")
                && !horafinCBox.getPromptText().toString().equals("- Hora de fin -") && !fechaCitaDtPicker.getPromptText().equals("Elija la fecha...")) {
            if (comprobarDisponibilidad()) {
                if (comprobarDuracion()) {
                    Cita cita = new Cita(fechaCitaDtPicker.getValue(),
                            BuscarController.filtrarCliente(nombreSeleccionado).get(0).getDni(),
                            descripcionTextArea.getText(), horaComboBox.getValue().toString(),
                            horafinCBox.getValue().toString());
                    if (CitaTable.nuevaCita(cita, new DataBaseConnection().getConnection())) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("REGISTRADO");
                        a.setContentText("SE HA CONFIRMADO EL REGISTRO DE SU CITA");
                        a.show();
                        App.changeScene("windows/mainwindow.fxml", 641, 288);
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setContentText("No se pueden registrar dos citas para la misma persona el mismo día");
                        a.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setContentText("Solo puedes introducir una fecha posterior a la de inicio");
                    a.show();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setContentText("Ya existe una cita durante las fechas señaladas");
                a.show();
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Por favor, rellene los campos");
            a.show();
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

    /**
     * Método de comprobación de selección de hora de inicio
     */
    @FXML
    public void seleccionarHora() {
        horaComboBox.setPromptText(horaComboBox.getValue().toString());
    }

    /**
     * Método de comprobación de selección de hora de fin
     */
    @FXML
    public void seleccionarHoraFin() {
        horafinCBox.setPromptText(horafinCBox.getValue().toString());
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
            horafinCBox.getItems().add(i + ":00");
            horafinCBox.getItems().add(i + ":30");
        }
    }
}
