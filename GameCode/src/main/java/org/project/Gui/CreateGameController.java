package org.project.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

            if(guiUserInterface.getClientView().getPopUpErrorMessage()!=null){
                loginstatus.setText(guiUserInterface.getClientView().getPopUpErrorMessage());
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
