package org.project.Gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

public class ChatController {
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button Send;
    @FXML
    private Button Back;
    private GuiUserInterface guiUserInterface;

    private GuiFx centralController;

    public void initialize() {
        Send.disableProperty().bind(messageTextField.textProperty().isEmpty());
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
    public void setCentralController(GuiFx centralController) {
        this.centralController = centralController;
    }

    public void sendMessage(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String message = messageTextField.getText();
            appendMessageToChat(message);
            clearMessageTextField();
            guiUserInterface.getClient().SendChatMessage(guiUserInterface.getNickname(), message);
        });
    }
    private void appendMessageToChat(String message) {
        chatTextArea.appendText("You: " + message + "\n");
    }
    private void clearMessageTextField() {
        messageTextField.clear();
    }
    public void addMessageToChat(String username, String message) {
        Platform.runLater(() -> {
            if(!(username.equals(guiUserInterface.getNickname()))){
                chatTextArea.appendText(username+ ": " + message + "\n");
            }
        });
    }
    public void BackToMain(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            centralController.showMainScene();
        });
    }
}

