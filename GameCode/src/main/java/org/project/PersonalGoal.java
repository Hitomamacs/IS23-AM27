package org.project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public abstract class PersonalGoal {

    private  int Pgoal_ID ;
    @Expose
    private HashMap<Coordinates, Color> personalGoal;

    public  int getPgoal_ID() {
        return Pgoal_ID;
    }



    public abstract void initialize();
    public abstract HashMap<Coordinates, Color> getColoredGoal();
}


