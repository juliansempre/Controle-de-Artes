package com.aerocopias.controledeartes.painel.model;


import com.aerocopias.controledeartes.autentificacao.login.controller.LoginController;
import com.aerocopias.controledeartes.painel.controller.PainelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class PainelModel {

    private String nome;
    private BorderPane mainContainer;
    //Contrutor
    public PainelModel(BorderPane mainContainer) {

        this.mainContainer = mainContainer;
    }
    //Operação Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    //Operação
    public void painel() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/painel.fxml"));
        Parent tela1 = loader.load();

         // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 800, 600);


        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("Painel");
        stage.setScene(tela1Scene);
        stage.setResizable(false); //impedir a maximização
        // Esconder a janela principal
        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
        stagemain.hide();

        //Exibir nome

        // Mostrar a Tela1
        stage.show();
    }
}