package org.project.Controller.Messages;

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
