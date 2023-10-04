package com.aerocopias.controledeartes.model;

import java.awt.*;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AroeiraModel {
    public AroeiraModel() {

    }

    public static void inserirDados(String artes, String link, String data, String status) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "INSERT INTO artesaroeira (artes, link, data, status) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, artes);
            statement.setString(2, link);
            statement.setString(3, data);
            statement.setString(4, status);

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

    public static void atualizarDados(int id, String artes, String link, String data, String status) throws SQLException {
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
                System.out.println("Arte "+ id +" atualizada com sucesso!");
                JOptionPane.showMessageDialog(null, "Arte "+ id +" atualizada com sucesso!");
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
    public static void deletarDados(int id) throws SQLException {
        Connection connection = ConectaDB.getConnection();
        String query = "DELETE FROM artesaroeira WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Arte "+ id +" deletada com sucesso!");
                JOptionPane.showMessageDialog(null, "Arte "+ id +" deletada com sucesso!");
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

    public static void AbrirWebLink(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
