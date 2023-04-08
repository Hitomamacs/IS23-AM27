package org.Project;

import com.google.gson.annotations.Expose;

public class Tile {
    @Expose
    private Color color;
    @Expose
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
