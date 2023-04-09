package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.config.ConnectionFactory;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Encript;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private static Scene newScene;

    @FXML
    private PasswordField txtpass;

    @FXML
    private TextField txtuser;

    @FXML
    private VBox tela_login;

    @FXML
    void onClickcancel(ActionEvent event) {
        Utils.getCurrentStage(event).close();
    }

    @FXML
    void onClickenter() {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT *FROM user WHERE user = ? AND senha = ?";
            st = conn.prepareStatement(query);
            st.setString(1, txtuser.getText());
            st.setString(2, Encript.encryptSHA1(txtpass.getText()));
            rs = st.executeQuery();
            if(rs.next()) {
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
            }else {
                Alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", null, "Usuário ou Senha incorretos!");
            }
        }catch (SQLException e) {
            Alerts.mostrarMensagemDeErro("Erro recupera login BD", "Erro ao acessar BD para pegar informações de login", e.getMessage());
        }finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
        }
    }

    public static Scene getMainScene(){
        return  newScene;
    }

}
