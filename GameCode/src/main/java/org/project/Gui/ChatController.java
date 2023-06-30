package org.project.Gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.project.Controller.Messages.ChatMessage;

import java.util.List;

/**
 * Controller that controls the Chat scene
 */
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

    @FXML
    private Button NextChat;

    @FXML
    private  TextField searchBar;

    @FXML
    private Button search;

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
    private GuiUserInterface guiUserInterface;
    private GuiFx centralController;

    /**
     * The method configures the disabling property of the "Send" button based on the content of the "messageTextField"
     * text field. The "Send" button is disabled if the text field is empty.
     */
    public void initialize() {

        Send.disableProperty().bind(messageTextField.textProperty().isEmpty());
        search.disableProperty().bind(searchBar.textProperty().isEmpty());
    }
    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }
    public void setCentralController(GuiFx centralController) {
        this.centralController = centralController;
    }

    /**
     * The method takes the message from the text field, adds it to the chat, clears the text field
     * and sends the text message
     * @param actionEvent button press
     */
    public void sendMessage(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String message = messageTextField.getText();
            appendMessageToChat(message);
            clearMessageTextField();
            if(guiUserInterface.getClientView().getCurrentChat().equalsIgnoreCase("Broadcast")){
            guiUserInterface.getClient().SendChatMessage(guiUserInterface.getNickname(), message);
            }else{
                guiUserInterface.getClient().SendChatMessage(guiUserInterface.getNickname(), message, guiUserInterface.getClientView().getCurrentChat());
            }
        });
    }
    public void searchChat(ActionEvent actionEvent){
        Platform.runLater(()->{
            String selected_chat = searchBar.getText();
            searchBar.clear();
            if(selected_chat.equalsIgnoreCase("Broadcast")){
                guiUserInterface.getClientView().setCurrent_Chat(selected_chat);
                reloadChat(selected_chat);
                return;

            }
            if(guiUserInterface.getClientView().findChat(selected_chat)){
                guiUserInterface.getClientView().setCurrent_Chat(selected_chat);
                reloadChat(selected_chat);
            }
        });
    }


    /**
     * The method adds the message to the chat, preceded by the username "You" to indicate
     * that the message is sent by the current user
     * @param message button press
     */
    private void appendMessageToChat(String message) {
        chatTextArea.appendText("You: " + message + "\n");
    }

    /**
     * The method clears the content of the text field
     */
    private void clearMessageTextField() {
        messageTextField.clear();
    }

    /**
     * The method adds a message to the chat from another user.
     * The message is displayed in the chatTextArea with the username of the message author.
     * @param username of the player who has sent the message in the chat
     * @param message that the player wants to share with other players
     */
    public void addMessageToChat(String username, String message) {
        Platform.runLater(() -> {
            if(!(username.equals(guiUserInterface.getNickname()))){
                chatTextArea.appendText(username+ ": " + message + "\n");
            }
        });
    }
    public void reloadChat(String chat_to_load){
        chatTextArea.clear();
        if(!chat_to_load.equalsIgnoreCase("Broadcast")){


        List<ChatMessage> messages = guiUserInterface.getClientView().getPrivateChats().get(chat_to_load);
        if(messages != null  &&!messages.isEmpty() ){


        for(int i = 0; i < messages.size(); i++){
            ChatMessage message = messages.get(i);
            String sender = message.getUsername();
            if(!sender.equalsIgnoreCase(guiUserInterface.getNickname())) {
                chatTextArea.appendText(sender+ ": " + message.getText() + "\n");
            }else chatTextArea.appendText("You: "+ message.getText() + "\n");
        }
        }

        }
        else {
            List<ChatMessage> messages = guiUserInterface.getClientView().getChat();
            if (messages != null && !messages.isEmpty()) {
                for (int i = 0; i < messages.size() ; i++) {
                    ChatMessage message = messages.get(i);
                    String sender = message.getUsername();
                    if (!sender.equalsIgnoreCase(guiUserInterface.getNickname())) {
                        chatTextArea.appendText(sender + ": " + message.getText() + "\n");
                    } else chatTextArea.appendText("You: " + message.getText() + "\n");
                }
            }
        }

    }


    /**
     * The method is used to return to the Main Scene
     * @param actionEvent button press
     */
    public void BackToMain(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            centralController.getMainSceneController().removeNotification();
            centralController.showMainScene();
        });
    }
}

