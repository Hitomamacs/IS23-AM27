package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
            loginstatus.setText(guiUserInterface.getClientView().getPopUpErrorMessage());
            try {
                guiUserInterface.getClient().SendCreateGameMessage(username, guiUserInterface.getClient().get_connection_type(), numPlayers);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
