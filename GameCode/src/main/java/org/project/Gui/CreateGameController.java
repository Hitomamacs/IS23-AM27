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

    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    public void CreateGameAction(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String username = Username.getText();
            int numPlayers = Integer.parseInt(NumbPlayers.getText());

            guiUserInterface.setNickname(username);
            guiUserInterface.setNumPlayers(numPlayers);

            guiUserInterface.setInput("create_game");

            try {
                guiUserInterface.getClient().SendCreateGameMessage(username, guiUserInterface.getClient().get_connection_type(), numPlayers);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void QuitAction(ActionEvent actionEvent){
        //TODO: scrivere quit
    }

    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }

    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("refresh".equals(evt.getPropertyName())){
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MainSceneController controller=loader.getController();
                    controller.setGuiUserInterface(guiUserInterface);
                    //guiUserInterface.getClientView().addPropertyChangeListener(controller.getPopupListener());
                    Stage stage=new Stage();
                    stage.setScene(new Scene(root));
                    guiController.closeScene();
                    guiController.setPrimaryStage(stage);
                    stage.show();
                }
            });
        }
    };

    public PropertyChangeListener getPopupListener() {
        return popupListener;
    }

    PropertyChangeListener popupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("popupCreate".equals(evt.getPropertyName())){
                    loginstatus.setText(guiUserInterface.getClientView().getPopUpErrorMessage());
                }
            });
        }
    };

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
