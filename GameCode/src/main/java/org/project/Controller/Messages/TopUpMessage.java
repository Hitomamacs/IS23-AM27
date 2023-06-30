package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

/**
 * Represents a message indicating a player's intention to "topUp" their grid with a tile
 * from the specified column and tile index.
 * Contains information about the username, column and tile index.
 */

public class TopUpMessage extends Message {
    private int column;
    private int tileIndex;
    private String username;
    public TopUpMessage(String username, int column, int tileIndex) {
        super(MessageID.TOPUP);
        this.username = username;
        this.column = column;
        this.tileIndex = tileIndex;
    }
    public String getUsername(){
        return username;
    }
    public int getColumn(){
        return column;
    }
    public int getTileIndex(){
        return tileIndex;
    }
}
