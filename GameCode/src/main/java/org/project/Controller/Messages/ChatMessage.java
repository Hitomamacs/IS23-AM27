package org.project.Controller.Messages;

/**
 * The class represents a chat message with a username and a text.
 * The class inherits from the Message class and provides methods for accessing the username and message text.
 */
public class ChatMessage extends Message {
    private String username;
    private String text;

    public ChatMessage(String username, String text){
        super(MessageID.CHAT);
        this.username = username;
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public String getUsername(){
        return username;
    }
}
