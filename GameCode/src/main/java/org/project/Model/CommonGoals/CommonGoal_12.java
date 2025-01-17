package org.project.Model.CommonGoals;


import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

/**
 * Five columns of increasing height or descending: starting from the first column left or right,
 * each successive column it must be formed by an extra tile.
 * Tiles can be of any type.
 */
public class CommonGoal_12 extends CommonGoal {
    private final int goal_ID = 12;

    public Integer getGoalID() {
        return goal_ID;
    }
    public boolean checkGoal(PlayerGrid playerGrid){
        //counter for the column of the matrix
        int j=0;

        //first case
        for(j = 0; j < 5; j++){
            if(!mySpaceCheck(j, j + 1, playerGrid)){
                break;
            }
            if(j == 4){
                return true;
            }
        }

        //second case
        for(j = 0; j < 5; j++){
            if(!mySpaceCheck(j, j, playerGrid)){
                break;
            }
            if(j == 4){
                return true;
            }
        }

        //third case
        for(j = 0; j < 5; j++){
            if(!mySpaceCheck(j, 5-j, playerGrid)){
                break;
            }
            if(j==4){
                return true;
            }
        }

        //fourth case
        for(j = 0; j < 5; j++){
            if(!mySpaceCheck(j, 4-j, playerGrid)){
                break;
            }
            if(j==4){
                return true;
            }
        }
        return false;
    }

    public boolean mySpaceCheck(int column, int n_tiles, PlayerGrid playerGrid) {

        if (column < 0 || column > 4) {
            throw new IllegalArgumentException("Column must be between 0 and 5");
        }
        int counter = 0;
        for (int i = 0; i <= 5; i++) {
            if (!playerGrid.getSpot(new Coordinates(i, column)).isOccupied()) {
                counter++;
            }
        }
        if (counter == n_tiles) {
            return true;
        }
        else {
            return false;
        }
    }
}


