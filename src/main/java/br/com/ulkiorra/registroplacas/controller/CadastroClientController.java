package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.DaoFactory;
import br.com.ulkiorra.registroplacas.DAO.IClientDAO;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.model.Client;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Formatter;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CadastroClientController implements Initializable {

    private final List<DataChangedListner> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txt_cnpj;

    @FXML
    private TextField txt_cpf;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_fone;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_par;

    @FXML
    private TextField txt_und;

    @FXML
    void onClickCad(ActionEvent event) {
        IClientDAO iClientDAO = DaoFactory.createClientDAO();
        String nome = txt_nome.getText().trim();
        String fone = txt_fone.getText().trim();
        String email = txt_email.getText().trim();
        LocalDate date = txt_date.getValue();
        String cpf = txt_cpf.getText().trim();
        String cnpj = txt_cnpj.getText().trim();
        String ppar = txt_par.getText().trim();
        if (ppar.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Preço padrão par é um campo obrigatório!");
            return;
        }
        Float par = Float.parseFloat(ppar);
        String pund = txt_und.getText().trim();
        if (pund.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Preço padrão unidade é um campo obrigatório!");
            return;
        }
        Float und = Float.parseFloat(pund);

        if (nome.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Nome é um campo obrigatório!");
            return;
        }
        if (fone.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Telefone é um campo obrigatório!");
            return;
        }

        Client client = new Client();
        client.setNome(nome);
        client.setTelefone(fone);
        client.setEmail(email);
        client.setDateCad(date);
        client.setCpf(cpf);
        client.setCnpj(cnpj);
        client.setPreco_par(par);
        client.setPreco_und(und);

        Client cadastroComSucesso = iClientDAO.create(client);
        if (cadastroComSucesso != null) {
            Alerts.mostrarMensagem("Information", null, "Cadastro bem sucedido!");
            notifyDataChangeListeners();
        } else {
            Alerts.mostrarMensagemDeErro("Erro", null, "Cadastro falhou!");
        }

        Utils.getCurrentStage(event).close();
    }

    @FXML
    void onClickCan(ActionEvent event) {
        Utils.getCurrentStage(event).close();
    }

    public void subscribeDataChangeListener(DataChangedListner listner) {
        dataChangeListeners.add(listner);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_date.setValue(LocalDate.now());
        txt_email.setTextFormatter(Formatter.noSpaceFormatter());
        txt_cpf.setTextFormatter(Formatter.cpfFormatter());
        txt_cnpj.setTextFormatter(Formatter.cnpjFormatter());
        txt_fone.setTextFormatter(Formatter.noLettersFormatter());
        txt_par.setTextFormatter(Formatter.noLettersFormatter());
        txt_und.setTextFormatter(Formatter.noLettersFormatter());
        txt_nome.setTextFormatter(Formatter.noNumberFormatter());
    }

    private void notifyDataChangeListeners() {
        for (DataChangedListner listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
}
