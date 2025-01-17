package org.project.Model;

import com.google.gson.annotations.Expose;

/**
 * The class manages the couple color-coordinate
 * More Goal make a Personal Goal Card
 */
public class Goal {
    @Expose
    private Coordinates coordinates;
    @Expose
    private Color color;

    public Goal(Coordinates coordinates, Color color) {
        this.coordinates = coordinates;
        this.color = color;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }
}
