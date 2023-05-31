package org.project.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField Username;
    @FXML
    private Button createGameButton;
    @FXML
    private Button joinButton;

    private GuiUserInterface guiUserInterface;
    public void startCreateGameAction(ActionEvent actionEvent) {

        Stage currentStage = (Stage) createGameButton.getScene().getWindow();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/createGame.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CreateGameController controller=loader.getController();
        controller.setGuiUserInterface(guiUserInterface);
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        currentStage.close();
        stage.show();
    }

    public void startJoinAction(ActionEvent actionEvent) {

        Stage currentStage = (Stage) joinButton.getScene().getWindow();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/join.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JoinController controller=loader.getController();
        controller.setGuiUserInterface(guiUserInterface);
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        currentStage.close();
        stage.show();
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
}
