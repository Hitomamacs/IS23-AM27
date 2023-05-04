package org.project.Model;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

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
}
