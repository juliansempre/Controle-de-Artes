package com.aerocopias.controledeartes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaDB {
    private static final String URL = "jdbc:mysql://192.168.0.206:3306/controledeartes";
    //private static final String URL = "jdbc:mysql://localhost/controledeartes";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        // Aqui você pode realizar operações no banco de dados usando a conexão
        // ...
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexão fechada!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
        System.out.println("Conexão fechada!");
    }
}
