package org.Project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class PersonalGoal_3 extends PersonalGoal{

    private static int Pgoal_ID = 3;
    @Expose
    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(2, 2), Color.PINK);
        coloredGoal.put(new Coordinates(1, 0), Color.BLUE);
        coloredGoal.put(new Coordinates(3, 1), Color.GREEN);
        coloredGoal.put(new Coordinates(5, 0), Color.WHITE);
        coloredGoal.put(new Coordinates(1, 3), Color.YELLOW);
        coloredGoal.put(new Coordinates(3, 4), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}
