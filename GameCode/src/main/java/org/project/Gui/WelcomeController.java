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
