package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class CadastroUserController {

    private final List<DataChangedListner> dataChangeListeners = new ArrayList<>();

    @FXML
    private Button btnCad;

    @FXML
    private Button btnCancel;

    @FXML
    private PasswordField txt_pass;

    @FXML
    private TextField txt_user;

    @FXML
    void onBtnCad(ActionEvent event) {

    }

    @FXML
    void onBtnCancel(ActionEvent event) {

    }

    public void subscribeDataChangeListener(DataChangedListner listner){
        dataChangeListeners.add(listner);
    }

    private void notifyDataChangeListeners() {
        for (DataChangedListner listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
}
