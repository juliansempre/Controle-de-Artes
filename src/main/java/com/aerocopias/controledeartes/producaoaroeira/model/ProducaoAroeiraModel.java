package com.aerocopias.controledeartes.producaoaroeira.model;

import com.aerocopias.controledeartes.model.ConectaDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProducaoAroeiraModel {
    private int id;
    private int quantidade;
    private String descricao;
    private String medida;
    private String status;
    private String data;
    private String funcionario;
    private String dataPostagem;
    private BorderPane mainContainer;
    //Contrutor
    public ProducaoAroeiraModel(int id, int quantidade, String descricao, String medida, String status, String data, String funcionario, String dataPostagem, BorderPane mainContainer) {
        this.id = id;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.medida = medida;
        this.status = status;
        this.data = data;
        this.funcionario= funcionario;
        this.dataPostagem = dataPostagem;
        this.mainContainer = mainContainer;
    }
    //Getters e setters

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(String dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
    //Operação toString
    //COMO APARECERÁ NA TELA
    @Override
    public String toString() {
        return id + " ) | " + data + " | " + quantidade + " " + descricao + " " + medida +
                " " + status.toUpperCase() + " " + funcionario.toUpperCase();
//                dataPostagem;
    }
    //Operações de persistencia de dados

    public void inserirDadosTarefa(int quantidade, String descricao, String medida, String status, String data, String funcionario, Date dataInsercao) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "INSERT INTO producaoaroeira (quantidade, descricao, medida, status, data, funcionario, data_postagem) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, quantidade);
            statement.setString(2, descricao);
            statement.setString(3, medida);
            statement.setString(4, status);
            statement.setString(5, data);
            statement.setString(6, funcionario);
            statement.setDate(7, dataInsercao); // Certifique-se de fornecer o valor para data_insercao

            statement.executeUpdate();
            System.out.println("Tarefa inserida com sucesso!");
            JOptionPane.showMessageDialog(null, "Tarefa inserida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados na classe ProducaoAroeiraModel: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir Tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }

    public void atualizarDadosTarefa(int id, int quantidade, String descricao, String medida, String status, String data, String funcionario, Date dataInsercao) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "UPDATE producaoaroeira SET quantidade = ?, descricao = ?, medida = ?, status = ?, data = ?, funcionario = ?, data_postagem = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, quantidade);
            statement.setString(2, descricao);
            statement.setString(3, medida);
            statement.setString(4, status);
            statement.setString(5, data);
            statement.setString(6, funcionario);
            statement.setDate(7, dataInsercao);
            statement.setInt(8, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tarefa atualizada com sucesso!");
                JOptionPane.showMessageDialog(null, "Tarefa atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa encontrada com o ID fornecido.");
                JOptionPane.showMessageDialog(null, "Nenhuma tarefa encontrada com o ID fornecido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados na classe ProducaoAroeiraModel: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }


    public void excluirTarefa(int id) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "DELETE FROM producaoaroeira WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Tarefa excluída com sucesso!");
                JOptionPane.showMessageDialog(null, "Tarefa excluída com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa excluída.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tarefa na classe ProducaoAroeiraModel: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao excluir Tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConectaDB.closeConnection(connection);
        }
    }


    //Operação Exibir tela
    public void producaoAroeiraTela() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/producao-aroeira.fxml"));
        Parent tela1 = loader.load();

        // Criar nova cena para a Tela1
        Scene tela1Scene = new Scene(tela1, 800, 600);

        // Configurar o Stage da Tela1
        Stage stage = new Stage();
        stage.setTitle("Produção Aroeira");
        stage.setScene(tela1Scene);
        stage.setMaximized(true);
        // Esconder a janela principal
        Stage stagemain = (Stage) mainContainer.getScene().getWindow();
        stagemain.hide();

        // Mostrar a Tela1
        stage.show();
    }
}