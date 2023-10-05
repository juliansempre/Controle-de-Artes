package com.aerocopias.controledeartes.autentificacao.login.controller;


import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import com.aerocopias.controledeartes.autentificacao.cadastro.model.CadastroUsuarioModel;
import com.aerocopias.controledeartes.autentificacao.login.model.LoginModel;
import com.aerocopias.controledeartes.controller.HelloController;
import com.aerocopias.controledeartes.painel.controller.PainelController;
import com.aerocopias.controledeartes.painel.model.PainelModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private Label textoTela;
    @FXML
    private TextField txtNomeLogin;
    @FXML
    private PasswordField txtSenhaLogin;

    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas
    private String nome;

    //Contrutor
    public LoginController(){
        System.out.println("Tela de login");

    }

    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
        PainelModel painelModel = new PainelModel(mainContainer);
        painelModel.painel();
    }

    public void btnEntrarLogin(ActionEvent actionEvent) throws IOException {
        autentificar();
    }

    public void autentificar() throws IOException {

        String nome = txtNomeLogin.getText();
        String senha = txtSenhaLogin.getText();

        System.out.println("Autentificando..." + nome + senha);
        // Cria um objeto da classe LoginModel com os dados do usuário
    if(nome.isEmpty() || senha.isEmpty()){
        JOptionPane.showMessageDialog(null, "Preencha os campos!");
    }else {
        LoginModel login = new LoginModel(mainContainer);
        login.setUsuario(nome);
        login.setSenha(senha);

        // Chama o método de autenticação e recebe o valor de retorno
        boolean autenticado = login.autenticaUsuario(login);

        // Verifica se o usuário foi autenticado ou não
        if (autenticado) {
            System.out.println("Usuário autenticado!");
            System.out.println(login.getUsuario());

            // Mostrar usuario no painel

            // Passar o nome do usuário autenticado para o PainelController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aerocopias/controledeartes/painel.fxml"));
            Parent root = loader.load();

            PainelController painelController = loader.getController();
            painelController.setNome(nome); // Define o nome do usuário autenticado


            // Exiba a tela do painel com o nome do usuário autenticado
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Fechar tela de login
            Stage stage2 = (Stage) txtNomeLogin.getScene().getWindow();
            stage2.close();

            //Sessão
            SessãoController.setNomeUsuarioAutenticado(nome);

        } else {
            System.out.println("Usuário ou senha inválidos!");
            JOptionPane.showMessageDialog(null,"Usuário ou senha inválidos!");
        }
    }

        limparFormulario();
    }

    public void btnLimparLogin(ActionEvent actionEvent) {
        limparFormulario();
    }
    public void limparFormulario(){
        txtNomeLogin.setText("");
        txtSenhaLogin.setText("");
    }

    public void labelclickEsqueciSenha(MouseEvent mouseEvent) {
        System.out.println("Mensagem Esqueci senha");
        //Aqui entrara o acesso de super usuario para ver, atualizar e excluir usuarios
        JOptionPane.showMessageDialog(null, "Contate o administrador do sistema!");
    }

    public void btnSairLogin(ActionEvent actionEvent) {
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow();

        // Fecha a janela
        stage.close();
    }

    public void btnConfiguracoesLogin(ActionEvent actionEvent) throws IOException {
        ConfigModel configModel = new ConfigModel(mainContainer);
        configModel.config();
    }

    public void btnSobreLogin(ActionEvent actionEvent) {
        HelloController h = new HelloController();
        h.MenuBarSobre(actionEvent);
    }

    public void btnCadastrarUsuario(ActionEvent event) throws IOException {
        CadastroUsuarioModel cadastroUsuarioModel = new CadastroUsuarioModel(mainContainer);
        cadastroUsuarioModel.cadastro();
    }

}
