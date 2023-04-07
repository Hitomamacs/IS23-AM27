package org.example;

import java.util.ArrayList;

/**
 * this class represents common goal 6 where there must be two lines formed each
 * from 5 different types of tiles.
 */

public class CommonGoal_7 extends CommonGoal{
    /**
     *This method saves tile colors to an arrayList if they were not already present.
     *If after going through the whole row, the size of the array is less than three, then I update the counter.
     *At the end the check is done to see if the counter has a value less than or equal to four.
     * @return true if the common goal is completed
     */
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
