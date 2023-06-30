package org.project.Model;

import com.google.gson.annotations.Expose;
import org.project.Model.Color;
import org.project.Model.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * The class represents a single personal goal card
 */

public  class PersonalGoal {

    /**
     * ID of the personal goal card
     */
    @Expose
    private int Pgoal_ID ;

    /**
     * List of Goal that are the color goals of the personal goal card.
     */
    @Expose
    private List<Goal> goals;

    /**
     * Constructor
     * @param Pgoal_ID
     * @param personalGoal
     */
    public PersonalGoal(int Pgoal_ID, List<Goal> personalGoal) {
        this.Pgoal_ID = Pgoal_ID;
        this.goals = personalGoal;
    }

    public  int getPgoal_ID() {
        return Pgoal_ID;
    }
    public  List<Goal> getColoredGoal(){
        return goals;
    }
    public List<Goal> getGoals() {
        return goals;
    }
}
