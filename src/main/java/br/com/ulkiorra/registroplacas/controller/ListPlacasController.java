package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.IPlacasDAO;
import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.model.Placas;
import br.com.ulkiorra.registroplacas.model.Status;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListPlacasController implements Initializable, DataChangedListner {

    private IPlacasDAO iPlacasDAO;

    @FXML
    private AnchorPane ancorpaine;

    @FXML
    private Label labeldtf;

    @FXML
    private Label labeldtfn;

    @FXML
    private Label labelfone;

    @FXML
    private Label labelnome;

    @FXML
    private Label labelobs;

    @FXML
    private Label labelprc;

    @FXML
    private Label labelstatus;

    @FXML
    private Label labelvend;

    @FXML
    private TableColumn<Placas, String> table_cliente;

    @FXML
    private TableColumn<Placas, Placas> table_delete;

    @FXML
    private TableColumn<Placas, LocalDate> table_dtestamp;

    @FXML
    private TableColumn<Placas, LocalDate> table_dtfin;

    @FXML
    private TableColumn<Placas, Placas> table_edit;

    @FXML
    private TableColumn<Placas, String> table_fone;

    @FXML
    private TableColumn<Placas, String> table_observacao;

    @FXML
    private TableColumn<Placas, String> table_placa;

    @FXML
    private TableColumn<Placas, Status> table_status;

    @FXML
    private TableColumn<Placas, String> table_vendedor;

    @FXML
    private TableColumn<Placas, Float> table_preco;

    @FXML
    private TableView<Placas> table_placas;

    @FXML
    private Label txtlabelplaca;

    @FXML
    void onActionNew(ActionEvent event) {
        Stage parentStage = Utils.getCurrentStage(event);
        createDialogorm(parentStage);
    }

    public void setiPlacasDAO(IPlacasDAO iPlacasDAO) {
        this.iPlacasDAO = iPlacasDAO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        table_placas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtlabelplaca.setText(newValue.getPlaca());
                labelnome.setText(newValue.getClient_name());
                labelfone.setText(newValue.getClient_fone());
                labeldtf.setText(newValue.getDataestampagem().toString());
                if(newValue.getDatafinalizacao() != null){
                    labeldtfn.setText(newValue.getDatafinalizacao().toString());
                }else {
                    labeldtfn.setText("");
                }
                labelobs.setText(newValue.getObservation());
                labelprc.setText(newValue.getPreco().toString());
                labelstatus.setText(newValue.getStatus().toString());
                labelvend.setText(newValue.getVendedor());
            }
        });
    }
    private void initializeNodes(){
        table_placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
        table_cliente.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        table_fone.setCellValueFactory(new PropertyValueFactory<>("client_fone"));
        table_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table_vendedor.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        table_observacao.setCellValueFactory(new PropertyValueFactory<>("observation"));
        table_dtestamp.setCellValueFactory(new PropertyValueFactory<>("dataestampagem"));
        table_dtfin.setCellValueFactory(new PropertyValueFactory<>("datafinalizacao"));
        table_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_placas.prefHeightProperty().bind(stage.heightProperty());
        table_placas.prefWidthProperty().bind(stage.widthProperty());
        ancorpaine.prefHeightProperty().bind(stage.heightProperty());
        ancorpaine.prefWidthProperty().bind(stage.widthProperty());
    }

    public void  updateTableView(){
        List<Placas> list = iPlacasDAO.FindAll();
        ObservableList<Placas> obsList = FXCollections.observableList(list);
        table_placas.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogorm(Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/cadastroPlacasView.fxml"));
            Pane pane = fxmlLoader.load();
            CadastroPlacasController controller = fxmlLoader.getController();
            controller.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Curso");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    private void createDialogormUpdate(Placas obj, Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/updatePlacasView.fxml"));
            Pane pane = fxmlLoader.load();
            UpdatePlacasController controller2 = fxmlLoader.getController();
            controller2.setEntity(obj);
            controller2.updateFormData();
            controller2.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update de Placa");
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
            protected void updateItem(Placas obj, boolean empty) {
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
            protected void updateItem(Placas obj, boolean empty) {
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

    private void removeEntity(Placas obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Você tem certeza que deseja deletar?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            iPlacasDAO.delete(obj);
            updateTableView();
        }
    }
}
