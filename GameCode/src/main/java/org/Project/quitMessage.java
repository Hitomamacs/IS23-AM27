package org.Project;

public class quitMessage {
    String username;
    MessageID messageID;

    public quitMessage(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public MessageID getMessageID(){
        return messageID;
    }

}
