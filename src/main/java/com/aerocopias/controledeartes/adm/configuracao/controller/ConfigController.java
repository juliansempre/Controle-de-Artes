package com.aerocopias.controledeartes.adm.configuracao.controller;

import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    private Label textoTela;

    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas
    @FXML
    private TextField txtAtualDiretorio;

    @FXML
    private TextField txtDiretorio;

    //Contrutor
    public ConfigController(){
        System.out.println("Tela 1");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtAtualDiretorio.setText("DIdidididi");
    }
    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
      System.out.println("Funciona");
    }

    public void btnSalvarDiretorio(ActionEvent actionEvent) throws SQLException {
        ConfigModel configModel = new ConfigModel(mainContainer);
        configModel.inserirDadosDiretorio("teste", "Linkteste");
    }

    public void btnAtualizarDiretorio(ActionEvent actionEvent) {
    }


}
