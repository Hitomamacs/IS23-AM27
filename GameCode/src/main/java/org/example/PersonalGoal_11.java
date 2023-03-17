package org.example;

import java.util.HashMap;

public class PersonalGoal_11 extends PersonalGoal{

    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(0, 2), Color.PINK);
        coloredGoal.put(new Coordinates(3, 2), Color.BLUE);
        coloredGoal.put(new Coordinates(4, 4), Color.GREEN);
        coloredGoal.put(new Coordinates(1, 1), Color.WHITE);
        coloredGoal.put(new Coordinates(2, 0), Color.YELLOW);
        coloredGoal.put(new Coordinates(5, 3), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
