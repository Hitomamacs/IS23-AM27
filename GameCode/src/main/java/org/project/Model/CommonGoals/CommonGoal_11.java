package org.project.Model.CommonGoals;


import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

public class CommonGoal_11 extends CommonGoal {

    private final int goal_ID = 11;
    public Integer getGoalID() {
        return goal_ID;
    }
    public boolean checkGoal(PlayerGrid playerGrid) {
        //counters for the matrix
        int i; //row
        int j; //column
        int counterDiagonal_1 = 0, counterDiagonal_2 = 0, counterDiagonal_3=0, counterDiagonal_4=0;


        if(playerGrid.getSpot(new Coordinates(0,0)).isOccupied()){
            counterDiagonal_1++;
            for(i=1; i<5; i++){
                if(playerGrid.getSpot(new Coordinates(i,i)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,i)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor()){
                    counterDiagonal_1++;
                    if(counterDiagonal_1==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(1,0)).isOccupied()){
            counterDiagonal_2++;
            for(i=2, j=1; i<6 && j<5; i++, j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(1,0)).getTile().getColor()){
                    counterDiagonal_2++;
                    if(counterDiagonal_2==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(0,4)).isOccupied()){
            counterDiagonal_3++;
            for(i=1, j=3; i<5 && j>=0; i++, j--){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(0,4)).getTile().getColor()){
                    counterDiagonal_3++;
                    if(counterDiagonal_3==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(1,4)).isOccupied()){
            counterDiagonal_4++;
            for(i=2, j=3; i<6 && j>=0; i++, j--){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(1,4)).getTile().getColor()){
                    counterDiagonal_4++;
                    if(counterDiagonal_4==5){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

