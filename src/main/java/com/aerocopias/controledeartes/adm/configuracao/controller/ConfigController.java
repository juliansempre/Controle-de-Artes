package com.aerocopias.controledeartes.adm.configuracao.controller;

import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import com.aerocopias.controledeartes.controller.HelloController;
import com.aerocopias.controledeartes.painel.model.PainelModel;
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

import javax.swing.*;
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
    //Iniciar com o nome atual do diretorio
        exibirDiretorioAtual();
    }
    public void exibirDiretorioAtual(){
        //Iniciar com o nome atual do diretorio
        ConfigModel configModel = new ConfigModel(mainContainer);
        //configModel.inserirDadosDiretorio("teste", "Linkteste");
        try {
            configModel.listarDadosDiretorio();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String nome = configModel.getLinkdiretorio();
        txtAtualDiretorio.setText(nome);
    }
    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
      System.out.println("Funciona");
    }

    public void btnSalvarDiretorio(ActionEvent actionEvent) throws SQLException {

        ConfigModel configModel = new ConfigModel(mainContainer);
        configModel.resetarDadosDiretorio();
        exibirDiretorioAtual();
    }

    public void btnAtualizarDiretorio(ActionEvent actionEvent) throws SQLException {
        ConfigModel configModel = new ConfigModel(mainContainer);
        String diretorio = txtDiretorio.getText();
        if(diretorio.isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha o campo!");
        }else {
            configModel.atualizarDadosDiretorioPadrao("Diretorio Atualizado", diretorio);
            exibirDiretorioAtual();
        }

    }

}
