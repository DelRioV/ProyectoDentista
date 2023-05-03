package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    /**
     * Método que permite cambiar de escenas
     *
     * @throws IOException -> En caso de que haya un fallo a la hora de cargar los archivos
     */
    @FXML
    public void cambiarNuevoCliente() throws IOException {
        App.changeScene("windows/nuevoclientewindow.fxml", 490, 242);

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
    }
}
