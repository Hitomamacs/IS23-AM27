package org.example;

import java.util.HashMap;

public class PersonalGoal_6 extends PersonalGoal{

    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(5, 0), Color.PINK);
        coloredGoal.put(new Coordinates(4, 3), Color.BLUE);
        coloredGoal.put(new Coordinates(0, 4), Color.GREEN);
        coloredGoal.put(new Coordinates(2, 3), Color.WHITE);
        coloredGoal.put(new Coordinates(4, 1), Color.YELLOW);
        coloredGoal.put(new Coordinates(0, 2), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
