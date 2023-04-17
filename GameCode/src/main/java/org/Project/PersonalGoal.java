package org.Project;

import java.util.HashMap;

public abstract class PersonalGoal {

    private HashMap<Coordinates, Color> personalGoal;

    public abstract void initialize();
    public abstract HashMap<Coordinates, Color> getColoredGoal();
}


