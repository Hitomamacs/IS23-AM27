package org.Project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public abstract class PersonalGoal {
    @Expose
    private HashMap<Coordinates, Color> personalGoal;

    public abstract void initialize();
    public abstract HashMap<Coordinates, Color> getColoredGoal();
}


