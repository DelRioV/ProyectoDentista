package com.example.dentista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que contiene las acciones básicas del proyecto en javafx
 *
 * @author: Pablo Salvadro Del Río Vergara
 * @version: 26/04/2023
 */
public class App extends Application {

    //Campos de la clase
    private static Stage stage;

    /**
     * Clase que llama el método "Main" para empezar el programa
     *
     * @param stage
     * @throws IOException -> En caso de que haya un fallo a la hora de cargar los archivos
     */
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

    /**
     * Clase que tiene como función cambiar la escena que se muestra
     *
     * @param fxml - La ruta donde se encuentra el archivo a cargar
     * @throws IOException -> En caso de que haya un fallo a la hora de cargar los archivos
     */
    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(
                App.class.getResource(fxml));

        stage.getScene().setRoot(pane);
    }

    /**
     * Primer método que ejecuta el inicio
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}