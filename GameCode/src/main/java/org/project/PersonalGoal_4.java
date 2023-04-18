package org.project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class PersonalGoal_4 extends PersonalGoal{

    private final int Pgoal_ID = 4;
    @Expose
    private HashMap<Coordinates, Color> coloredGoal;

    @Override
    public int getPgoal_ID() {
        return Pgoal_ID;
    }

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(3, 3), Color.PINK);
        coloredGoal.put(new Coordinates(2, 2), Color.BLUE);
        coloredGoal.put(new Coordinates(4, 2), Color.GREEN);
        coloredGoal.put(new Coordinates(4, 1), Color.WHITE);
        coloredGoal.put(new Coordinates(0, 4), Color.YELLOW);
        coloredGoal.put(new Coordinates(2, 0), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}