package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.DaoFactory;
import br.com.ulkiorra.registroplacas.DAO.IClientDAO;
import br.com.ulkiorra.registroplacas.DAO.IPlacasDAO;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.model.Client;
import br.com.ulkiorra.registroplacas.model.Placa_Tipo;
import br.com.ulkiorra.registroplacas.model.Placas;
import br.com.ulkiorra.registroplacas.model.Status;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Formatter;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdatePlacasController implements Initializable {

    private Placas entity;

    private final List<DataChangedListner> dataChangeListeners = new ArrayList<>();

    @FXML
    private ComboBox<String> txt_cliente;

    @FXML
    private TextField txt_preco;

    @FXML
    private ComboBox<Placa_Tipo> txt_tipo;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TextField txt_vendedor;

    @FXML
    private DatePicker txt_datefinalizacao;

    @FXML
    private TextField txt_idsolicitante;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextArea txt_observacao;

    @FXML
    private TextField txt_placa;

    @FXML
    private ComboBox<Status> txt_status;

    @FXML
    private TextField txt_telefone;

    public void setEntity(Placas entity) {
        this.entity = entity;
    }

    @FXML
    void onClickCad(ActionEvent event) {
        IPlacasDAO iPlacasDAO = DaoFactory.createPlacasDAO();
        String placa = txt_placa.getText().trim();
        String cId = txt_idsolicitante.getText().trim();
        Long clientID = null;
        if(!cId.equals("")) clientID = Long.parseLong(cId);
        Status status = txt_status.getValue();
        String observation = txt_observacao.getText().trim();
        LocalDate dataestampagem = txt_date.getValue();
        LocalDate datafinalizacao = txt_datefinalizacao.getValue();
        String name = txt_nome.getText().trim();
        String fone = txt_telefone.getText().trim();
        String vendedor = txt_vendedor.getText().trim();
        String prc = txt_preco.getText().trim();
        Float preco = Float.parseFloat(prc);
        Placa_Tipo tipo = txt_tipo.getValue();

        if (placa.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Placa é um campo obrigatório!");
            return;
        }
        if (status == null) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Status é um campo obrigatório!");
            return;
        }
        if (dataestampagem == null) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Data da estampagem é um campo obrigatório!");
            return;
        }
        if (name.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Nome é um campo obrigatório!");
            return;
        }
        if (fone.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Telefone é um campo obrigatório!");
            return;
        }
        if (prc.isEmpty()) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Preço é um campo obrigatório!");
            return;
        }
        if (tipo == null) {
            Alerts.mostrarMensagemDeErro("Erro", null, "Tipo é um campo obrigatório!");
            return;
        }
        Placas placa1 = new Placas();
        placa1.setId(entity.getId());
        placa1.setPlaca(placa);
        placa1.setClientId(clientID);
        placa1.setStatus(status);
        placa1.setObservation(observation);
        placa1.setDataestampagem(dataestampagem);
        placa1.setDatafinalizacao(datafinalizacao);
        placa1.setClient_name(name);
        placa1.setClient_fone(fone);
        placa1.setVendedor(vendedor);
        placa1.setPreco(preco);
        placa1.setTipo(tipo);

        Placas cadastroComSucesso = iPlacasDAO.update(placa1);
        if (cadastroComSucesso != null) {
            Alerts.mostrarMensagem("Information", null, "Update bem sucedido!");
            notifyDataChangeListeners();
        } else {
            Alerts.mostrarMensagemDeErro("Erro", null, "Update falhou!");
        }

        Utils.getCurrentStage(event).close();
    }

    @FXML
    void onClickCancel(ActionEvent event) {
        Utils.getCurrentStage(event).close();
    }

    public void subscribeDataChangeListener(DataChangedListner listner) {
        dataChangeListeners.add(listner);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IClientDAO iClientDAO = DaoFactory.createClientDAO();
        List<Client> opList = iClientDAO.FindAll();
        txt_cliente.getItems().add("none");
        for (Client e : opList) {
            txt_cliente.getItems().add(e.getNome());
        }

        txt_status.getItems().add(Status.ESTAMPADA);
        txt_status.getItems().add(Status.FINALIZADA);
        txt_tipo.getItems().add(Placa_Tipo.PAR);
        txt_tipo.getItems().add(Placa_Tipo.UNIDADE);

        txt_placa.textProperty().addListener((observable, oldValue, newValue) -> txt_placa.setText(newValue.toUpperCase()));
        txt_vendedor.textProperty().addListener((observable, oldValue, newValue) -> txt_vendedor.setText(newValue.toUpperCase()));

        txt_placa.setTextFormatter(Formatter.noSpaceFormatter());
        txt_placa.setTextFormatter(Formatter.PlacasFormatter());
        txt_nome.setTextFormatter(Formatter.noNumberFormatter());
        txt_telefone.setTextFormatter(Formatter.noLettersFormatter());
        txt_vendedor.setTextFormatter(Formatter.noNumberFormatter());
        txt_preco.setTextFormatter(Formatter.noLettersFormatter());

        txt_cliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("none")) {
                Client selectedClient = iClientDAO.FindByName(newValue);
                if (selectedClient != null) {
                    txt_nome.setText(selectedClient.getNome());
                    txt_telefone.setText(selectedClient.getTelefone());
                    txt_idsolicitante.setText(selectedClient.getId().toString());
                    if (txt_tipo.getValue() == Placa_Tipo.PAR){
                        txt_preco.setText(selectedClient.getPreco_par().toString());
                    }
                    if (txt_tipo.getValue() == Placa_Tipo.UNIDADE){
                        txt_preco.setText(selectedClient.getPreco_und().toString());
                    }
                    if (txt_tipo.getValue() == null){
                        txt_cliente.setValue("none");
                        Alerts.mostrarMensagem("Escolha o tipo", null, "Escolha o tipo primeiro");
                    }
                }
            } else {
                txt_nome.clear();
                txt_telefone.clear();
                txt_idsolicitante.clear();
                txt_preco.clear();
            }
        });

        txt_tipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && txt_cliente != null) {
                Client selectedClient = iClientDAO.FindByName(txt_cliente.getValue());
                if (selectedClient != null) {
                    if (txt_tipo.getValue() == Placa_Tipo.PAR){
                        txt_preco.setText(selectedClient.getPreco_par().toString());
                    }
                    if (txt_tipo.getValue() == Placa_Tipo.UNIDADE){
                        txt_preco.setText(selectedClient.getPreco_und().toString());
                    }
                }
            } else {
                txt_preco.clear();
            }
        });

        txt_status.setOnAction(event -> {
            if (txt_status.getValue() == Status.FINALIZADA) {
                txt_datefinalizacao.setValue(LocalDate.now());
            } else {
                txt_datefinalizacao.setValue(null);
            }
        });
    }

    public void updateFormData(){
        txt_placa.setText(String.valueOf(entity.getPlaca()));
        txt_tipo.setValue(entity.getTipo());
        txt_status.setValue(entity.getStatus());
        txt_observacao.setText(entity.getObservation());
        txt_date.setValue(entity.getDataestampagem());
        txt_datefinalizacao.setValue(entity.getDatafinalizacao());
        if(entity.getClientId() != null){
            txt_idsolicitante.setText(entity.getClientId().toString());
        }else {
            txt_idsolicitante.setText("");
        }
        if (entity.getClientId() != null){
            txt_cliente.setValue(entity.getClient_name());
        }else {
            txt_cliente.setValue("none");
        }
        txt_nome.setText(entity.getClient_name());
        txt_telefone.setText(entity.getClient_fone());
        txt_vendedor.setText(entity.getVendedor());
        txt_preco.setText(entity.getPreco().toString());
    }

    private void notifyDataChangeListeners() {
        for (DataChangedListner listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

}