package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class NuevaCitaController {

    @FXML
    public void prueba() throws IOException {
        App.changeScene("windows/mainwindow.fxml");
    }
}
