package com.aerocopias.controledeartes.controller;

import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import com.aerocopias.controledeartes.autentificacao.login.controller.SessãoController;
import com.aerocopias.controledeartes.autentificacao.login.model.LoginModel;
import com.aerocopias.controledeartes.painel.model.PainelModel;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.aerocopias.controledeartes.model.AroeiraModel;
import com.aerocopias.controledeartes.model.ConectaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import javax.swing.*;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas
    @FXML
    private Label labelNomeDoFuncionario;
    @FXML
    private Button painelx;
    @FXML
    private DatePicker dtData;
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

        container.getChildren().clear();
        listar();

    }
    @FXML
    public void btnAtualizarClick(ActionEvent actionEvent) {

        enviarAtualizar();

        container.getChildren().clear();
        listar();

    }

    public void btnlistarAlteracoes(ActionEvent actionEvent) {

        container.getChildren().clear();
        listarAlteracoes();
    }

    public void btnlistarprontos(ActionEvent actionEvent) {
        container.getChildren().clear();
        listarProntos();
    }
    public void btnlistarAprovados(ActionEvent actionEvent) {
        container.getChildren().clear();
        listarAprovados();
    }
    public void btnlistarParaFazer(ActionEvent actionEvent) {
        limparFormulario();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      listar();
      datahoje();
            //sessão
            String nomeUsuario = SessãoController.getNomeUsuarioAutenticado();
            if (nomeUsuario != null) {
               labelNomeDoFuncionario.setText("" + nomeUsuario.toUpperCase() + "");
            }

    }
    public void datahoje(){
        // Obtém a data atual
        Date dataAtual = new Date();

        // Define o formato desejado para a data (PT-BR)
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

        // Formata a data para o padrão PT-BR
        String dataFormatada = formato.format(dataAtual);

        // Imprime a data formatada
        System.out.println("Data atual no formato PT-BR: " + dataFormatada);
        txtData.setText(dataFormatada);
    }
    public void listar(){
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();
        ArrayList<String[]> listaAlteracao = new ArrayList<>();
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
                //lista Alteração
                if (status.equalsIgnoreCase("ALTERAÇÃO")) {
                    listaAlteracao.add(artesaroeira);
                    listaArtesaroeira.remove(artesaroeira);
                }
                if (status.equalsIgnoreCase("PRONTO")) {
                    listaArtesaroeira.remove(artesaroeira);
                }
                if (status.equalsIgnoreCase("APROVADO")) {
                    listaArtesaroeira.remove(artesaroeira);
                }

            }

            //lista Alteração



            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listaArtesaroeira.addAll(0, listaAlteracao);
        // Exibir os dados da lista
        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            // System.out.println(a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4]);

            // Criar novos Labels para cada item da lista
            Label label1 = new Label( " ( "+ a[0] + " ) ");
            Label label2 = new Label(a[1]);
            Label label3 = new Label(a[2]);
            Label label4 = new Label(a[3]);
            Label label5 = new Label(a[4].toUpperCase());

            //Mudar de cor
            if(a[4].equalsIgnoreCase("Para fazer")){
                label5.setTextFill(javafx.scene.paint.Color.BLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Alteração")){
                label5.setTextFill(javafx.scene.paint.Color.RED);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Pronto")){
                label5.setTextFill(javafx.scene.paint.Color.GREEN);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Aprovado")){
                label5.setTextFill(javafx.scene.paint.Color.DARKBLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }

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


            Button buttonAlt = new Button("Alteração");
            buttonAlt.setUserData(a[0]);
            buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

            buttonAlt.setOnAction(event -> {
                String idAtual = (String) buttonAlt.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
            });
            container.add(buttonAlt, col + 7, row);

            Button buttonAp = new Button("Aprovado");
            buttonAp.setUserData(a[0]);
            buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonAp.setOnAction(event -> {
                String idAtual = (String) buttonAp.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
            });
            container.add(buttonAp, col + 8, row);

            Button buttonPr = new Button("Pronto");
            buttonPr.setUserData(a[0]);
            buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            buttonPr.setOnAction(event -> {
                String idAtual = (String) buttonPr.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
            });
            container.add(buttonPr, col + 9, row);

            Button buttonpf = new Button("Para fazer");
            buttonpf.setUserData(a[0]);
            buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonpf.setOnAction(event -> {
                String idAtual = (String) buttonpf.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
            });
            container.add(buttonpf, col + 10, row);

            // Adicionar o botão "editar" com evento de ação
            Button button = new Button("Editar");
            button.setUserData(a[0]); // Armazenar o ID na propriedade userData do botão

            button.setOnAction(event -> {
                String idAtual = (String) button.getUserData(); // Obter o ID atual do botão clicado
                String arteAtual = a[1]; // Obter o valor de a[1]
                String linkAtual = a[2]; // Obter o valor de a[2]
                String dataAtual = a[3]; // Obter o valor de a[3]
                String statusAtual = a[4]; // Obter o valor de a[4]
                System.out.println("Botão 'Editar' clicado! ID: " + idAtual + ", Arte: " + arteAtual + ", Link: " + linkAtual + ", Data: " + dataAtual + ", Status: " + statusAtual);

                txtId.setText(idAtual);
                txtArte.setText(arteAtual);
                txtLink.setText(linkAtual);
                txtData.setText(dataAtual);
                txtStatus.setText(statusAtual);

            });
            container.add(button, col + 5, row);
/*
            // Adicionar o botão "abrir" com evento de ação
            Button buttonAbr = new Button("Abrir");
            buttonAbr.setUserData(a[0]); // Armazenar o ID na propriedade userData do botão

            buttonAbr.setOnAction(event -> {
                JOptionPane.showMessageDialog(null,"aberto");
            });
            container.add(buttonAbr, col + 8, row);
*/
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
            //Desativando botão deletar
           // container.add(button2, col + 6, row);

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
            //Validação
           if(nome.isEmpty() || link.isEmpty() || data.isEmpty() || status.isEmpty()){
               JOptionPane.showMessageDialog(null, "Preencha os campos!");
           }else {
               if(data.matches("\\d{2}/\\d{2}/\\d{4}") ){
                   if (status.equalsIgnoreCase("APROVADO") || status.equalsIgnoreCase("ALTERAÇÃO") || status.equalsIgnoreCase("PARA FAZER") || status.equalsIgnoreCase("PRONTO")){
                       aroeiraModel.inserirDados(aroeiraController.getArtes(), aroeiraController.getLink(), aroeiraController.getData(), aroeiraController.getStatus());
                       limparFormulario();
                   }
                   else {
                       JOptionPane.showMessageDialog(null, "Digite: PARA FAZER, APROVADO, ALTERAÇAO OU PRONTO!");
                   }
               }else {
                   JOptionPane.showMessageDialog(null, "O Formato da data está errado\n O correto é: 00/00/0000");
               }
           }
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
            Label label1 = new Label( " ( "+ a[0] + " ) ");
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

            Button button = new Button("Editar");
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



            Button buttonAlt = new Button("Alteração");
            buttonAlt.setUserData(a[0]);
            buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

            buttonAlt.setOnAction(event -> {
                String idAtual = (String) buttonAlt.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
            });
            container.add(buttonAlt, col + 7, row);

            Button buttonAp = new Button("Aprovado");
            buttonAp.setUserData(a[0]);
            buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonAp.setOnAction(event -> {
                String idAtual = (String) buttonAp.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
            });
            container.add(buttonAp, col + 8, row);

            Button buttonPr = new Button("Pronto");
            buttonPr.setUserData(a[0]);
            buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            buttonPr.setOnAction(event -> {
                String idAtual = (String) buttonPr.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
            });
            container.add(buttonPr, col + 9, row);

            Button buttonpf = new Button("Para fazer");
            buttonpf.setUserData(a[0]);
            buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonpf.setOnAction(event -> {
                String idAtual = (String) buttonpf.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
            });
            container.add(buttonpf, col + 10, row);


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
            //Desativando botão deletar
          //  container.add(button2, col + 6, row);

            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
    }

    //fim buscar
//select where

    public void listarAlteracoes() {
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

        try {
            Connection connection = ConectaDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM artesaroeira WHERE status = ?");
            statement.setString(1, "ALTERAÇÃO");
            ResultSet resultSet = statement.executeQuery();

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

        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            Label label1 = new Label( " ( "+ a[0] + " ) ");
            Label label2 = new Label(a[1]);
            Label label3 = new Label(a[2]);
            Label label4 = new Label(a[3]);
            Label label5 = new Label(a[4]);

            //Mudar de cor
            if(a[4].equalsIgnoreCase("Para fazer")){
                label5.setTextFill(javafx.scene.paint.Color.BLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Alteração")){
                label5.setTextFill(javafx.scene.paint.Color.RED);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Pronto")){
                label5.setTextFill(javafx.scene.paint.Color.GREEN);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Aprovado")){
                label5.setTextFill(javafx.scene.paint.Color.DARKBLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }

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

            Button button = new Button("Editar");
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


            Button buttonAlt = new Button("Alteração");
            buttonAlt.setUserData(a[0]);
            buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

            buttonAlt.setOnAction(event -> {
                String idAtual = (String) buttonAlt.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
            });
            container.add(buttonAlt, col + 7, row);

            Button buttonAp = new Button("Aprovado");
            buttonAp.setUserData(a[0]);
            buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonAp.setOnAction(event -> {
                String idAtual = (String) buttonAp.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
            });
            container.add(buttonAp, col + 8, row);

            Button buttonPr = new Button("Pronto");
            buttonPr.setUserData(a[0]);
            buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            buttonPr.setOnAction(event -> {
                String idAtual = (String) buttonPr.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
            });
            container.add(buttonPr, col + 9, row);

            Button buttonpf = new Button("Para fazer");
            buttonpf.setUserData(a[0]);
            buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonpf.setOnAction(event -> {
                String idAtual = (String) buttonpf.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
            });
            container.add(buttonpf, col + 10, row);


            Button button2 = new Button("Deletar");
            button2.setUserData(a[0]);

            button2.setOnAction(event -> {
                String idAtual = (String) button2.getUserData();

                try {
                    AroeiraModel aroeiraModel = new AroeiraModel();
                    aroeiraModel.deletarDados(Integer.parseInt(idAtual));

                    container.getChildren().clear();
                    listarAlteracoes();
                } catch (SQLException e) {
                    // Tratar a exceção aqui
                }
            });
            //Desativando botão deletar
            //container.add(button2, col + 6, row);

            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
    }
// select where pronto
public void listarProntos() {
    ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

    try {
        Connection connection = ConectaDB.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM artesaroeira WHERE status = ?");
        statement.setString(1, "PRONTO");
        ResultSet resultSet = statement.executeQuery();

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

    int row = 0;
    int col = 0;
    for (String[] a : listaArtesaroeira) {
        Label label1 = new Label( " ( "+ a[0] + " ) ");
        Label label2 = new Label(a[1]);
        Label label3 = new Label(a[2]);
        Label label4 = new Label(a[3]);
        Label label5 = new Label(a[4]);

        //Mudar de cor
        if(a[4].equalsIgnoreCase("Para fazer")){
            label5.setTextFill(javafx.scene.paint.Color.BLUE);
            label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        }
        else if(a[4].equalsIgnoreCase("Alteração")){
            label5.setTextFill(javafx.scene.paint.Color.RED);
            label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        }
        else if(a[4].equalsIgnoreCase("Pronto")){
            label5.setTextFill(javafx.scene.paint.Color.GREEN);
            label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        }
        else if(a[4].equalsIgnoreCase("Aprovado")){
            label5.setTextFill(javafx.scene.paint.Color.DARKBLUE);
            label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        }


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

        Button button = new Button("Editar");
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


        Button buttonAlt = new Button("Alteração");
        buttonAlt.setUserData(a[0]);
        buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

        buttonAlt.setOnAction(event -> {
            String idAtual = (String) buttonAlt.getUserData();
            String arteAtual = a[1];
            String linkAtual = a[2];
            String dataAtual = a[3];

            enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
        });
        container.add(buttonAlt, col + 7, row);

        Button buttonAp = new Button("Aprovado");
        buttonAp.setUserData(a[0]);
        buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        buttonAp.setOnAction(event -> {
            String idAtual = (String) buttonAp.getUserData();
            String arteAtual = a[1];
            String linkAtual = a[2];
            String dataAtual = a[3];

            enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
        });
        container.add(buttonAp, col + 8, row);

        Button buttonPr = new Button("Pronto");
        buttonPr.setUserData(a[0]);
        buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        buttonPr.setOnAction(event -> {
            String idAtual = (String) buttonPr.getUserData();
            String arteAtual = a[1];
            String linkAtual = a[2];
            String dataAtual = a[3];

            enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
        });
        container.add(buttonPr, col + 9, row);

        Button buttonpf = new Button("Para fazer");
        buttonpf.setUserData(a[0]);
        buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        buttonpf.setOnAction(event -> {
            String idAtual = (String) buttonpf.getUserData();
            String arteAtual = a[1];
            String linkAtual = a[2];
            String dataAtual = a[3];

            enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
        });
        container.add(buttonpf, col + 10, row);


        Button button2 = new Button("Deletar");
        button2.setUserData(a[0]);

        button2.setOnAction(event -> {
            String idAtual = (String) button2.getUserData();

            try {
                AroeiraModel aroeiraModel = new AroeiraModel();
                aroeiraModel.deletarDados(Integer.parseInt(idAtual));

                container.getChildren().clear();
                listarAlteracoes();
            } catch (SQLException e) {
                // Tratar a exceção aqui
            }
        });
        //Desativando botão deletar
        //container.add(button2, col + 6, row);

        if (col + 7 >= 7) {
            col = 0;
            row++;
        } else {
            col += 7;
        }
    }
}
//Aprovados

    public void listarAprovados() {
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

        try {
            Connection connection = ConectaDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM artesaroeira WHERE status = ?");
            statement.setString(1, "APROVADO");
            ResultSet resultSet = statement.executeQuery();

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

        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            Label label1 = new Label( " ( "+ a[0] + " ) ");
            Label label2 = new Label(a[1]);
            Label label3 = new Label(a[2]);
            Label label4 = new Label(a[3]);
            Label label5 = new Label(a[4]);

            //Mudar de cor
            if(a[4].equalsIgnoreCase("Para fazer")){
                label5.setTextFill(javafx.scene.paint.Color.BLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Alteração")){
                label5.setTextFill(javafx.scene.paint.Color.RED);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("Pronto")){
                label5.setTextFill(javafx.scene.paint.Color.GREEN);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
            else if(a[4].equalsIgnoreCase("aprovado")){
                label5.setTextFill(javafx.scene.paint.Color.DARKBLUE);
                label5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }


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

            Button button = new Button("Editar");
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


            Button buttonAlt = new Button("Alteração");
            buttonAlt.setUserData(a[0]);
            buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

            buttonAlt.setOnAction(event -> {
                String idAtual = (String) buttonAlt.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
            });
            container.add(buttonAlt, col + 7, row);

            Button buttonAp = new Button("Aprovado");
            buttonAp.setUserData(a[0]);
            buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonAp.setOnAction(event -> {
                String idAtual = (String) buttonAp.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
            });
            container.add(buttonAp, col + 8, row);

            Button buttonPr = new Button("Pronto");
            buttonPr.setUserData(a[0]);
            buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            buttonPr.setOnAction(event -> {
                String idAtual = (String) buttonPr.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
            });
            container.add(buttonPr, col + 9, row);

            Button buttonpf = new Button("Para fazer");
            buttonpf.setUserData(a[0]);
            buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonpf.setOnAction(event -> {
                String idAtual = (String) buttonpf.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
            });
            container.add(buttonpf, col + 10, row);


            Button button2 = new Button("Deletar");
            button2.setUserData(a[0]);

            button2.setOnAction(event -> {
                String idAtual = (String) button2.getUserData();

                try {
                    AroeiraModel aroeiraModel = new AroeiraModel();
                    aroeiraModel.deletarDados(Integer.parseInt(idAtual));

                    container.getChildren().clear();
                    listarAlteracoes();
                } catch (SQLException e) {
                    // Tratar a exceção aqui
                }
            });
           // botão deletar
            container.add(button2, col + 6, row);

            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
    }
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
        if(nome.isEmpty() || link.isEmpty() || data.isEmpty() || status.isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha os campos para atualizar!");
        }else{
            if(data.matches("\\d{2}/\\d{2}/\\d{4}")){
                if (status.equalsIgnoreCase("APROVADO") || status.equalsIgnoreCase("ALTERAÇÃO") || status.equalsIgnoreCase("PARA FAZER") || status.equalsIgnoreCase("PRONTO")){
                    aroeiraModel.atualizarDados(aroeiraController.getId(), aroeiraController.getArtes(), aroeiraController.getLink(), aroeiraController.getData(), aroeiraController.getStatus());
                    limparFormulario();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Digite: PARA FAZER, APROVADO, ALTERAÇÃO OU PRONTO!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "O Formato da data está errado\n O correto é: 00/00/0000");
            }
        }

        } catch (SQLException e) {
            // Tratar a exceção aqui
            e.printStackTrace();
        }


    }

    public void enviarAtualizarComParams(String id, String nome, String link, String data, String status){

        AroeiraController aroeiraController = new AroeiraController();

        aroeiraController.setId(id);
        aroeiraController.setArtes(nome);
        aroeiraController.setLink(link);
        aroeiraController.setData(data);
        aroeiraController.setStatus(status);

        AroeiraModel aroeiraModel = new AroeiraModel();
        try {
            if (status.equalsIgnoreCase("APROVADO") || status.equalsIgnoreCase("ALTERAÇÃO") || status.equalsIgnoreCase("PARA FAZER") || status.equalsIgnoreCase("PRONTO")){
                aroeiraModel.atualizarDados(aroeiraController.getId(), aroeiraController.getArtes(), aroeiraController.getLink(), aroeiraController.getData(), aroeiraController.getStatus());
                limparFormulario();
            }
            else {
                JOptionPane.showMessageDialog(null, "Digite: PARA FAZER, APROVADO, ALTERAÇÃO OU PRONTO!");
            }

        } catch (SQLException e) {
            // Tratar a exceção aqui
            e.printStackTrace();
        }


    }
    public void limparFormulario(){
        // Obtém a data atual
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        String dataFormatada = formato.format(dataAtual);

        txtId.setText("");
        txtArte.setText("");
        txtLink.setText("");
        txtData.setText(dataFormatada);
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
    //MenuBar
    @FXML
    public void MenuBarSobre(ActionEvent event) {
        // Código da ação a ser executada quando o item de menu 1 for selecionado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre");
        alert.setHeaderText(null);
        alert.setContentText("Desenvolvedor: Julian Silva\n2023\nJavaFx 20.0.1\nVersão 1.0.3");

        alert.showAndWait();


    }

//Sair

    public void btnSair(ActionEvent actionEvent) throws IOException {
    System.out.println("Até logo!");

        LoginModel loginModel = new LoginModel(mainContainer);
        loginModel.login();
//        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();
//
//        // Fecha a janela
//        stage.close();
    }

    public void MenuBarConf(ActionEvent actionEvent) throws IOException {
        // Código da ação a ser executada quando o item de menu 1 for selecionado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configurações");
        alert.setHeaderText(null);
        alert.setContentText(
                "Banco de dados: 192.168.0.206:3306"
                +"\nDatabase: controledeartes"
                +"\nMysql \nVersão 1.0");

        alert.showAndWait();

        ConfigModel configModel = new ConfigModel(mainContainer);
        configModel.config();
    }
    //Buscar por data
    public void btnBuscarData(ActionEvent actionEvent) {

        LocalDate dataSelecionada = dtData.getValue();
        String dataFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(dataFormatada);

        container.getChildren().clear();

        buscarPorData(dataFormatada);
        dtData.setValue(null);


        JOptionPane.showMessageDialog(null, "Aqui estão as artes do dia:\n" + dataFormatada);
    }
    public void buscarPorData(String datando) {
        ArrayList<String[]> listaArtesaroeira = new ArrayList<>();

        try {
            Connection connection = ConectaDB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM artesaroeira WHERE data = ?");
            statement.setString(1, datando);
            ResultSet resultSet = statement.executeQuery();

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

        int row = 0;
        int col = 0;
        for (String[] a : listaArtesaroeira) {
            Label label1 = new Label( " ( "+ a[0] + " ) ");
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

            Button button = new Button("Editar");
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



            Button buttonAlt = new Button("Alteração");
            buttonAlt.setUserData(a[0]);
            buttonAlt.setStyle("-fx-background-color: orange; -fx-text-fill: white;");

            buttonAlt.setOnAction(event -> {
                String idAtual = (String) buttonAlt.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"alteração");
            });
            container.add(buttonAlt, col + 7, row);

            Button buttonAp = new Button("Aprovado");
            buttonAp.setUserData(a[0]);
            buttonAp.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonAp.setOnAction(event -> {
                String idAtual = (String) buttonAp.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"APROVADO");
            });
            container.add(buttonAp, col + 8, row);

            Button buttonPr = new Button("Pronto");
            buttonPr.setUserData(a[0]);
            buttonPr.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            buttonPr.setOnAction(event -> {
                String idAtual = (String) buttonPr.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PRONTO");
            });
            container.add(buttonPr, col + 9, row);

            Button buttonpf = new Button("Para fazer");
            buttonpf.setUserData(a[0]);
            buttonpf.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

            buttonpf.setOnAction(event -> {
                String idAtual = (String) buttonpf.getUserData();
                String arteAtual = a[1];
                String linkAtual = a[2];
                String dataAtual = a[3];

                enviarAtualizarComParams(idAtual,arteAtual,linkAtual,dataAtual,"PARA FAZER");
            });
            container.add(buttonpf, col + 10, row);


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
            //Desativando botão deletar
            //  container.add(button2, col + 6, row);

            if (col + 7 >= 7) {
                col = 0;
                row++;
            } else {
                col += 7;
            }
        }
    }

    public void painelx(ActionEvent actionEvent) throws IOException {

        PainelModel painelModel = new PainelModel(mainContainer);
        painelModel.painel();

    }
}

