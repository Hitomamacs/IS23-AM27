package org.Project;

import java.util.ArrayList;

public class CommonGoal_7 extends CommonGoal{

    private static int goal_ID = 7;
    public boolean checkGoal(PlayerGrid playerGrid)
    {
        int counter=0; //counter for row that verify the goal
        int i, j; //counters for playergrid

        for(i=0; i<6; i++)
        {
            ArrayList<Integer> color= new ArrayList<Integer>();
            for(j=0; j<5; j++)
            {
                if(!(playerGrid.getSpot(new Coordinates(i,j)).isOccupied()))
                {
                    break;
                }
                else{
                    if(!(color.contains(playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal())))
                    {
                        color.add(playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal());
                    }
                }
            }
            if(color.size()<=3)
                counter++;
        }
        if(counter>=4)
            return true;
        else return false;

    }
}
