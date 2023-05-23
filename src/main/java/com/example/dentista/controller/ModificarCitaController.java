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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * Saca los datos de la cita seleccionada y rellena los campos correspondientes
     */
    @FXML
    public void rellenarDatos() {
        if (citaComboBox.getValue() != null) {
            citaComboBox.setPromptText(citaComboBox.getValue().toString());
            if (citaComboBox.getValue() != null) {
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
                        horaComboBox.setValue(rs.getString(4));
                        horaComboBox.setPromptText(rs.getString(4));
                        horafinCBox.setValue(rs.getString(5));
                        horafinCBox.setPromptText(rs.getString(5));
                    }
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Se encarga de ejecutar la sentencia sql de actualización de la base de datos
     *
     * @throws IOException
     * @throws ParseException -> Si se produce un fallo de conversion de String a Date
     */
    @FXML
    public void modificarCita() throws IOException, ParseException {
        if (!clienteComboBox.getPromptText().toString().equals("- Seleccione un cliente -") && !citaComboBox.getPromptText().toString().equals("- Seleccione la cita que desea modificar -") && !horaComboBox.getPromptText().toString().equals("- Hora de la cita -") && !fechaCitaDtPicker.getPromptText().equals("Elija la fecha...") && citaComboBox.getValue() != null) {
            if (comprobarDisponibilidad()) {
                if (comprobarDuracion()) {
                    Cita cita = new Cita(fechaCitaDtPicker.getValue(), dni,
                            descripcionTextArea.getText(), horaComboBox.getValue().toString(),
                            horafinCBox.getValue().toString());
                    if (CitaTable.modificarCita(cita, new DataBaseConnection().getConnection(), citaComboBox.getValue().toString())) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("¡HECHO!");
                        a.setContentText("Cita modificada con éxito");
                        a.show();
                        App.changeScene("windows/mainwindow.fxml", 641, 288);
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("¡ERROR!");
                        a.setContentText("No es posible modificar la cita");
                        a.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setContentText("Solo puedes introducir una fecha posterior a la de inicio");
                    a.show();
                }
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Rellene los campos");
            a.show();
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
        ArrayList<String> fechasHoras = CitaTable.sacarFechasHoras(fechaCitaDtPicker.getValue(), new DataBaseConnection().getConnection(), clienteComboBox.getValue().toString());
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
     * Método de comprobación de selección de cliente
     */
    @FXML
    public void seleccionarCliente() {
        if (clienteComboBox.getValue() != null) {
            clienteComboBox.setPromptText(clienteComboBox.getValue().toString());
        }
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
     * Método que se ejecuta al cargar la pantalla
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
