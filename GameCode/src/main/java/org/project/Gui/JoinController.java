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
    @FXML
    private TextField Username;
    @FXML
    private Button Login2Button;
    @FXML
    private Label Loginstatus;

    private GuiController guiController;
    public void JoinAction(ActionEvent actionEvent) {

        Platform.runLater(()->{
            String username = Username.getText();

            guiUserInterface.setNickname(username);

            guiUserInterface.setInput("join");


            guiUserInterface.getClient().SendJoinMessage(username, guiUserInterface.getClient().get_connection_type());


            });

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
                    controller.setGuiController(guiController);
                    guiUserInterface.getClientView().addPropertyChangeListener(controller.getRefreshListener());
                    Stage stage=new Stage();
                    stage.setScene(new Scene(root));
                    guiController.closeScene();
                    guiController.setPrimaryStage(stage);
                    stage.show();
                }
            });
        }
    };

    public void QuitAction(ActionEvent actionEvent){
        //TODO: quit
    }

    public PropertyChangeListener getPopupListener() {
        return popupListener;
    }

    PropertyChangeListener popupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {
                if("popupCreate".equals(evt.getPropertyName())){
                    Loginstatus.setText(guiUserInterface.getClientView().getPopUpErrorMessage());
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
