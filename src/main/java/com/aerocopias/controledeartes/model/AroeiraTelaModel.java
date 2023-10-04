package com.aerocopias.controledeartes.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AroeiraTelaModel {
    private BorderPane mainContainer;
    //Contrutor
    public AroeiraTelaModel(BorderPane mainContainer) {
        this.mainContainer = mainContainer;
    }
    //Operação
    public void aroeira() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/arte-aroeira.fxml"));
        Parent tela1 = loader.load();

        // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 320, 240);

        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("Artes da Aroeira");
        stage.setScene(tela1Scene);

        // Esconder a janela principal
        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
        stagemain.hide();

        // Mostrar a Tela1
        stage.setMaximized(true);
        stage.show();
    }
}