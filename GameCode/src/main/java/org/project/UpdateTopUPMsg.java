package org.project;

public class UpdateTopUPMsg extends Message{

    private String playerName;
    private String[] tiles;
    private String[][] grid;

    public UpdateTopUPMsg(String playerName, String[] tiles, String[][] grid){
        super();
        this.setMessageID(MessageID.TOPUP_UPDATE);
        this.playerName = playerName;
        this.grid = grid;
        this.tiles = tiles;
    }
    public UpdateTopUPMsg(String username, String playerName, String[] tiles, String[][] grid){
        super(username);
        this.setMessageID(MessageID.TOPUP_UPDATE);
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
