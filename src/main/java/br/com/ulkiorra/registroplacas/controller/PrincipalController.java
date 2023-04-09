package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.listeners.DataChangedListner;
import br.com.ulkiorra.registroplacas.util.Alerts;
import br.com.ulkiorra.registroplacas.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class PrincipalController implements Initializable, DataChangedListner {

    @FXML
    private Button btnClientView;

    @FXML
    private Button btnPlacasView;

    @FXML
    private MenuItem cad_clent;

    @FXML
    private MenuItem cad_placas;

    @FXML
    private MenuItem cad_user;

    @FXML
    private MenuItem edit_Client;

    @FXML
    private MenuItem edit_placas;

    @FXML
    private MenuItem edit_user;

    @FXML
    private MenuItem help_sobre;

    @FXML
    private ListView<?> listplacas;

    @FXML
    private Label txtlabelplaca;

    @FXML
    private ScrollPane principalWindow;

    @FXML
    void onBtnClientView(ActionEvent event) {

    }

    @FXML
    void onBtnPlacasView(ActionEvent event) {

    }

    @FXML
    void onCadClient(ActionEvent event) {

    }

    @FXML
    void onCadPlacas(ActionEvent event) {

    }

    @FXML
    void onCadUser() {
        Stage parentStage = Utils.getCurrentStage(principalWindow);
        createDialogorm(parentStage, "view/cadastroUserView.fxml", "Cadastro de Usuário");
    }

    @FXML
    void onEditClient(ActionEvent event) {

    }

    @FXML
    void onEditPlacas(ActionEvent event) {

    }

    @FXML
    void onEditUser(ActionEvent event) {

    }

    @FXML
    void onHelpSobre(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onDataChanged() {

    }

    private synchronized <T> void loadView(String absolutName, Consumer<T> initializeAction) {
        try {
            FXMLLoader loader = new FXMLLoader(Principal.class.getResource(absolutName));
            VBox newVbox = loader.load();
            Scene mainScene = LoginController.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());
            T controller = loader.getController();
            initializeAction.accept(controller);
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro ao carregar tela!", "Erro ao carregar tela!", e.getMessage());
        }
    }

    private void createDialogorm(Stage parentStage, String resource, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource(resource));
            Pane pane = fxmlLoader.load();
            CadastroUserController controller = fxmlLoader.getController();
            controller.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }
}
