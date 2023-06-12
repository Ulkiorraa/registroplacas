package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.DaoFactory;
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
import java.util.*;

public class ListPedidoPlacasController implements Initializable, DataChangedListner {
    private Timer timer;

    private IPlacasDAO iPlacasDAO;

    @FXML
    private TableColumn<Placas, String> table_cliente;

    @FXML
    private TableColumn<Placas, Placas> table_delete;

    @FXML
    private TableColumn<Placas, LocalDate> table_dtPedido;

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
    void onClickAtt() {
        updateTableView();
    }

    public void setiPlacasDAO(IPlacasDAO iPlacasDAO) {
        this.iPlacasDAO = iPlacasDAO;
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        setiPlacasDAO(DaoFactory.createPlacasDAO());
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                onClickAtt();
            }
        };
        // Define o intervalo de tempo em milissegundos (10 segundos = 10.000 milissegundos)
        long interval = 10000;
        // Define o atraso inicial (0 para começar imediatamente)
        long delay = 0;
        timer.scheduleAtFixedRate(task, delay, interval);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void initializeNodes(){
        table_placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
        table_cliente.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        table_fone.setCellValueFactory(new PropertyValueFactory<>("client_fone"));
        table_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table_vendedor.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        table_observacao.setCellValueFactory(new PropertyValueFactory<>("observation"));
        table_dtPedido.setCellValueFactory(new PropertyValueFactory<>("datapedido"));
        table_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_placas.prefHeightProperty().bind(stage.heightProperty());
        table_placas.prefWidthProperty().bind(stage.widthProperty());
    }

    public void  updateTableView(){
        if(!iPlacasDAO.Open()){
            stopTimer();
            return;
        }
        List<Placas> list = iPlacasDAO.FindByStatus(Status.SOLICITADA);
        ObservableList<Placas> obsList = FXCollections.observableList(list);
        table_placas.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
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