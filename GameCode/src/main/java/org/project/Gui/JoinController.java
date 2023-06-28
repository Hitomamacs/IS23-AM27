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

/**
 * Controller that controls the Join scene
 */
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

    public Label getLoginStatus(){
        return this.Loginstatus;
    }
    public void setCentralController(GuiFx controller){
        this.centralController = controller;
    }

    /**
     * The method takes the username entered by the user, stores it and sends a join message to the remote client.
     * If the user has previously logged in or created a game, an appropriate message is shown.
     * @param actionEvent button press
     */
    public void JoinAction(ActionEvent actionEvent) {

        Platform.runLater(()->{
            String username = Username.getText();
            GuiUserInterface guiUserInterface = centralController.getGuiUserInterface();

            guiUserInterface.setNickname(username);
            guiUserInterface.setInput("join");
            if(!guiUserInterface.isFirstAction()){
                guiUserInterface.getClient().SendJoinMessage(username, guiUserInterface.getClient().get_connection_type());
                if(guiUserInterface.getClientView()!=null){
                    if(guiUserInterface.getClientView().getPopUpErrorMessage().contains("Successfully created game") && guiUserInterface.getClientView().getPopUpErrorMessage().contains("Succesfully joined game")){
                        guiUserInterface.setFirstAction(true);
                    }
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
        Platform.exit();
        System.exit(0);
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
}
