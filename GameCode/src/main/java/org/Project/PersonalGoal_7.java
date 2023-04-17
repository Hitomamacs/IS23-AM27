package org.Project;

import java.util.HashMap;

public class PersonalGoal_7 extends PersonalGoal{
    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(2, 1), Color.PINK);
        coloredGoal.put(new Coordinates(1, 3), Color.BLUE);
        coloredGoal.put(new Coordinates(0, 0), Color.GREEN);
        coloredGoal.put(new Coordinates(5, 2), Color.WHITE);
        coloredGoal.put(new Coordinates(4, 4), Color.YELLOW);
        coloredGoal.put(new Coordinates(3, 0), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
