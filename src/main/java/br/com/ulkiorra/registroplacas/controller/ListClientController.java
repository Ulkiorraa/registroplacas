package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.IClientDAO;
import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.model.Client;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListClientController implements Initializable, DataChangedListner {

    private IClientDAO iClientDAO;

    @FXML
    private TableView<Client> table_clientes;

    @FXML
    private TableColumn<Client, String> table_cnpj;

    @FXML
    private TableColumn<Client, String> table_cpf;

    @FXML
    private TableColumn<Client, LocalDate> table_datacad;

    @FXML
    private TableColumn<Client, Client> table_delete;

    @FXML
    private TableColumn<Client, Client> table_edit;

    @FXML
    private TableColumn<Client, String> table_email;

    @FXML
    private TableColumn<Client, String> table_fone;

    @FXML
    private TableColumn<Client, String> table_nome;

    @FXML
    private TableColumn<Client, Float> table_precopar;

    @FXML
    private TableColumn<Client, Float> table_precound;

    @FXML
    void onActionNew(ActionEvent event) {
        Stage parentStage = Utils.getCurrentStage(event);
        createDialogorm(parentStage);
    }

    public void setiClientDAO(IClientDAO iClientDAO) {
        this.iClientDAO = iClientDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }
    private void initializeNodes(){
        table_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        table_fone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        table_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_datacad.setCellValueFactory(new PropertyValueFactory<>("dateCad"));
        table_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        table_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        table_precopar.setCellValueFactory(new PropertyValueFactory<>("preco_par"));
        table_precound.setCellValueFactory(new PropertyValueFactory<>("preco_und"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_clientes.prefHeightProperty().bind(stage.heightProperty());
        table_clientes.prefWidthProperty().bind(stage.widthProperty());
    }

    public void  updateTableView(){
        List<Client> list = iClientDAO.FindAll();
        ObservableList<Client> obsList = FXCollections.observableList(list);
        table_clientes.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogorm(Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/cadastroClientView.fxml"));
            Pane pane = fxmlLoader.load();
            CadastroClientController controller = fxmlLoader.getController();
            controller.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Cliente");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    private void createDialogormUpdate(Client obj, Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/updateClientView.fxml"));
            Pane pane = fxmlLoader.load();
            UpdateClientController controller2 = fxmlLoader.getController();
            controller2.setEntity(obj);
            controller2.updateFormData();
            controller2.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update de Cliente");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        table_edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        table_edit.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("edite");

            @Override
            protected void updateItem(Client obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogormUpdate(obj, Utils.getCurrentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        table_delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        table_delete.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Client obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Client obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Você tem certeza que deseja deletar?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            iClientDAO.delete(obj);
            updateTableView();
        }
    }
}
