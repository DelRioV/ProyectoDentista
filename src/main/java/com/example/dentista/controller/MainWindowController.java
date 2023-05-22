package com.example.dentista.controller;

import com.example.dentista.App;
import com.example.dentista.database.CitaTable;
import com.example.dentista.database.DataBaseConnection;
import com.example.dentista.model.Cita;
import com.example.dentista.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Clase que controla las acciones de mainwindow.fxml
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 26/04/2023
 */
public class MainWindowController implements Initializable {
    //Campos de la clase
    @FXML
    private Label fechaLabel;
    @FXML
    private TableView tablaCitasTableView;

    /**
     * Método que permite cambiar de escenas
     *
     * @throws IOException -> En caso de que haya un fallo a la hora de cargar los archivos
     */
    @FXML
    public void cambiarNuevoCliente() throws IOException {
        App.changeScene("windows/nuevoclientewindow.fxml", 490, 242);

    }

    @FXML
    public void cambiarModCliente() throws IOException {
        App.changeScene("windows/modificarclientewindow.fxml", 490, 280);

    }

    @FXML
    public void cambiarElCliente() throws IOException {
        App.changeScene("windows/eliminarclientewindow.fxml", 469, 130);

    }

    @FXML
    public void cambiarMostrarCliente() throws IOException {
        App.changeScene("windows/mostrarclientewindow.fxml", 600, 400);

    }

    @FXML
    public void cambiarNuevaCita() throws IOException {
        App.changeScene("windows/nuevacitawindow.fxml", 493, 297);

    }

    @FXML
    public void cambiarModCita() throws IOException {
        App.changeScene("windows/modificarcitawindow.fxml", 484, 329);

    }
    @FXML
    public void cambiarElimCita() throws IOException {
        App.changeScene("windows/eliminarcitawindow.fxml", 491, 227);

    }
    @FXML
    public void cambiarMostrarCita() throws IOException {
        App.changeScene("windows/mostrarcitawindow.fxml", 600, 338);

    }
    @FXML
    public void filtrarPorDia(String fecha) {
        ArrayList<Cita> citas = CitaTable.tomarCitas(new DataBaseConnection().getConnection(), fecha);
        for (int i = 0; i < citas.size(); i++) {
            tablaCitasTableView.getItems().add(citas.get(i));
        }
    }
    /**
     * Método que se ejecuta al realizar la carga del archivo mainwindow.fxml
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
        String text = date.format(formatters);
        fechaLabel.setText(text);
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
        filtrarPorDia(LocalDate.now().toString());
    }
}
