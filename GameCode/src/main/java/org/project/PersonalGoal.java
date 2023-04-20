package org.project;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public  class PersonalGoal {

    private int Pgoal_ID ;
    @Expose
    private HashMap<Coordinates, Color> personalGoal;

    public PersonalGoal(int Pgoal_ID, HashMap<Coordinates, Color> personalGoal) {
        this.Pgoal_ID = Pgoal_ID;
        this.personalGoal = personalGoal;
    }

    public  int getPgoal_ID() {
        return Pgoal_ID;
    }



    public  void initialize(){

    };
    public  HashMap<Coordinates, Color> getColoredGoal(){
        return personalGoal;

    }
}


