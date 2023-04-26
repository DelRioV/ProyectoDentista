package com.example.dentista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("windows/mainwindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 400);
        stage.setTitle("Clínica Dental - Del Río");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("static/img/logo_dent.jpg")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }
    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(
                App.class.getResource(fxml));

        stage.getScene().setRoot(pane);
    }
    public static void main(String[] args) {
        launch();
    }
}