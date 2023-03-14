package org.example;

public class Spot {
    private boolean occupied;
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
    }
    /** Class that returns a tile specifi in that spot and epties it*/
    public Tile removeTile(){
        setOccupied(false);
        Tile currentTile = getTile();
        placeTile(null); //Setting the new space as an empty Spot
        return currentTile;
    }
}


