package org.example;

public class CommonGoal_4 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid) {
        int counter=0; //counter for right pairs
        int i,j; //counter for matrix
        boolean[][] used = new boolean[6][5];

        //check if in the row there are some right pairs
        for(i=0;i<6;i++){
            for(j=0;j<4;j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                        playerGrid.getSpot(new Coordinates(i,j+1)).getTile().getColor().ordinal()
                   && used[i][j]!=true && used[i][j+1]!=true){
                    counter++;
                    used[i][j]=true;
                    used[i][j+1]=true;
                }
            }
        }
        //check if in the column there are some right pairs
        for(i=0;i<5;i++){
            for(j=0;j<5;j++){
                if(playerGrid.getSpot(new Coordinates(j,i)).getTile().getColor().ordinal()==
                        playerGrid.getSpot(new Coordinates(j+1,i)).getTile().getColor().ordinal()
                        && used[j][i]!=true && used[j+1][i]!=true){
                    counter++;
                    used[j][i]=true;
                    used[j+1][i]=true;
                }
            }
        }

        if(counter>=6)
            return true;
        else return false;
    }
}
