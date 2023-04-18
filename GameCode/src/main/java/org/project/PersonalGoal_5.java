package org.project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class PersonalGoal_5 extends PersonalGoal{

    private final int Pgoal_ID = 5;

    @Override
    public int getPgoal_ID() {
        return Pgoal_ID;
    }
    @Expose

    private HashMap<Coordinates, Color> coloredGoal;

    public void initialize() {
        coloredGoal = new HashMap<>();
        coloredGoal.put(new Coordinates(4, 4), Color.PINK);
        coloredGoal.put(new Coordinates(3, 1), Color.BLUE);
        coloredGoal.put(new Coordinates(5, 3), Color.GREEN);
        coloredGoal.put(new Coordinates(3, 2), Color.WHITE);
        coloredGoal.put(new Coordinates(5, 0), Color.YELLOW);
        coloredGoal.put(new Coordinates(1, 1), Color.AZURE);
    }

    public HashMap<Coordinates, Color> getColoredGoal() {
        return coloredGoal;
    }
}