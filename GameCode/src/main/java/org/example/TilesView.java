package org.example;

public class TilesView {
    String[] playerTiles;
    String username;
    public TilesView(String username){
        this.username = username;
        playerTiles = new String[3];
    }
    //In the view instead of using color we will use one letter strings e.g. "G" = green, this way we can
    //define a letter for null and a letter for not valid (will be needed in the board representation)
    //N for null, I for invalid
    public void setTile(String color, int position){
        playerTiles[position] = color;
    }
    public void removeTile(int position){
        playerTiles[position] = "N";
    }

}
