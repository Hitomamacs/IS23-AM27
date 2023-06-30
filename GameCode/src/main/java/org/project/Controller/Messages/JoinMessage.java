package org.project.Controller.Messages;

/**
 * Represents a message for join a game.
 * Contains information about the username and connection type.
 */
public class JoinMessage extends Message {
    private String username;
    private boolean connectionType;
    public JoinMessage(String username, boolean connectionType){
        super(MessageID.JOIN);
        this.username = username;
        this.connectionType = connectionType;
    }
    public boolean getConnectionType(){
        return connectionType;
    }
    public String getUsername(){
        return username;
    }
}
