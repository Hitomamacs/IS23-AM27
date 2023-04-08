package org.Project;

import com.google.gson.annotations.Expose;

public class Spot {
    @Expose
    private boolean occupied;
    @Expose
    private Tile tile;

    public Spot(boolean occupied, Tile tile){
        this.occupied = occupied;
        this.tile = tile;

    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Tile getTile() {
        return tile;
    }

    public void placeTile(Tile tile) {
        this.tile = tile;
        if(tile != null)
        setOccupied(true);
    }
    /** Class that returns a tile specific in that spot and epties it*/
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


