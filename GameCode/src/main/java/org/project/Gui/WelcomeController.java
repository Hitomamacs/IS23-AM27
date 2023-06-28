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

    /**
     * The method displays a specific warning message to inform the user that the server is inaccessible or has crashed.
     * This dialog is displayed on top of the main application window.
     */
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

    /**
     * The method is called when the "Create game" button is pressed in the welcome scene.
     * It calls the GuiFx showCreateGameScene() method to switch to the game creation scene.
     * @param actionEvent button press
     */
    public void startCreateGameAction(ActionEvent actionEvent) {
        centralController.showCreateGameScene();
    }

    /**
     * The method is called when the "Join" button is pressed in the welcome scene.
     * It calls the GuiFx showCreateGameScene() method to switch to the join scene.
     * @param actionEvent button press
     */
    public void startJoinAction(ActionEvent actionEvent) {
        centralController.showJoinScene();
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
