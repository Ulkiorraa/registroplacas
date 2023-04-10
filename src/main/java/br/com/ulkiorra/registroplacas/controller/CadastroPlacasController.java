package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.model.Client;
import br.com.ulkiorra.registroplacas.model.Status;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadastroPlacasController {

    @FXML
    private ComboBox<Client> txt_cliente;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TextArea txt_observacao;

    @FXML
    private TextField txt_placa;

    @FXML
    private ComboBox<Status> txt_status;

    @FXML
    void onClickCad(ActionEvent event) {

    }

    @FXML
    void onClickCancel(ActionEvent event) {
        Utils.getCurrentStage(event).close();
    }
}