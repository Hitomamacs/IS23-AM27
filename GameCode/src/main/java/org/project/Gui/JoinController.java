package org.project.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class JoinController {

    private GuiUserInterface guiUserInterface;
    @FXML
    private TextField Username;
    @FXML
    private Button Login2Button;
    @FXML
    private Label Loginstatus;
    public void joinAction(ActionEvent actionEvent) {

        String username = Username.getText();

        guiUserInterface.setNickname(username);

        guiUserInterface.setInput("join");
    }

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void modifyLabel(ActionEvent actionEvent) {
        Loginstatus.setText("Calcolo");
    }
}
