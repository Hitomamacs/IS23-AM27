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

public class JoinController {

    private GuiUserInterface guiUserInterface;
    private GuiFx centralController;
    @FXML
    private TextField Username;
    @FXML
    private Button Login2Button;
    @FXML
    private Button Back;
    @FXML
    private Label Loginstatus;

    private GuiController guiController;

    public Label getLoginStatus(){
        return this.Loginstatus;
    }
    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }
    public void JoinAction(ActionEvent actionEvent) {

        Platform.runLater(()->{
            String username = Username.getText();
            GuiUserInterface guiUserInterface = centralController.getGuiUserInterface();

            guiUserInterface.setNickname(username);
            guiUserInterface.setInput("join");
            guiUserInterface.getClient().SendJoinMessage(username, guiUserInterface.getClient().get_connection_type());

            centralController.showJoinScene();
            });

    }
    public void BackToWelcome(ActionEvent actionEvent){
        Platform.runLater(()->{
            centralController.showWelcomeScene();
        });
    }
    public void QuitAction(ActionEvent actionEvent){
        //TODO: quit
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
