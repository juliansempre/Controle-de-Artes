package com.aerocopias.controledeartes.controller;

import com.aerocopias.controledeartes.model.AroeiraModel;
import com.aerocopias.controledeartes.model.ConectaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtArte;
    @FXML
    private TextField txtLink;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtStatus;
    @FXML
    private GridPane container;
    @FXML
    private TextField txtBuscarei;
    @FXML
    private TableView<String[]> tableView;
    @FXML
    private TableColumn<String[], String> colNome;
    @FXML
    private TableColumn<String[], String> colLink;
    @FXML
    private TableColumn<String[], String> colData;
    @FXML
    private TableColumn<String[], String> colStatus;
    private boolean pararFuncao = false;




    @FXML
    protected void onHelloButtonClick() {
        limparFormulario();
    }
    @FXML
    public void btnSalvarClick(ActionEvent actionEvent) {
        enviarInserir();
        limparFormulario();
        container.getChildren().clear();
        listar();

    }
    @FXML
    public void btnAtualizarClick(ActionEvent actionEvent) {

        enviarAtualizar();
        limparFormulario();
        container.getChildren().clear();
        listar();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      listar();
    }
    public void listar(){
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

        try {
            Connection connection = ConectaDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM artesaroeira");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("artes");
                String link = resultSet.getString("link");
                String data = resultSet.getString("data");
                String status = resultSet.getString("status");

                String[] artesaroeira = {id, nome, link, data, status};
                listaArtesaroeira.add(artesaroeira);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Exibir os dados da lista
        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            // System.out.println(a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4]);

            // Criar novos Labels para cada item da lista
            Label label1 = new Label(a[0]);
            Label label2 = new Label(a[1]);
            Label label3 = new Label(a[2]);
            Label label4 = new Label(a[3]);
            Label label5 = new Label(a[4]);

            // Adicionar os Labels ao GridPane
            container.add(label1, col, row);
            container.add(label2, col + 1, row);
           // container.add(label3, col + 2, row);
            container.add(label4, col + 3, row);
            container.add(label5, col + 4, row);

            // Adicionar o botão "link" com evento de ação
            Button buttonlink = new Button("Abrir link do email");
            buttonlink.setUserData(a[0]); // Armazenar o ID na propriedade userData do botão

            buttonlink.setOnAction(event -> {
                String linkAtual = a[2]; // Obter o valor de a[2]
                AroeiraModel.AbrirWebLink(linkAtual);

            });
            container.add(buttonlink, col + 2, row);

            // Adicionar o botão "alterar" com evento de ação
            Button button = new Button("Alterar");
            button.setUserData(a[0]); // Armazenar o ID na propriedade userData do botão

            button.setOnAction(event -> {
                String idAtual = (String) button.getUserData(); // Obter o ID atual do botão clicado
                String arteAtual = a[1]; // Obter o valor de a[1]
                String linkAtual = a[2]; // Obter o valor de a[2]
                String dataAtual = a[3]; // Obter o valor de a[3]
                String statusAtual = a[4]; // Obter o valor de a[4]
                System.out.println("Botão 'Alterar' clicado! ID: " + idAtual + ", Arte: " + arteAtual + ", Link: " + linkAtual + ", Data: " + dataAtual + ", Status: " + statusAtual);

                txtId.setText(idAtual);
                txtArte.setText(arteAtual);
                txtLink.setText(linkAtual);
                txtData.setText(dataAtual);
                txtStatus.setText(statusAtual);

            });
            container.add(button, col + 5, row);

            // Adicionar o botão "deletar" com evento de ação
            Button button2 = new Button("deletar");
            button2.setUserData(a[0]); // Armazenar o ID na propriedade userData do botão

            button2.setOnAction(event -> {
                String idAtual = (String) button2.getUserData(); // Obter o ID atual do botão clicado

            try {
            AroeiraModel aroeiraModel = new AroeiraModel();
            aroeiraModel.deletarDados(Integer.parseInt(idAtual));

            //limpar lista e mostrar novamente
                container.getChildren().clear();
                listar();

            } catch (SQLException e) {
            // Tratar a exceção aqui
            }

            });
            container.add(button2, col + 6, row);

            // Atualizar as posições de linha e coluna
            //row++;

            // Atualizar as posições de linha e coluna
            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
        //parar função:
        if (pararFuncao) {
            return; // Encerrar a função
        }
    }
    public void enviarInserir(){
        String nome = txtArte.getText();
        String link = txtLink.getText();
        String data = txtData.getText();
        String status = txtStatus.getText();

        System.out.println(nome + " " + link + " " + data + " " + status);

        AroeiraController aroeiraController = new AroeiraController();

        aroeiraController.setArtes(nome);
        aroeiraController.setLink(link);
        aroeiraController.setData(data);
        aroeiraController.setStatus(status);

        AroeiraModel aroeiraModel = new AroeiraModel();
        try {
            aroeiraModel.inserirDados(aroeiraController.getArtes(), aroeiraController.getLink(), aroeiraController.getData(), aroeiraController.getStatus());
        } catch (SQLException e) {
            // Tratar a exceção aqui
            e.printStackTrace();
        }


    }
//buscar aqui

    public void buscarPorId(String id) {
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

        try {
            Connection connection = ConectaDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM artesaroeira WHERE id = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("artes");
                String link = resultSet.getString("link");
                String data = resultSet.getString("data");
                String status = resultSet.getString("status");

                String[] artesaroeira = {id, nome, link, data, status};
                listaArtesaroeira.add(artesaroeira);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            Label label1 = new Label(a[0]);
            Label label2 = new Label(a[1]);
            Label label3 = new Label(a[2]);
            Label label4 = new Label(a[3]);
            Label label5 = new Label(a[4]);

            container.add(label1, col, row);
            container.add(label2, col + 1, row);
            container.add(label4, col + 3, row);
            container.add(label5, col + 4, row);

            Button buttonlink = new Button("Abrir link do email");
            buttonlink.setUserData(a[0]);

            buttonlink.setOnAction(event -> {
                String linkAtual = a[2];
                AroeiraModel.AbrirWebLink(linkAtual);
            });
            container.add(buttonlink, col + 2, row);

            Button button = new Button("Alterar");
            button.setUserData(a[0]);

            button.setOnAction(event -> {
                String idAtual = (String) button.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];
                String statusAtual = a[4];

                txtId.setText(idAtual);
                txtArte.setText(arteAtual);
                txtLink.setText(linkAtual);
                txtData.setText(dataAtual);
                txtStatus.setText(statusAtual);
            });
            container.add(button, col + 5, row);

            Button button2 = new Button("Deletar");
            button2.setUserData(a[0]);

            button2.setOnAction(event -> {
                String idAtual = (String) button2.getUserData();

                try {
                    AroeiraModel aroeiraModel = new AroeiraModel();
                    aroeiraModel.deletarDados(Integer.parseInt(idAtual));

                    container.getChildren().clear();
                    listar();
                } catch (SQLException e) {
                    // Tratar a exceção aqui
                }
            });
            container.add(button2, col + 6, row);

            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
    }

    //fim buscar

    public void enviarAtualizar(){
        String id = txtId.getText();
        String nome = txtArte.getText();
        String link = txtLink.getText();
        String data = txtData.getText();
        String status = txtStatus.getText();


        AroeiraController aroeiraController = new AroeiraController();

        aroeiraController.setId(id);
        aroeiraController.setArtes(nome);
        aroeiraController.setLink(link);
        aroeiraController.setData(data);
        aroeiraController.setStatus(status);

        AroeiraModel aroeiraModel = new AroeiraModel();
        try {

            aroeiraModel.atualizarDados(aroeiraController.getId(), aroeiraController.getArtes(), aroeiraController.getLink(), aroeiraController.getData(), aroeiraController.getStatus());
        } catch (SQLException e) {
            // Tratar a exceção aqui
            e.printStackTrace();
        }


    }
    public void limparFormulario(){
        txtId.setText("");
        txtArte.setText("");
        txtLink.setText("");
        txtData.setText("");
        txtStatus.setText("");

        txtBuscarei.setText("");

        container.getChildren().clear();
        listar();
    }



    public void OptionPanedoJavaFX(String texto1, String texto2){
        // Exibir uma caixa de diálogo de mensagem
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(texto1);
        alert.setContentText(texto2);
        alert.showAndWait();
    }


    public void btnBuscarei(ActionEvent actionEvent) {
        String numStr = txtBuscarei.getText();

        if (!isNumeric(numStr)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Digite um número, por favor.");
            alert.showAndWait();
            return;
        }

        int num = Integer.parseInt(numStr);
        container.getChildren().clear();
        buscarPorId(String.valueOf(num));
    }
    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
