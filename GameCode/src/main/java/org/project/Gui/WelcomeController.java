package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField Username;
    @FXML
    private Button createGameButton;
    @FXML
    private Button joinButton;

    @FXML
    private Pane bannerPane;

    @FXML
    private Button warning;

    public void showBanner() {
        Platform.runLater(() -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(centralController.getPrimaryStage());
            dialog.setTitle("Server Crash");
            dialog.setHeaderText("Server is unreachable");

            Label contentLabel = new Label("The server has crashed and is currently unreachable.");
            VBox contentPane = new VBox(10);
            contentPane.setAlignment(Pos.CENTER);
            contentPane.setPadding(new Insets(20));
            contentPane.getChildren().add(contentLabel);

            dialog.getDialogPane().setContent(contentPane);

            dialog.showAndWait();
        });
    }

    private GuiUserInterface guiUserInterface;
    private GuiController guiController;
    private GuiFx centralController;


    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }
    public void startCreateGameAction(ActionEvent actionEvent) {

        centralController.showCreateGameScene();

        /*
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/createGame.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CreateGameController controller=loader.getController();
        controller.setGuiUserInterface(guiUserInterface);
        guiUserInterface.getClientView().addPropertyChangeListener(controller.getPopupListener());
        guiUserInterface.getClientView().addPropertyChangeListener(controller.getRefreshListener());
        controller.setGuiController(guiController);
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        guiController.closeScene();
        guiController.setPrimaryStage(stage);
        stage.show();*/
    }

    public void startJoinAction(ActionEvent actionEvent) {

        centralController.showJoinScene();
        /*
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/join.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JoinController controller=loader.getController();
        controller.setGuiUserInterface(guiUserInterface);
        guiUserInterface.getClientView().addPropertyChangeListener(controller.getPopupListener());
        guiUserInterface.getClientView().addPropertyChangeListener(controller.getRefreshListener());
        controller.setGuiController(guiController);
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        guiController.closeScene();
        guiController.setPrimaryStage(stage);
        stage.show();
        */
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
