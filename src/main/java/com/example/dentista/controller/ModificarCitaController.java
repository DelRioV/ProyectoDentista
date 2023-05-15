package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.CitaTable;
import com.example.dentista.database.ClienteTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Clase que controla las acciones de la ventana modificarcitawindow.fxml
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 15/05/2023
 */
public class ModificarCitaController implements Initializable {

    @FXML
    private ComboBox clienteComboBox;
    @FXML
    private TextField filterField;
    @FXML
    private ComboBox citaComboBox;
    @FXML
    private ComboBox horaComboBox;
    @FXML
    private DatePicker fechaCitaDtPicker;
    @FXML
    private TextArea descripcionTextArea;
    @FXML
    private ComboBox horafinCBox;
    private String dni;

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

    @FXML
    public void sacarCitas() {
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

    @FXML
    public void rellenarDatos() {
        String fecha = citaComboBox.getValue().toString().substring(0, citaComboBox.getValue().toString().indexOf("|"));
        try {
            Connection con = new DataBaseConnection().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT dni FROM clientes " + " WHERE nombre = '" + clienteComboBox.getValue().toString() + "'");
            rs.next();
            dni = rs.getString(1);
            rs = st.executeQuery("SELECT * FROM citas WHERE fecha_cita = '" + fecha.trim() + "' AND dni_cliente = '" + dni + "'");
            while (rs.next()) {
                fechaCitaDtPicker.setValue(rs.getDate(1).toLocalDate());
                fechaCitaDtPicker.setPromptText(rs.getString(1));
                descripcionTextArea.setText(rs.getString(3));
                horaComboBox.setValue(rs.getString(4).substring(0, 2) + ":" + rs.getString(4).substring(2));
                horaComboBox.setPromptText(rs.getString(4));
                horafinCBox.setValue(rs.getString(5));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void modificarCita() throws IOException, ParseException {
        if (!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -") && !horaComboBox.getPromptText().toString().equals("- Hora de la cita -") && !fechaCitaDtPicker.getPromptText().equals("Elija la fecha...")) {
            if (comprobarDisponibilidad()) {
                Cita cita = new Cita(fechaCitaDtPicker.getValue(), dni,
                        descripcionTextArea.getText(), horaComboBox.getValue().toString().replace(":", ""),
                        horafinCBox.getValue().toString());
                if (CitaTable.modificarCita(cita, new DataBaseConnection().getConnection(), clienteComboBox.getValue().toString())) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("¡HECHO!");
                    a.setContentText("Cita modificada con éxito");
                    a.show();
                    volverMain();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("¡ERROR!");
                    a.setContentText("No es posible modificar la cita");
                    a.show();
                }
            }
        }
    }
    /**
     * Método de comprobación de selección de hora de fin
     */
    @FXML
    public void seleccionarHoraFin() {
        horafinCBox.setPromptText(horafinCBox.getValue().toString());
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
        ArrayList<String> fechasHoras = CitaTable.sacarFechasHoras(fechaCitaDtPicker.getValue(), new DataBaseConnection().getConnection(),clienteComboBox.getValue().toString());
        System.out.println(fechasHoras);
        for (int i = 0; i < fechasHoras.size(); i = i + 2) {
            Date inicioGuardado = new SimpleDateFormat("HH:mm").parse(fechasHoras.get(i));
            Date finalGuardado = new SimpleDateFormat("HH:mm").parse(fechasHoras.get(i + 1));
            Date inicioCita = new SimpleDateFormat("HH:mm").parse(horaComboBox.getValue().toString());
            Date finalCita = new SimpleDateFormat("HH:mm").parse(horafinCBox.getValue().toString());

            if(finalCita.after(finalGuardado) && inicioCita.before(inicioGuardado)
                    || inicioCita.before(finalGuardado) && finalCita.after(finalGuardado)
                    || inicioCita.before(inicioGuardado) && finalCita.before(finalGuardado)
                    || inicioCita.equals(inicioGuardado)){
                disponible = false;
            }
        }
        System.out.println(disponible);
        return disponible;
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
