package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class CreateGameController {
    @FXML
    private TextField Username;
    @FXML
    private TextField NumbPlayers;
    @FXML
    private Button Login1Button;
    @FXML
    private Label loginstatus;

    @FXML
    private Button Back;

    private GuiUserInterface guiUserInterface;
    private GuiController guiController;
    private GuiFx centralController;

    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }
    public Label getLoginStatus(){
        return this.loginstatus;
    }
    public void CreateGameAction(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String username = Username.getText();
            int numPlayers = Integer.parseInt(NumbPlayers.getText());
            GuiUserInterface guiUserInterface = centralController.getGuiUserInterface();

            guiUserInterface.setNickname(username);
            guiUserInterface.setNumPlayers(numPlayers);
            guiUserInterface.setInput("create_game");
            try {
                guiUserInterface.getClient().SendCreateGameMessage(username, centralController.getGuiUserInterface().getClient().get_connection_type(), numPlayers);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            centralController.showCreateGameScene();
        });

    }

    public void QuitAction(ActionEvent actionEvent){
        //TODO: scrivere quit
    }
    public void BackToWelcome(ActionEvent actionEvent){
        Platform.runLater(()->{
            centralController.showWelcomeScene();
        });
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
