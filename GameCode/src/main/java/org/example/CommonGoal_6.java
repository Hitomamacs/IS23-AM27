package org.example;

public class CommonGoal_6 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid)
    {
        int counter=0; //counter for row that verify the goal
        int i=0, j=0; //counters for playergrid
        int k=0; //counter for array
        int s=0; //counter for array
        int uguali=0;

        int[] arrayColor={-1,-1,-1,-1,-1};

        for(i=0; i<5; i++)
        {
            uguali=0;
            for(j=0; j<6; j++)
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
    }
}
