package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;
/**
 * Represents a message containing the updated "topUp" information.
 * Contains the player name, an array of tiles and the grid.
 */
public class UpdateTopUPMsg extends Message {
    private String playerName;
    private String[] tiles;
    private String[][] grid;

    public UpdateTopUPMsg(String playerName, String[] tiles, String[][] grid){
        super(MessageID.TOPUP_UPDATE);
        this.playerName = playerName;
        this.grid = grid;
        this.tiles = tiles;
    }
    public String getPlayerName() {
        return playerName;
    }
    public String[] getTiles() {
        return tiles;
    }
    public String[][] getGrid() {
        return grid;
    }
}
