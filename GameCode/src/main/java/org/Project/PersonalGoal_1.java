package org.Project;

import java.util.HashMap;

public class PersonalGoal_1 extends PersonalGoal{
    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(0, 0), Color.PINK);
        coloredGoal.put(new Coordinates(0, 2), Color.BLUE);
        coloredGoal.put(new Coordinates(1, 4), Color.GREEN);
        coloredGoal.put(new Coordinates(2, 3), Color.WHITE);
        coloredGoal.put(new Coordinates(3, 1), Color.YELLOW);
        coloredGoal.put(new Coordinates(5, 2), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
