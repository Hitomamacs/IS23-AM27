package org.project;

import java.util.ArrayList;

/**
 * This class represents common goal 5 where there should be three columns each consisting of
 * 6 tiles of one, two or three different types.
 * Different columns can have
 * Different combinations of tile types
 */
public class CommonGoal_5 extends CommonGoal{

    /**
     *This method saves tile colors to an arrayList if they were not already present.
     *  If after going through the whole column, the size of the array is less than three, then I update the counter.
     *  At the end the check is done to see if the counter has a value less than or equal to three.
     * @return true if the common goal is completed
     */
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
