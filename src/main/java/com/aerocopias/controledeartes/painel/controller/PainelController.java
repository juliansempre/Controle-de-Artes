package com.aerocopias.controledeartes.painel.controller;


import com.aerocopias.controledeartes.autentificacao.login.controller.SessãoController;
import com.aerocopias.controledeartes.autentificacao.login.model.LoginModel;
import com.aerocopias.controledeartes.controller.HelloController;
import com.aerocopias.controledeartes.model.AroeiraTelaModel;
import com.aerocopias.controledeartes.painel.model.PainelModel;
import com.aerocopias.controledeartes.producaoaroeira.controller.ProducaoAroeiraController;
import com.aerocopias.controledeartes.producaoaroeira.model.ProducaoAroeiraModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PainelController implements Initializable {
    public MenuItem btnSobrePainel;
    public MenuItem brnConfPainel;
    @FXML
    public Label labelNomePainel;
    @FXML
    private Label textoTela;
    @FXML
    private Button btnArteAroeira;

    @FXML
    private BorderPane mainContainer; // O contêiner onde as telas serão exibidas

    public String nome;
    PainelModel p = new PainelModel(mainContainer);
    //Contrutor
    public PainelController(){
        System.out.println("painel");
        this.nome = nome;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //sessão
        String nomeUsuario = SessãoController.getNomeUsuarioAutenticado();
        if (nomeUsuario != null) {
            labelNomePainel.setText("Bem-vindo, " + nomeUsuario.toUpperCase() + "!");
        }
    }

    //Operações get e set
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            labelNomePainel.setText("Bem-vindo, " + nome + "!");
        }




    @FXML
    private void voltarParaPrincipal(ActionEvent event) throws IOException {
    System.out.println("Work");
    }

    public void btnArteAroeira(ActionEvent actionEvent) throws IOException {
        AroeiraTelaModel aroeiraTelaModel = new AroeiraTelaModel(mainContainer);
        aroeiraTelaModel.aroeira();
    }

    public void btnPainelSair(ActionEvent actionEvent) throws IOException {
        LoginModel loginModel = new LoginModel(mainContainer);
        loginModel.login();
    }
    public void btnSobrePainel(ActionEvent event) throws IOException{
        HelloController h = new HelloController();
        h.MenuBarSobre(event);
    }
    public void btnConfPainel(ActionEvent event) throws IOException{
        HelloController h = new HelloController();
        h.MenuBarConf(event);
    }


    public void btnProducaoAroeiraAbrir(ActionEvent actionEvent) throws IOException {
        ProducaoAroeiraModel producaoAroeiraModel = new ProducaoAroeiraModel( 0,0,"","","","","","",mainContainer);
        producaoAroeiraModel.producaoAroeiraTela();
    }
}
