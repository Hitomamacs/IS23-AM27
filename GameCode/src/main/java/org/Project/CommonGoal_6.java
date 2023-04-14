package org.Project;

import java.util.ArrayList;

public class CommonGoal_6 extends CommonGoal{
    private static int goal_ID = 6;

    public boolean checkGoal(PlayerGrid playerGrid) {
        /*int counter=0; //counter for row that verify the goal
        int i=0, j=0; //counters for playergrid
        int k=0; //counter for array
        int s=0; //counter for array
        int uguali=0;

        int[] arrayColor={-1,-1,-1,-1,-1};

        for(i=0; i<6; i++)
        {
            uguali=0;
            for(j=0; j<5; j++)
            {
                if(!(playerGrid.getSpot(new Coordinates(i,j)).isOccupied()))
                {
                    arrayColor[j]=-1;
                }
                else{
                    for(k=0; k<5;k++)
                    {
                        arrayColor[k]= playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal();
                    }
                }
            }

            for(k=0;k<5;k++){

                if(arrayColor[k]==-1){
                    uguali=1;
                    break;
                }
                for(s=0;s<5;s++)
                {
                    if(arrayColor[k]==arrayColor[s])
                    {
                        uguali=1;
                    }
                }
            }
            if(uguali!=1)
                counter++;
        }

        if(counter>=2)
            return true;
        else
            return false;
    }*/
        int counter = 0; //counter for row that verify the goal
        int i, j; //counters for playergrid

        for (i = 0; i < 6; i++) {
            ArrayList<Integer> color = new ArrayList<Integer>();
            for (j = 0; j < 5; j++) {
                if (!(playerGrid.getSpot(new Coordinates(i, j)).isOccupied())) {
                    break;
                } else {
                    if (!(color.contains(playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor().ordinal()))) {
                        color.add(playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor().ordinal());
                    }
                }
            }
            if (color.size() == 5)
                counter++;
        }
        if (counter >= 2)
            return true;
        else return false;
    }
}
