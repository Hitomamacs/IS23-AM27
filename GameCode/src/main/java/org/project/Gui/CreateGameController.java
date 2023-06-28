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
            if(!guiUserInterface.isFirstAction()){
                try {
                    guiUserInterface.getClient().SendCreateGameMessage(username, centralController.getGuiUserInterface().getClient().get_connection_type(), numPlayers);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                if(guiUserInterface.getClientView()!=null){
                    if(guiUserInterface.getClientView().getPopUpErrorMessage().equals("Successfully created game") && guiUserInterface.getClientView().getPopUpErrorMessage().contains("Succesfully joined game"))
                        guiUserInterface.setFirstAction(true);
                }

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
}
