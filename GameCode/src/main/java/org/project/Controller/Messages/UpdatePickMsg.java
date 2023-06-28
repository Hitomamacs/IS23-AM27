package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

/**
 * Represents a message containing the updated pick information.
 * Contains the player name, an array of tiles and the game board.
 */
public class UpdatePickMsg extends Message {
    private String playerName;
    private String[] tiles;
    private String[][] board;

    public UpdatePickMsg(String playerName, String[] tiles, String[][] board){
        super(MessageID.PICK_UPDATE);
        this.playerName = playerName;
        this.board = board;
        this.tiles = tiles;
    }
    public String getPlayerName() {
        return playerName;
    }
    public String[] getTiles() {
        return tiles;
    }
    public String[][] getBoard() {
        return board;
    }
}
