package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.Model.Color;
import org.project.Model.Coordinates;

import java.util.List;
import java.util.Map;

public  class PersonalGoal {


    @Expose
    private int Pgoal_ID ;
    @Expose
    private List<Goal> goals;

    public PersonalGoal(int Pgoal_ID, List<Goal> personalGoal) {
        this.Pgoal_ID = Pgoal_ID;
        this.goals = personalGoal;
    }

    public  int getPgoal_ID() {
        return Pgoal_ID;
    }



    public  void initialize(){

    };
    public  List<Goal> getColoredGoal(){
        return goals;

    }

    public List<Goal> getGoals() {
        return goals;
    }
}


