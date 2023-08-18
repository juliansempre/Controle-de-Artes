package com.aerocopias.controledeartes.autentificacao.cadastro.controller;


import com.aerocopias.controledeartes.autentificacao.cadastro.model.CadastroUsuarioModel;
import com.aerocopias.controledeartes.autentificacao.login.model.LoginModel;
import com.aerocopias.controledeartes.controller.HelloController;
import com.aerocopias.controledeartes.painel.model.PainelModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class CadastroUsuarioController {

    @FXML
    private TextField txtNomecadastrox;
    @FXML
    private PasswordField txtSenhacadastrox;

    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas

    //Contrutor
    public CadastroUsuarioController(){
        System.out.println("Tela de cadastro");
    }

    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
        PainelModel painelModel = new PainelModel(mainContainer);
        painelModel.painel();
    }



    public void btnLimparcadastrox(ActionEvent actionEvent) {
        limparFormulario();
    }
    public void limparFormulario(){
        txtNomecadastrox.setText("");
        txtSenhacadastrox.setText("");
    }

    public void labelclickEsqueciSenha(MouseEvent mouseEvent) {
        System.out.println("Mensagem Esqueci senha");
        //Aqui entrara o acesso de super usuario para ver, atualizar e excluir usuarios
        JOptionPane.showMessageDialog(null, "Contate o administrador do sistema!");
    }

    public void btnSaircadastrox(ActionEvent actionEvent) {
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();

        // Fecha a janela
        stage.close();
    }

    public void btnConfiguracoescadastrox(ActionEvent actionEvent) {
        // Código da ação a ser executada quando o item de menu 1 for selecionado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Configurações");
        alert.setHeaderText(null);
        alert.setContentText(
                "Banco de dados: 192.168.0.206:3306"
                        +"\nDatabase: controledeartes"
                        +"\nMysql \nVersão 1.0.2");

        alert.showAndWait();
    }

    public void btnSobrecadastrox(ActionEvent actionEvent) {
        HelloController h = new HelloController();
        h.MenuBarSobre(actionEvent);
    }


    public void btnFazerLoginCadastro(ActionEvent event) throws IOException {
        //Tela de Login
        limparFormulario();
        LoginModel loginModel = new LoginModel(mainContainer);
        loginModel.login();
    }

    public void btnCadastrarCadastro(ActionEvent event) throws SQLException, IOException {
        //Cadastro
        String nome = txtNomecadastrox.getText();
        String senha = txtSenhacadastrox.getText();
        System.out.println(nome + senha);

            CadastroUsuarioModel cadastroUsuarioModel = new CadastroUsuarioModel(mainContainer);

            cadastroUsuarioModel.setUsuario(nome);
            cadastroUsuarioModel.setSenha(senha);

            if(cadastroUsuarioModel.getUsuario().isEmpty() || cadastroUsuarioModel.getSenha().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha os campos!");
            }else {
                try {
                    cadastroUsuarioModel.inserirDadosUsuario(cadastroUsuarioModel.getUsuario(), cadastroUsuarioModel.getSenha());

                    limparFormulario();

                    LoginModel l = new LoginModel(mainContainer);
                    l.login();
                }catch (Exception erro){
                    JOptionPane.showMessageDialog(null, "Erro ao inserir usuario!");
                    System.out.println("Erro "+ erro);
                }
            }
        }
}
