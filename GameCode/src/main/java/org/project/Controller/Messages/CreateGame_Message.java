package org.project.Controller.Messages;

/**
 * Represents a message for creating a game.
 * Contains information about the username, connection type and number of players.
 */

public class CreateGame_Message extends Message {
    private String username;
    private int numPlayers;
    private boolean connectionType;

    /**
     * Constructor with the specified username, connection type, and number of players.
     * @param username of the player creating the game
     * @param connectionType The connection type for the game (true = online, false = offline)
     * @param numPlayers the desired number of players for the game
     */
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
