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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que controla las acciones de la ventana mostrarcitawindow.fxml
 *
 * @author: Pablo Salvador Del Río Vergara
 * @version: 25/05/2023
 */
public class MostrarCitaController implements Initializable {

    @FXML
    private TableView tablaCitasTableView;
    @FXML
    private DatePicker diaDtPicker;

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
     * Método que filtra por día las citas (sin pasarle una fecha)
     */
    @FXML
    public void filtrarPorDia() {
        tomarDia();
        tablaCitasTableView.getItems().clear();
        ArrayList<Cita> citas = CitaTable.tomarCitas(new DataBaseConnection().getConnection(), diaDtPicker.getValue().toString());
        for (int i = 0; i < citas.size(); i++) {
            tablaCitasTableView.getItems().add(citas.get(i));
        }
    }

    /**
     * Método que filtra por día las citas (pasandole una fecha)
     */
    @FXML
    public void filtrarPorDia(String fecha) {
        tomarDia();
        ArrayList<Cita> citas = CitaTable.tomarCitas(new DataBaseConnection().getConnection(), fecha);
        for (int i = 0; i < citas.size(); i++) {
            tablaCitasTableView.getItems().add(citas.get(i));
        }
    }

    /**
     * Método que recarga la ventana
     *
     * @throws IOException
     */
    @FXML
    public void recargar() throws IOException {
        App.changeScene("windows/mostrarcitawindow.fxml", 600, 338);
    }

    /**
     * Método que permite mandar un mensaje predeterminado por whatsapp
     *
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    @FXML
    public void mandarMensaje() throws MalformedURLException, UnsupportedEncodingException {
        if (tablaCitasTableView.getSelectionModel().getSelectedItem() != null) {
            Cita cita = (Cita) tablaCitasTableView.getSelectionModel().getSelectedItem();
            Cliente cliente = ClienteTable.tomarCliente(new DataBaseConnection().getConnection(), cita.getDniCliente());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Quieres mandar un mensaje al cliente seleccionado con su cita?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                WhatsappController.whatsapp(cliente, cita);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Seleccione una cita para poder mandarle un recordatorio");
            alert.show();
        }
    }

    /**
     * Método que comprueba si se ha escogido el día o no
     */
    public void tomarDia() {
        if (diaDtPicker.getValue() != null) {
            diaDtPicker.setPromptText(diaDtPicker.getValue().toString());
        }
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
                new TableColumn<>("Fecha");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("fechaCita"));


        TableColumn<Cliente, String> column2 =
                new TableColumn<>("DNI");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("dniCliente"));

        TableColumn<Cliente, String> column3 =
                new TableColumn<>("Descripción");

        column3.setCellValueFactory(
                new PropertyValueFactory<>("descripcion"));

        TableColumn<Cliente, String> column4 =
                new TableColumn<>("Hora de inicio");

        column4.setCellValueFactory(
                new PropertyValueFactory<>("horaInicio"));

        TableColumn<Cliente, String> column5 =
                new TableColumn<>("Hora de fin");

        column5.setCellValueFactory(
                new PropertyValueFactory<>("horaFin"));

        tablaCitasTableView.getColumns().add(column1);
        tablaCitasTableView.getColumns().add(column2);
        tablaCitasTableView.getColumns().add(column3);
        tablaCitasTableView.getColumns().add(column4);
        tablaCitasTableView.getColumns().add(column5);
        filtrarPorDia("");
    }
}
