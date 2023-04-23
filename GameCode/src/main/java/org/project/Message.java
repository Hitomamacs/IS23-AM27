package org.project;

import java.util.List;

public class Message {

    private MessageID messageID;
    private String username;

    public Message(){

    }
    public Message(String username){
        this.username = username;
    }
    public MessageID getMessageID(){
        return messageID;
    }
    public String getUsername(){
        return username;
    }
    public void setMessageID(MessageID messageID) {
        this.messageID = messageID;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
