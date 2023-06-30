package org.project.Controller.View;

/**
 * The class represents tiles view
 */
public class TilesView {
    private String[] playerTiles;
    private String username;

    /**
     * Constructor
     * @param username of the player
     */
    public TilesView(String username){
        this.username = username;
        playerTiles = new String[3];
    }

    /* In the view instead of using color we will use one letter strings e.g. "G" = green, this way we can
    define a letter for null and a letter for not valid (will be needed in the board representation)
    N for null, I for invalid */

    /**
     * The method updates the player tiles view with a new configuration represented by the pickedTiles,
     * it copies the values from the pickedTiles into the playerTiles, element by element.
     * @param pickedTiles tiles picked by the player
     */
    public void updateTilesView(String[] pickedTiles){
        System.arraycopy(pickedTiles, 0, playerTiles, 0, 3);
    }
    public String getUsername(){ return username; }
    public String[] getPlayerTiles(){ return playerTiles; }
}
