package org.project.Controller.Messages;

public class LoginMessage extends Message {

    int numPlayers;
    boolean connectionType;

    public LoginMessage(String username, boolean connectionType){
        super(username);
        this.setMessageID(MessageID.LOGIN);
        this.connectionType = connectionType;
    }
    public LoginMessage(String username, boolean connectionType, int numPlayers){
        super(username);
        this.setMessageID(MessageID.LOGIN);
        this.connectionType = connectionType;
        this.numPlayers = numPlayers;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
    public boolean getConnectionType(){
        return connectionType;
    }

}
