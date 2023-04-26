package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class MainWindowController {
    @FXML
    private Label fechaLabel;

    @FXML
    public void cargarFecha(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
        String text = date.format(formatters);
        fechaLabel.setText(text);
    }

    @FXML
    public void prueba() throws IOException {
        App.changeScene("windows/nuevoclientewindow.fxml");
    }
}
