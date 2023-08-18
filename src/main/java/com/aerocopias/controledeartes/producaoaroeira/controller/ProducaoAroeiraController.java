package com.aerocopias.controledeartes.producaoaroeira.controller;

import com.aerocopias.controledeartes.autentificacao.login.controller.SessãoController;
import com.aerocopias.controledeartes.autentificacao.login.model.LoginModel;
import com.aerocopias.controledeartes.model.ConectaDB;
import com.aerocopias.controledeartes.painel.model.PainelModel;
import com.aerocopias.controledeartes.producaoaroeira.model.ProducaoAroeiraModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProducaoAroeiraController implements Initializable {
    @FXML
    public VBox mainContainerVBOX;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label labeltxtSaudacao;
    @FXML
    private TableView<ProducaoAroeiraController> tabelaProducaoAroeira;
    @FXML
    private BorderPane mainContainer;
    private ConectaDB conexao;
    //ListView
    @FXML
    private ListView lvProducaoAroeira;
    private List<ProducaoAroeiraModel> tarefas = new ArrayList<>();
    private ObservableList<ProducaoAroeiraModel> obsTarefas;
    //Campos de texto
    @FXML
    private TextField txtIdProducaoArte;
    @FXML
    private TextField txtQuantidadeProducaoArte;
    @FXML
    private TextField txtDescricaoProducaoArte;
    @FXML
    private TextField txtMedidaProducaoArte;
    @FXML
    private DatePicker txtDataProducaoArte;
    @FXML
    private TextField txtStatusProducaoArte;


    //Contrutor
    public ProducaoAroeiraController(){

        System.out.println("Tela de Produção da Aroeira");
    }
    @FXML
    public void btnproducaoAroeiraSair(ActionEvent actionEvent) throws IOException {
        LoginModel l = new LoginModel(mainContainer);
        l.login();
    }
    @FXML
    public void btnproducaoAroeiraIrPainel(ActionEvent actionEvent) throws IOException {
        PainelModel p = new PainelModel(mainContainer);
        p.painel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarTarefasFromDatabase();
        //Criar botões
        ativarcriandobotao();
        //Data de agora
        LocalDateTime now = LocalDateTime.now();
        txtDataProducaoArte.setValue(LocalDate.from(now));
        //sessão
        String nomeUsuario = SessãoController.getNomeUsuarioAutenticado();
        if (nomeUsuario != null) {
            labeltxtSaudacao.setText("" + nomeUsuario.toUpperCase() + "");
        }

    }
    public void teste(){
        JOptionPane.showMessageDialog(null, "Funciona");
    }
    //Teste Listar tarefas
//    public void listarTarefas(){
//        // Exemplo 1
//        BorderPane mainContainer1 = new BorderPane();
//        ProducaoAroeiraModel t1 = new ProducaoAroeiraModel(1, 10, "Descrição 1", "Medida 1", "Status 1", "Data 1", "Data Postagem 1", mainContainer1);
//
//        // Exemplo 2
//        BorderPane mainContainer2 = new BorderPane();
//        ProducaoAroeiraModel t2 = new ProducaoAroeiraModel(2, 20, "Descrição 2", "Medida 2", "Status 2", "Data 2", "Data Postagem 2", mainContainer2);
//
//        // Exemplo 3
//        BorderPane mainContainer3 = new BorderPane();
//        ProducaoAroeiraModel t3 = new ProducaoAroeiraModel(3, 30, "Descrição 3", "Medida 3", "Status 3", "Data 3", "Data Postagem 3", mainContainer3);
//
//        tarefas.add(t1);
//        tarefas.add(t2);
//        tarefas.add(t3);
//
//        obsTarefas = FXCollections.observableArrayList(tarefas);
//
//        lvProducaoAroeira.setItems(obsTarefas);
//    }
    //fim Listar tarefas

    //Listar do banco de dados tabela producaoaroeira

    public void listarTarefasFromDatabase() {
        ConectaDB db = new ConectaDB();

        try (Connection connection = db.getConnection()) {
            System.out.println("Conexão da listagem ok");
            String query = "SELECT * FROM producaoaroeira";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<ProducaoAroeiraModel> tarefas = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quantidade = resultSet.getInt("quantidade");
                String descricao = resultSet.getString("descricao");
                String medida = resultSet.getString("medida");
                String status = resultSet.getString("status");
                String data = resultSet.getString("data");
                String funcionario = resultSet.getString("funcionario");
                String dataPostagem = resultSet.getString("data_postagem");

                BorderPane mainContainer = new BorderPane();
                ProducaoAroeiraModel tarefa = new ProducaoAroeiraModel(id, quantidade, descricao, medida, status, data, funcionario, dataPostagem, mainContainer);
                tarefas.add(tarefa);
            }

            obsTarefas = FXCollections.observableArrayList(tarefas);
            lvProducaoAroeira.setItems(obsTarefas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnSalvarProducaoArte(ActionEvent event) {
        inserirMetodoProducaoArte();
    }
    public void inserirMetodoProducaoArte(){
        int quantidade = 0;
        try {
            quantidade = Integer.parseInt(txtQuantidadeProducaoArte.getText());
        } catch (NumberFormatException e) {
            System.out.println("Erro: "+e);
        }
        String descricao = txtDescricaoProducaoArte.getText();
        String medida = txtMedidaProducaoArte.getText();

        //Pegar dataPicker
        LocalDate dataSelecionada = txtDataProducaoArte.getValue();
        String data = dataSelecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(data);

        //Data atual
        Date currentDate = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);

        String status = txtStatusProducaoArte.getText();

        //Inserir funcionario

        //sessão
        String nomeUsuario = SessãoController.getNomeUsuarioAutenticado();


        if(quantidade == 0 || descricao.isEmpty() || medida.isEmpty() || data.isEmpty() || status.isEmpty()){
            System.out.println("Preencha os campos!");
            JOptionPane.showMessageDialog(null, "Preencha os campos");
        }else{
            try {
                System.out.println("Inserindo...");
                ProducaoAroeiraModel p = new ProducaoAroeiraModel(0,quantidade,descricao,medida,status,data,"funcionario",formattedDate,mainContainer);
                p.inserirDadosTarefa(quantidade, descricao, medida, status, data, nomeUsuario, currentDate);

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao inserir tarefa");
                System.out.println("Erro: "+e);
            }
            //Refresh
            mainContainer.getChildren().clear();
            listarTarefasFromDatabase();
            limpar();
        }

    }

    public void btnAtualizarProducaoArte(ActionEvent event) {
        int quantidade = 0;
        int id = 0;
        try {
            quantidade = Integer.parseInt(txtQuantidadeProducaoArte.getText());
            id = Integer.parseInt(txtIdProducaoArte.getText());
        } catch (NumberFormatException e) {
            System.out.println("Erro: "+e);
        }
        String descricao = txtDescricaoProducaoArte.getText();
        String medida = txtMedidaProducaoArte.getText();

        //Pegar dataPicker
        LocalDate dataSelecionada = txtDataProducaoArte.getValue();
        String data = dataSelecionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(data);

        //Data atual
        Date currentDate = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        //Data agora
        LocalDateTime now = LocalDateTime.now();

        String status = txtStatusProducaoArte.getText();

        //Inserir funcionario

        //sessão
        String nomeUsuario = SessãoController.getNomeUsuarioAutenticado();


        if(id == 0 || quantidade == 0 || descricao.isEmpty() || medida.isEmpty() || data.isEmpty() || status.isEmpty() || currentDate == null){
            System.out.println("Preencha os campos!");
            JOptionPane.showMessageDialog(null, "Preencha os campos");
        }else{
            try {
                System.out.println("Atualizando...");
                ProducaoAroeiraModel p = new ProducaoAroeiraModel(0,quantidade,descricao,medida,status,data,"funcionario",formattedDate,mainContainer);
                p.atualizarDadosTarefa(id, quantidade, descricao, medida, status, data, nomeUsuario,currentDate);

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao inserir tarefa");
                System.out.println("Erro: "+e);
            }
            //Refresh
            mainContainer.getChildren().clear();
            listarTarefasFromDatabase();
            limpar();
        }

    }

    public void limpar(){
        txtIdProducaoArte.setText("");
        txtQuantidadeProducaoArte.setText("");
        txtDescricaoProducaoArte.setText("");
        txtMedidaProducaoArte.setText("");
        LocalDateTime now = LocalDateTime.now();
        txtDataProducaoArte.setValue(LocalDate.from(now));
        txtStatusProducaoArte.setText("");

        //Refresh
        mainContainer.getChildren().clear();
        listarTarefasFromDatabase();
    }

    //Cell Factory inserindo botão na listview

        public void ativarcriandobotao() {
            String cordotextodobotao = "#FFFFFF";

            List<ButtonInfo> buttonInfos = new ArrayList<>();


            final Runnable runnable = () -> {
              JOptionPane.showMessageDialog(null, "Selecione um item da lista");
            };

            buttonInfos.add(new ButtonInfo("✔ | PRONTO", () -> {
                // Lógica para o botão 1
                int index = lvProducaoAroeira.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < lvProducaoAroeira.getItems().size()) {
                    ProducaoAroeiraModel item = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                    System.out.println("Botão pronto clicado para o item de ID: " + item.getId());
                } else {
                    System.out.println("Índice inválido selecionado.");
                    runnable.run();
                }
            }, "#32CD32"));

            buttonInfos.add(new ButtonInfo("PARA FAZER", () -> {
                // Lógica para o botão 2
                int index = lvProducaoAroeira.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < lvProducaoAroeira.getItems().size()) {
                    ProducaoAroeiraModel item = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                    System.out.println("Botão para fazer clicado para o item de ID: " + item.getId());
                } else {
                    System.out.println("Índice inválido selecionado.");
                    runnable.run();
                }
            }, "#1E90FF"));

            buttonInfos.add(new ButtonInfo("EDITAR", () -> {
                // Lógica para o botão 1
                int index = lvProducaoAroeira.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < lvProducaoAroeira.getItems().size()) {
                    ProducaoAroeiraModel item = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                    System.out.println("Botão Editar clicado para o item de ID: " + item.getId());

                    ProducaoAroeiraModel clickedItem = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                    txtIdProducaoArte.setText(String.valueOf(clickedItem.getId()));
                    txtQuantidadeProducaoArte.setText(String.valueOf(clickedItem.getQuantidade()));
                    txtDescricaoProducaoArte.setText(clickedItem.getDescricao());
                    txtMedidaProducaoArte.setText(clickedItem.getMedida());
                    txtDataProducaoArte.setValue(LocalDate.parse(clickedItem.getDataPostagem()));
                    txtStatusProducaoArte.setText(clickedItem.getStatus());

                } else {
                    System.out.println("Índice inválido selecionado.");
                    runnable.run();
                }
            }, ""));

            buttonInfos.add(new ButtonInfo("DELETAR", () -> {
                // Lógica para o delete button
                int index = lvProducaoAroeira.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < lvProducaoAroeira.getItems().size()) {
                    ProducaoAroeiraModel item = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                    System.out.println("Botão deletar clicado para o item de ID: " + item.getId());

                    ProducaoAroeiraModel producaoAroeiraModel = new ProducaoAroeiraModel(0,0,"","","","","","",mainContainer);
                    String[] opcoes = {"Sim", "Não"};
                    int resposta = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                    if (resposta == JOptionPane.YES_OPTION) {
                        // Código para o botão sim

                        try {
                            producaoAroeiraModel.excluirTarefa(item.getId());
                            //refresh
                            mainContainer.getChildren().clear();
                            listarTarefasFromDatabase();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (resposta == JOptionPane.NO_OPTION) {
                        // Código para o botão não
                        //refresh
                        mainContainer.getChildren().clear();
                        listarTarefasFromDatabase();
                    }

                } else {
                    System.out.println("Índice inválido selecionado.");
                    runnable.run();
                }
            }, "#CD0000"));

            // Adicione mais botões conforme necessário, com cores diferentes

            createDynamicButtons(lvProducaoAroeira, buttonInfos, cordotextodobotao);
        }
    private Map<Integer, List<Button>> buttonMap = new HashMap<>();

    private void createDynamicButtons(ListView<ProducaoAroeiraModel> listView, List<ButtonInfo> buttonInfos, String cordobotao) {
        listView.setCellFactory(param -> new ListCell<ProducaoAroeiraModel>() {
            @Override
            protected void updateItem(ProducaoAroeiraModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox cellLayout = new HBox(); // Layout principal
                    cellLayout.setAlignment(Pos.CENTER_LEFT); // Alinhar à esquerda

                    // Configuração do texto
                    Label textLabel = new Label(item.getId() + " ) | " + item.getData() + " | " + item.getQuantidade() + " " + item.getDescricao() + " " + item.getMedida() + " " + item.getStatus().toUpperCase() + " " + item.getFuncionario().toUpperCase());
                    HBox textLayout = new HBox(textLabel); // Layout do texto
                    textLayout.setAlignment(Pos.CENTER_LEFT); // Alinhar à esquerda

                    // Configuração dos botões
                    HBox buttonBox = new HBox();
                    Region filler = new Region(); // Elemento de preenchimento
                    HBox.setHgrow(filler, Priority.ALWAYS); // Preenchimento expansível

                    List<Button> buttons = new ArrayList<>();

                    for (ButtonInfo buttonInfo : buttonInfos) {
                        Button button = new Button(buttonInfo.getText());
                        button.setStyle("-fx-background-color: " + buttonInfo.getColor() + "; -fx-text-fill: " + "" + ";");

                        final int index = getIndex(); // Obtém o índice da linha atual

                        button.setOnAction(event -> {
                            ProducaoAroeiraModel clickedItem = (ProducaoAroeiraModel) lvProducaoAroeira.getItems().get(index);
                           // System.out.println("Botão clicado para o item de ID: " + clickedItem.getId());
                            buttonInfo.getAction().run();

                        });

                        buttons.add(button);
                    }

                    buttonMap.put(getIndex(), buttons); // Adiciona os botões no mapa

                    buttonBox.getChildren().addAll(buttons);
                    buttonBox.getChildren().add(filler);

                    cellLayout.getChildren().addAll(textLayout, filler, buttonBox); // Adicionei o 'filler' para garantir o espaçamento

                    setGraphic(cellLayout);

                    // Verifica se a linha está selecionada e mostra/oculta os botões correspondentes
                    int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                    if (getIndex() == selectedIndex) {
                        buttonBox.setVisible(true);
                    } else {
                        buttonBox.setVisible(false);
                    }
                }
            }
        });
    }

    public void btnParaFazerListarProducaoAroeira(ActionEvent actionEvent) {
        //Refresh
        mainContainer.getChildren().clear();
        listarTarefasFromDatabase();
    }

    public void btnProntosListarProducaoAroeira(ActionEvent actionEvent) {
    }


// ... (resto do código)


    private static class ButtonInfo {
        private final String text;
        private final Runnable action;
        private final String color; // Adicione a propriedade de cor

        public ButtonInfo(String text, Runnable action, String color) {
            this.text = text;
            this.action = action;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Runnable getAction() {
            return action;
        }

        public String getColor() {
            return color;
        }
    }


    public void brnLimparFormularioProducaoAroeira(ActionEvent event) {
        limpar();
    }

    public void listaPersonalizada(String comandoSQLquery) {
        ConectaDB db = new ConectaDB();

        try (Connection connection = db.getConnection()) {
            System.out.println("Conexão da listagem ok");
            String query = comandoSQLquery;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<ProducaoAroeiraModel> tarefas = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int quantidade = resultSet.getInt("quantidade");
                String descricao = resultSet.getString("descricao");
                String medida = resultSet.getString("medida");
                String status = resultSet.getString("status");
                String data = resultSet.getString("data");
                String funcionario = resultSet.getString("funcionario");
                String dataPostagem = resultSet.getString("data_postagem");

                BorderPane mainContainer = new BorderPane();
                ProducaoAroeiraModel tarefa = new ProducaoAroeiraModel(id, quantidade, descricao, medida, status, data, funcionario, dataPostagem, mainContainer);
                tarefas.add(tarefa);
            }

            obsTarefas = FXCollections.observableArrayList(tarefas);
            lvProducaoAroeira.setItems(obsTarefas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}