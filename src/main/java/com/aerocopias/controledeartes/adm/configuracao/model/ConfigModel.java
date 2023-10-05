package com.aerocopias.controledeartes.adm.configuracao.model;
import com.aerocopias.controledeartes.model.ConectaDB;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfigModel {
    private int id;
    private String nomediretorio;
    private String linkdiretorio;
    private BorderPane mainContainer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomediretorio() {
        return nomediretorio;
    }

    public void setNomediretorio(String nomediretorio) {
        this.nomediretorio = nomediretorio;
    }

    public String getLinkdiretorio() {
        return linkdiretorio;
    }

    public void setLinkdiretorio(String linkdiretorio) {
        this.linkdiretorio = linkdiretorio;
    }

    //Contrutor
    public ConfigModel(BorderPane mainContainer) {
        this.mainContainer = mainContainer;
    }
    //Operação persistencia
    public void inserirDadosDiretorio(String nomediretorio, String linkdiretorio) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "INSERT INTO explorar (nomediretorio, linkdiretorio) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nomediretorio);
            statement.setString(2, linkdiretorio);

            statement.executeUpdate();
            System.out.println("Diretório inserido com sucesso!");
            JOptionPane.showMessageDialog(null, "Diretório inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados na classe ProducaoAroeiraModel: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir Diretório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }


    //Operação tela
    public void config() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/config-view.fxml"));
        Parent tela1 = loader.load();

        // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 600, 400);

        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("Configurações");
        stage.setScene(tela1Scene);

        // Esconder a janela principal
//        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
//        stagemain.hide();

        // Mostrar a Tela1
        stage.show();
    }
}