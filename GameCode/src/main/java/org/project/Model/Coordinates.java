package org.project.Model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * The Coordinates class represents the coordinates of the tiles in the game board or in the player's grid.
 * It implements the Serializable interface, which means that objects of this class can be serialized and deserialized.
 */

public class Coordinates implements Serializable {
    @Expose
    private int x;
    @Expose
    private int y;
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinates other = (Coordinates) obj;
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
