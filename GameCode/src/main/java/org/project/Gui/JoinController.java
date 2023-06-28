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
            if(guiUserInterface.isFirstAction()==false){
                guiUserInterface.getClient().SendJoinMessage(username, guiUserInterface.getClient().get_connection_type());
                if(guiUserInterface.getClientView().getPopUpErrorMessage() != null && (!guiUserInterface.getClientView().getPopUpErrorMessage().contains("is already in use in the game"))){
                    guiUserInterface.setFirstAction(true);
                }

            }else{
                Loginstatus.setText("Hai già fatto login/create game");
            }

            centralController.showJoinScene();
            });

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

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
