package com.aerocopias.controledeartes.autentificacao.cadastro.model;

import com.aerocopias.controledeartes.autentificacao.cadastro.controller.CadastroUsuarioController;
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

public class CadastroUsuarioModel {
    //Atributos
    private String id;
    private String usuario;
    private String senha;
    private BorderPane mainContainer;
    //Contrutor
    public CadastroUsuarioModel(BorderPane mainContainer) {
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
    public void inserirDadosUsuario(String usuario, String senha) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "insert into usuarios (usuario, senha) values (?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, senha);

            statement.executeUpdate();
            System.out.println("Dados inseridos com sucesso!");
            JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");


        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao inserir dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }

    public static void atualizarDadosUsuario(int id, String artes, String link, String data, String status) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "UPDATE artesaroeira SET artes = ?, link = ?, data = ?, status = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, artes);
            statement.setString(2, link);
            statement.setString(3, data);
            statement.setString(4, status);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Arte atualizada com sucesso!");
                JOptionPane.showMessageDialog(null, "Arte atualizada com sucesso!");
            } else {
                System.out.println("Nenhum dado foi atualizado.");
                JOptionPane.showMessageDialog(null, "Nenhum dado foi atualizado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }
    public static void deletarDadosUsuario(int id) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "DELETE FROM artesaroeira WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Arte deletada com sucesso!");
                JOptionPane.showMessageDialog(null, "Arte deletada com sucesso!");
            } else {
                System.out.println("Nenhum dado foi deletado.");
                JOptionPane.showMessageDialog(null, "Nenhum dado foi deletado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar dados: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }

    //Operação chamar a tela de cadastro
    public void cadastro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/cadastro-usuario.fxml"));
        Parent tela1 = loader.load();

        // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 800, 600);

        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("cadastro");
        stage.setScene(tela1Scene);
        stage.setResizable(false); //impedir a maximização
        // Esconder a janela principal
        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
        stagemain.hide();

        // Mostrar a Tela1
        stage.show();
    }
}