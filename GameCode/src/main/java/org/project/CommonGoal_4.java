package org.project;

/**
 * method that represents the fourth common goal, that is, there must be six separate groups formed each
 * from two adjacent tiles of the same type.Tiles in a group can
 * be different from those of another group.
 */
public class CommonGoal_4 extends CommonGoal{

    /**
     *This method first checks if there are tiles in the row that complete the objective,
     *  if so I put the Boolean matrix in the positions of the tiles to true.
     *  In the second cycle check if there are any tiles that complete the objective in the column.
     *  In the first cycle the control is also done with the following configurations
     *  $    |  $ |$$$ | $$$
     *  $$$  |$$$ |$   |   $
     * @return true if the common goal is completed
     */
    public boolean checkGoal(PlayerGrid playerGrid) {
        int counter=0; //counter for right pairs
        int i,j; //counter for matrix
        boolean[][] used = new boolean[6][5];

        //check if in the row there are some right pairs
        for(i=0;i<6;i++){
            for(j=0;j<4;j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() && playerGrid.getSpot(new Coordinates(i,j+1)).isOccupied()&&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                        playerGrid.getSpot(new Coordinates(i,j+1)).getTile().getColor().ordinal()
                   && used[i][j]!=true && used[i][j+1]!=true){
                    if(j+2<5 && playerGrid.getSpot(new Coordinates(i,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i+1,j)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                            playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal() &&
                            playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                            playerGrid.getSpot(new Coordinates(i+1,j)).getTile().getColor().ordinal()){
                        counter+=2;
                        used[i][j]=true;
                        used[i][j+1]=true;
                        used[i+1][j]=true;
                        used[i][j+2]=true;
                    } else if(j+2<5 && playerGrid.getSpot(new Coordinates(i,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i+1,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                            playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal() &&
                            playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal()==
                            playerGrid.getSpot(new Coordinates(i+1,j+2)).getTile().getColor().ordinal()){
                        counter+=2;
                        used[i][j]=true;
                        used[i][j+1]=true;
                        used[i+1][j+2]=true;
                        used[i][j+2]=true;
                    }else if(j+2<5 && i-1>0 && playerGrid.getSpot(new Coordinates(i,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i-1,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                                    playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal() &&
                            playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal()==
                                    playerGrid.getSpot(new Coordinates(i-1,j+2)).getTile().getColor().ordinal()){
                        counter+=2;
                        used[i][j]=true;
                        used[i][j+1]=true;
                        used[i-1][j+2]=true;
                        used[i][j+2]=true;
                    }else if(j+2<5 && i-1>0&& playerGrid.getSpot(new Coordinates(i,j+2)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i-1,j)).isOccupied() &&
                            playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal()==
                                    playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal() &&
                            playerGrid.getSpot(new Coordinates(i,j+2)).getTile().getColor().ordinal()==
                                    playerGrid.getSpot(new Coordinates(i-1,j)).getTile().getColor().ordinal()){
                        counter+=2;
                        used[i][j]=true;
                        used[i][j+1]=true;
                        used[i-1][j]=true;
                        used[i][j+2]=true;
                    }else{
                        counter++;
                        used[i][j]=true;
                        used[i][j+1]=true;
                    }
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
