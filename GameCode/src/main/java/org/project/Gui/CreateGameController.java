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

    /**
     * The method collects the information entered by the user (username and number of players),
     * stores them in the guiUserInterface and sends a game creation message to the remote client.
     * Next, move on to the game creation scene.
     * @param actionEvent press button
     */
    public void CreateGameAction(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String username = Username.getText();
            int numPlayers = Integer.parseInt(NumbPlayers.getText());
            GuiUserInterface guiUserInterface = centralController.getGuiUserInterface();

            guiUserInterface.setNickname(username);
            guiUserInterface.setNumPlayers(numPlayers);
            guiUserInterface.setInput("create_game");
            if(guiUserInterface.isFirstAction()==false){
                try {
                    guiUserInterface.getClient().SendCreateGameMessage(username, centralController.getGuiUserInterface().getClient().get_connection_type(), numPlayers);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                guiUserInterface.setFirstAction(true);
            }else{
                loginstatus.setText("Hai già fatto login/create game");
            }

            centralController.showCreateGameScene();
        });
    }

    /**
     * The method is an event handler for the Quit button, it calls the quit() method to quit the application.
     * @param actionEvent button press
     */
    public void QuitAction(ActionEvent actionEvent) {
        quit();
    }

    /**
     * The method is called to close the application
     */
    public void quit(){
        //centralController.getGuiUserInterface().getClient().SendQuitMessage(centralController.getGuiUserInterface().getNickname());
        //guiUserInterface.getClient().close();
        Platform.exit();
        System.exit(0);
    }

    /**
     * The method is used to return to the Welcome Scene
     * @param actionEvent button press
     */
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
