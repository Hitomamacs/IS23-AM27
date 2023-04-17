package org.project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public abstract class PersonalGoal {

    private static int Pgoal_ID;
    @Expose
    private HashMap<Coordinates, Color> personalGoal;

    public static int getPgoal_ID() {
        return Pgoal_ID;
    }

    public static void setPgoal_ID(int pgoal_ID) {
        Pgoal_ID = pgoal_ID;
    }

    public abstract void initialize();
    public abstract HashMap<Coordinates, Color> getColoredGoal();
}


