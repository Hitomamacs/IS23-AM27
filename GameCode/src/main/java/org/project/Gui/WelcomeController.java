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

/**
 * Controller that controls the Welcome Scene screen
 */
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
    private GuiUserInterface guiUserInterface;
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
}
