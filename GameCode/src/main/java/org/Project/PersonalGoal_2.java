package org.Project;

import java.util.HashMap;

public class PersonalGoal_2 extends PersonalGoal{
    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(1, 1), Color.PINK);
        coloredGoal.put(new Coordinates(5, 4), Color.BLUE);
        coloredGoal.put(new Coordinates(2, 0), Color.GREEN);
        coloredGoal.put(new Coordinates(3, 4), Color.WHITE);
        coloredGoal.put(new Coordinates(2, 2), Color.YELLOW);
        coloredGoal.put(new Coordinates(4, 3), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
