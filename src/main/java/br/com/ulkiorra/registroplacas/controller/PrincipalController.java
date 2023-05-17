package br.com.ulkiorra.registroplacas.controller;

import br.com.ulkiorra.registroplacas.DAO.DaoFactory;
import br.com.ulkiorra.registroplacas.Principal;
import br.com.ulkiorra.registroplacas.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class PrincipalController implements Initializable {

    @FXML
    void onListClient() {
        loadView("view/listClientView.fxml", (ListClientController controller) -> {
            controller.setiClientDAO(DaoFactory.createClientDAO());
            controller.updateTableView();
        });
    }

    @FXML
    void onListPlacas() {
        loadView("view/listPlacasView.fxml", (ListPlacasController controller) -> {
            controller.setiPlacasDAO(DaoFactory.createPlacasDAO());
            controller.updateTableView();
        });
    }

    @FXML
    void onListUser() {

    }

    @FXML
    void onHelpSobre() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
