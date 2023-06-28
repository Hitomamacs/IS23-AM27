package org.project.Controller.Messages;

public class CreateGame_Message extends Message {
    private String username;
    private int numPlayers;
    private boolean connectionType;
    public CreateGame_Message(String username, boolean connectionType, int numPlayers){
        super(MessageID.CREATE_GAME);
        this.username = username;
        this.connectionType = connectionType;
        this.numPlayers = numPlayers;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public boolean getConnectionType(){
        return connectionType;
    }

    public String getUsername(){
        return username;
    }
}
