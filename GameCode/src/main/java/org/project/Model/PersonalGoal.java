package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.Model.Color;
import org.project.Model.Coordinates;

import java.util.Map;

public  class PersonalGoal {

    private int Pgoal_ID ;
    @Expose
    private Map<Coordinates, Color> personalGoal;

    public PersonalGoal(int Pgoal_ID, Map<Coordinates, Color> personalGoal) {
        this.Pgoal_ID = Pgoal_ID;
        this.personalGoal = personalGoal;
    }

    public  int getPgoal_ID() {
        return Pgoal_ID;
    }



    public  void initialize(){

    };
    public  Map<Coordinates, Color> getColoredGoal(){
        return personalGoal;

    }
}


