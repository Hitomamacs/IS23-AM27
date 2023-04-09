package org.example;

public class CommonGoal_12 extends CommonGoal{

    /** method that checks the CommonGoal_12 in which the shape that must
     * be satisfied is represented by a low triangular matrix
     * there are four possible configurations and I consider them as four separate cases:
     * in each of them I check that in each column of the playerGrid there is the correct number
     * of empty spots, otherwise I exit the for loop and I go on
     */

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

    /** method that checks that in a column there is a number of empty places
     * equal to a parameter called n_tiles
     */
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


