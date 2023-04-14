package org.Project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class PersonalGoal_8 extends PersonalGoal{

    private static int Pgoal_ID = 8;
    @Expose

    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(3, 0), Color.PINK);
        coloredGoal.put(new Coordinates(0, 4), Color.BLUE);
        coloredGoal.put(new Coordinates(1, 1), Color.GREEN);
        coloredGoal.put(new Coordinates(4, 3), Color.WHITE);
        coloredGoal.put(new Coordinates(5, 3), Color.YELLOW);
        coloredGoal.put(new Coordinates(2, 2), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
