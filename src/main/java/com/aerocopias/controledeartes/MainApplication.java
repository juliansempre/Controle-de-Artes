package com.aerocopias.controledeartes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 950, 700);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Aero Cópias");
        Image icon = new Image(getClass().getResourceAsStream("/img/logo.png"));
        stage.getIcons().add(icon);
        //stage.setMaximized(true);
        stage.setResizable(false); //impedir a maximização
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}