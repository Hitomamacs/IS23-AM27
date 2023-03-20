package org.example;

import java.util.ArrayList;

public class CommonGoal_7 extends CommonGoal{
    public boolean checkGoal(PlayerGrid playerGrid)
    {
        int counter=0; //counter for row that verify the goal
        int i=0, j=0; //counters for playergrid

        for(i=0; i<5; i++)
        {
            ArrayList<Integer> color= new ArrayList<Integer>();
            for(j=0; j<6; j++)
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
