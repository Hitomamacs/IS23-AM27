package org.project.Gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChatController {
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button Send;
    @FXML
    private Button Back;

    @FXML
    private Pane bannerPane;

    @FXML
    private Button warning;

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

