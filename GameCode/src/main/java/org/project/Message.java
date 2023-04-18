package org.project;

import java.util.List;

public class Message {

    MessageID messageID;
    String username;

    public Message(String username){
        this.username = username;
    }
    public MessageID getMessageID(){
        return messageID;
    }
    public String getUsername(){
        return username;
    }

}
