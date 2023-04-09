package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static Scene newScene;

    @FXML
    private Button btncancel;

    @FXML
    private Button btnenter;

    @FXML
    private PasswordField txtpass;

    @FXML
    private TextField txtuser;

    @FXML
    private VBox tela_login;

    @FXML
    void onClickcancel(ActionEvent event) {

    }

    @FXML
    void onClickenter(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/principalView.fxml"));
            ScrollPane scrollPane = fxmlLoader.load();
            newScene = new Scene(scrollPane);
            Stage newStage = new Stage();
            newStage.setTitle("Sistema de cadastros");
            newStage.setResizable(false);
            newStage.setScene(newScene);
            newStage.show();
            Stage Login_window = (Stage) tela_login.getScene().getWindow();
            Login_window.close();
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", "Tela Principal não carregada!", e.getMessage());
        }
    }

    public static Scene getMainScene(){
        return  newScene;
    }

}
