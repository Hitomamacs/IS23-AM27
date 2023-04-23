package org.project;

public class UpdatePickMsg extends Message{
    private String playerName;
    private String[] tiles;
    private String[][] board;

    public UpdatePickMsg(String playerName, String[] tiles, String[][] board){
        super();
        this.setMessageID(MessageID.PICK_UPDATE);
        this.playerName = playerName;
        this.board = board;
        this.tiles = tiles;
    }
    public UpdatePickMsg(String username, String playerName, String[] tiles, String[][] board){
        super(username);
        this.setMessageID(MessageID.PICK_UPDATE);
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
