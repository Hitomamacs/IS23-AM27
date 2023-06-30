package org.project.Model.CommonGoals;


import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

import java.util.ArrayList;

/**
 * Four lines formed each from 5 tiles of one, two or three types
 * different. Different lines may have different combinations of tile types
 */
public class CommonGoal_7 extends CommonGoal {

    private final int goal_ID = 7;
    public Integer getGoalID() {
        return goal_ID;
    }
    public boolean checkGoal(PlayerGrid playerGrid)
    {
        int counter=0;
        int checkPiena=0; //counter for row that verify the goal
        int i, j; //counters for playergrid

        for(i=0; i<6; i++)
        {
            checkPiena=0;
            ArrayList<Integer> color= new ArrayList<Integer>();
            for(j=0; j<5; j++)
            {
                if((playerGrid.getSpot(new Coordinates(i,j)).isOccupied()))
                {
                    if(!(color.contains(playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal())))
                    {
                        color.add(playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal());
                    }
                }
                checkPiena++;
            }
            if(color.size()<=3 && checkPiena==5)
                counter++;
        }
        if(counter>=4)
            return true;
        else return false;

    }
}
