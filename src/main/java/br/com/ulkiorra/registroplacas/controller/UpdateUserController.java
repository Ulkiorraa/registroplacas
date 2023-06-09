package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.DaoFactory;
import br.com.ulkiorra.registroplacas.DAO.IUserDAO;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.model.User;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Formatter;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

    private User entity;

    private final List<DataChangedListner> dataChangeListeners = new ArrayList<>();

    @FXML
    private PasswordField txt_pass;

    @FXML
    private TextField txt_user;

    public void setEntity(User entity) {
        this.entity = entity;
    }

    @FXML
    void onBtnCad(ActionEvent event) {
        IUserDAO iUserDAO = DaoFactory.createUserDAO();
        String user = txt_user.getText().trim();
        String password = txt_pass.getText().trim();

        if (user.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Usuário é um campo obrigatório!");
            return;
        }
        if (user.length() < 4) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Usuário deve ter pelo menos 4 letras!");
            return;
        }
        if (password.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Senha é um campo obrigatório!");
            return;
        }
        if (password.length() < 6) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Senha deve ter pelo menos 6 caracteres!");
            return;
        }

        User user1 = new User();
        user1.setId(entity.getId());
        user1.setUser(user);
        user1.setPassword(password);

        User cadastradoComSucesso = iUserDAO.update(user1);
        if (cadastradoComSucesso != null) {
            Alerts.mostrarMensagem("Information", null, "Update bem sucedido!");
            notifyDataChangeListeners();
        } else {
            Alerts.mostrarMensagemDeErro("Erro", null, "Update falhou!");
        }

        Utils.getCurrentStage(event).close();
    }

    @FXML
    void onBtnCancel(ActionEvent event) {
        Utils.getCurrentStage(event).close();
    }

    public void subscribeDataChangeListener(DataChangedListner listner) {
        dataChangeListeners.add(listner);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_user.setTextFormatter(Formatter.noNumberFormatter());
        txt_user.setTextFormatter(Formatter.noSpaceFormatter());
        txt_pass.setTextFormatter(Formatter.noSpaceFormatter());
    }

    public void updateFormData(){
        txt_user.setText(entity.getUser());
        txt_pass.setText(entity.getPassword());
    }

    private void notifyDataChangeListeners() {
        for (DataChangedListner listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
}
