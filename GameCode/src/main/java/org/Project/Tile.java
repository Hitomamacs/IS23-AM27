package org.Project;

public class Tile {
    private Color color;
    private int idTile;
    public Tile(Color color, int idTile){
        this.color=color;
        this.idTile=idTile;
    }
    public Color getColor() {
        return color;
    }
    public int getIdTile() {
        return idTile;
    }
}
