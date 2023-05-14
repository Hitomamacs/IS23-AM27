package org.project.Controller.View;

public class TilesView {
    private String[] playerTiles;
    private String username;
    public TilesView(String username){
        this.username = username;
        playerTiles = new String[3];
    }
    //In the view instead of using color we will use one letter strings e.g. "G" = green, this way we can
    //define a letter for null and a letter for not valid (will be needed in the board representation)
    //N for null, I for invalid

    public void updateTilesView(String[] pickedTiles){
        System.arraycopy(pickedTiles, 0, playerTiles, 0, 3);
    }
    public String getUsername(){ return username; }
    public String[] getPlayerTiles(){ return playerTiles; }
}
