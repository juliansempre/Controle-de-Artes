package com.aerocopias.controledeartes.adm.configuracao.controller;

import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import com.aerocopias.controledeartes.model.ConectaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    public Label statusconnectlabel;
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

        verificarConexao(statusconnectlabel);
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
        //Resetar diretório

        int resposta = JOptionPane.showConfirmDialog(
                null, // Onde a janela será exibida (null para centralizar na tela)
                "Você gostaria de resetar o diretório?", // Mensagem da caixa de diálogo
                "Confirmação", // Título da janela
                JOptionPane.YES_NO_OPTION // Tipo de opções (YES_NO_OPTION: Sim e Não)
        );

        // Verifica a resposta
        if (resposta == JOptionPane.YES_OPTION) {
            System.out.println("Você escolheu SIM.");

            ConfigModel configModel = new ConfigModel(mainContainer);
            configModel.resetarDadosDiretorio();

        } else if (resposta == JOptionPane.NO_OPTION) {
            System.out.println("Você escolheu NÃO.");
            exibirDiretorioAtual();
        } else {
            System.out.println("A janela foi fechada sem uma escolha.");
            exibirDiretorioAtual();
        }



        exibirDiretorioAtual();
    }

    public void btnAtualizarDiretorio(ActionEvent actionEvent) throws SQLException {
        ConfigModel configModel = new ConfigModel(mainContainer);
        String diretorio = txtDiretorio.getText();
        if(diretorio.isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha o campo!");
        }else {
            int resposta = JOptionPane.showConfirmDialog(
                    null, // Onde a janela será exibida (null para centralizar na tela)
                    "Deseja atualizar o diretório?", // Mensagem da caixa de diálogo
                    "Confirmação", // Título da janela
                    JOptionPane.YES_NO_OPTION // Tipo de opções (YES_NO_OPTION: Sim e Não)
            );

            // Verifica a resposta
            if (resposta == JOptionPane.YES_OPTION) {
                System.out.println("Você escolheu SIM.");

                configModel.atualizarDadosDiretorioPadrao("Diretorio Atualizado", diretorio);
                exibirDiretorioAtual();

            } else if (resposta == JOptionPane.NO_OPTION) {
                System.out.println("Você escolheu NÃO.");
                exibirDiretorioAtual();
            } else {
                System.out.println("A janela foi fechada sem uma escolha.");
                exibirDiretorioAtual();
            }


        }

    }
public static ConfigController verificarConexao(Label statusconnectlabel){

    Connection connection = ConectaDB.getConnection();

    // Verifica se a conexão está ativa
    if (ConectaDB.isConnected(connection)) {

        statusconnectlabel.setText("CONECTADO");
    } else {

        statusconnectlabel.setText("DESCONECTADO");
    }
    //JOptionPane.showMessageDialog(null, statusconect);
    return null;
}


}
