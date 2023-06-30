package org.project.Model.CommonGoals;

import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

import java.util.ArrayList;

/**
 * Three columns each formed by 6 tiles of one, two or three different types.
 * Different columns can have different combinations of tile types.
 */
public class CommonGoal_5 extends CommonGoal {
    private final int goal_ID = 5;
    public Integer getGoalID() {
        return goal_ID;
    }

    public boolean checkGoal(PlayerGrid playerGrid)
    {
        int counter=0; //counter for row that verify the goal
        int i, j; //counters for playergrid

        for(i=0; i<5; i++)
        {
            ArrayList<Integer> color= new ArrayList<>();
            for(j=0; j<6; j++)
            {
                if(!(playerGrid.getSpot(new Coordinates(j,i)).isOccupied()))
                {
                    break;
                }
                else{
                    if(!(color.contains(playerGrid.getSpot(new Coordinates(j,i)).getTile().getColor().ordinal())))
                    {
                        color.add(playerGrid.getSpot(new Coordinates(j,i)).getTile().getColor().ordinal());
                    }
                }
            }
            if(color.size()<=3)
                counter++;
        }
        if(counter>=3)
            return true;
        else return false;

    }
}
