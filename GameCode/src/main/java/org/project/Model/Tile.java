package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.Model.Color;

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
