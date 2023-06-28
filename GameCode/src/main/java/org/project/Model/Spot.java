package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.Model.Tile;

/**
 * Class that represents the space (on the game board or on the player grid) where a tile can be placed
 */

public class Spot {
    @Expose
    private boolean occupied;
    @Expose
    private Tile tile;

    public Spot(boolean occupied, Tile tile){
        this.occupied = occupied;
        this.tile = tile;

    }

    /**
     * @return true if the spot is occupied, false otherwise
     */
    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public Tile getTile() {
        return tile;
    }

    /**
     * The method places a tile on the current square and sets the square's occupation status.
     * @param tile that we want to place
     */
    public void placeTile(Tile tile) {
        this.tile = tile;
        if(tile != null)
            setOccupied(true);
    }

    /**
     * The method set the space as an empty Spot
     * @return the tile that was in the Spot
     */
    public Tile removeTile(){
        Tile currentTile = getTile();
        this.occupied = false;
        placeTile(null); //Setting the new space as an empty Spot
        if (currentTile == null){
            throw new IllegalStateException("Tiles not present");
        }
        return currentTile;
    }
}


