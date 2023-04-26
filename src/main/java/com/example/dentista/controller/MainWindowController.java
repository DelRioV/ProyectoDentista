package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private Label fechaLabel;

    @FXML
    public void prueba() throws IOException {
        App.changeScene("windows/nuevoclientewindow.fxml");
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
        String text = date.format(formatters);
        fechaLabel.setText(text);
    }
}
