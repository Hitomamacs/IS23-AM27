package org.project;

public class loginMessage {

    String username;
    MessageID messageID;

    int numPlayers;
    boolean connectionType;

    public loginMessage(String username, boolean connectionType){
        this.username = username;
        this.connectionType = connectionType;
    }
    public loginMessage(String username, boolean connectionType, int numPlayers){
        this.username = username;
        this.connectionType = connectionType;
        this.numPlayers = numPlayers;
    }
    public String getUsername(){
        return username;
    }
    public MessageID getMessageID(){
        return messageID;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public boolean getConnectionType(){
        return connectionType;
    }

}
