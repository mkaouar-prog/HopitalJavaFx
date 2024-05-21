package com.projet.miniprojetfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 515, 410);
        //Scene scene = new Scene(fxmlLoader.load(), 750, 550);
        stage.setTitle("Gestion de hopital");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}