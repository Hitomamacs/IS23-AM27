package org.project;

import java.util.HashMap;

public class PersonalGoal_12 extends PersonalGoal{

    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(1, 1), Color.PINK);
        coloredGoal.put(new Coordinates(2, 2), Color.BLUE);
        coloredGoal.put(new Coordinates(5, 0), Color.GREEN);
        coloredGoal.put(new Coordinates(0, 2), Color.WHITE);
        coloredGoal.put(new Coordinates(4, 4), Color.YELLOW);
        coloredGoal.put(new Coordinates(3, 3), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
