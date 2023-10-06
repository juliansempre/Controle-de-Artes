package com.aerocopias.controledeartes.model;
import com.aerocopias.controledeartes.adm.configuracao.model.ConfigModel;
import javafx.scene.layout.BorderPane;

import java.awt.*;
        import java.awt.event.KeyEvent;
        import java.io.File;
        import java.io.IOException;
import java.sql.SQLException;

public class AbrirDiretorioModel {
    public String directoryPath;
    private static BorderPane mainContainer;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

//    public static void main(String[] args) {
//        buscardiretorio(45);
//    }

    public void buscardiretorio(int id) {
        //Iniciar com o nome atual do diretorio
        ConfigModel configModel = new ConfigModel(mainContainer);
        //configModel.inserirDadosDiretorio("teste", "Linkteste");
        try {
            configModel.listarDadosDiretorio();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String nome = configModel.getLinkdiretorio();
        //Inicio do codigo de busca

        String numeroString = "" + id;

        String directoryPath = nome;
        String searchString = numeroString;

        try {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                // Abre o File Explorer no diretório especificado
                Desktop.getDesktop().open(directory);

                // Aguarde um curto período de tempo para o File Explorer abrir
                Thread.sleep(2000);

                // Pressione a tecla Ctrl + F para abrir a caixa de pesquisa no File Explorer
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_F);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_F);

                // Digite a string de pesquisa
                for (char c : searchString.toCharArray()) {
                    robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                    robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
                }

                // Pressione Enter para iniciar a pesquisa
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            } else {
                System.out.println("O diretório especificado não existe ou não é um diretório válido.");
            }
        } catch (IOException | InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }
}
