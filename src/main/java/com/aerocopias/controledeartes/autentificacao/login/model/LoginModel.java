package com.aerocopias.controledeartes.autentificacao.login.model;


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
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    //Atributos
    private String id;
    private String usuario;
    private String senha;
    private BorderPane mainContainer;
    //Contrutor
    public LoginModel(BorderPane mainContainer) {
        this.mainContainer = mainContainer;
    }
    //Operação get e set

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    //Operação

    //Operação de persistencia

    public boolean autenticaUsuario(LoginModel login) {
        boolean autenticado = false;
        Connection connection = ConectaDB.getConnection();
        if (connection != null) {
            try {
                // Prepara uma consulta SQL para verificar se o usuário e a senha existem na tabela usuarios
                String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, login.getUsuario());
                statement.setString(2, login.getSenha());
                ResultSet result = statement.executeQuery();
                // Se o resultado não for vazio, significa que o usuário foi autenticado
                if (result.next()) {
                    autenticado = true;
                }
                // Fecha o resultado, o statement e a conexão
                result.close();
                statement.close();
                ConectaDB.closeConnection(connection);
            } catch (SQLException e) {
                System.out.println("Erro ao autenticar o usuário: " + e.getMessage());
            }
        }
        return autenticado;
    }

    //Operação chamar a tela de login
    public void login() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/login.fxml"));
        Parent tela1 = loader.load();

        // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 800, 600);

        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(tela1Scene);
        stage.setResizable(false); //impedir a maximização
        // Esconder a janela principal
        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
        stagemain.hide();

        // Mostrar a Tela1
        stage.show();
    }
}