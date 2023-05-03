package com.example.dentista.controller;

import com.example.dentista.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class NuevoClienteController {

    @FXML
    public void volverMain() throws IOException {
        App.changeScene("windows/mainwindow.fxml", 620, 400);
    }
}
