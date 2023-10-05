package com.aerocopias.controledeartes.adm.configuracao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigController {
    @FXML
    private Label textoTela;

    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas

    //Contrutor
    public ConfigController(){
        System.out.println("Tela 1");
    }
    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
      System.out.println("Funciona");
    }
}
