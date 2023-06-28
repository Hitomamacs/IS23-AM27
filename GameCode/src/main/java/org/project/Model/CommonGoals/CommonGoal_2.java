package org.project.Model.CommonGoals;

/**
 * Two columns formed each from 6 different types of tiles.
 */

import org.project.Model.Color;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

//Goal is to have at least two columns containing all different colors. Since there are six colors
//and columns are 6 spots tall this corresponds to checking that each color appears in the column.
//If each color appears it means it must appear just once
public class CommonGoal_2 extends CommonGoal {

    private final int goal_ID = 2;

    public Integer getGoalID() {
        return goal_ID;
    }

    public boolean checkGoal(PlayerGrid playerGrid) {

        boolean[] color_array = new boolean[6]; //Array of booleans to check if color is in column
        boolean column_correct = true; //boolean to tell if the column satisfies the check
        Coordinates coordinates;
        Color color;
        int count = 0;//Count to check there is at least two columns

        //Iterate through the grid but going down each column
        for(int j = 0; j < 5; j++){
            if(count >= 2)
                break; //Exits the cycle earlier if two correct columns have been found
            column_correct = true;
            for(int k = 0; k < 6; k++)
                color_array[k] = false;  //Reset the boolean array
            for(int i = 0; i < 6; i++){
                coordinates = new Coordinates(i,j);
                if(playerGrid.getSpot(coordinates).isOccupied()){
                    color = playerGrid.getSpot(coordinates).getTile().getColor();
                    color_array[color.ordinal()] = true;
                }
                else break;//Exits the column earlier if a spot of the column is not occupied
            }
            for(int i = 0; i < 6; i++){ //Checks all the colors have been found in the column
                if(!color_array[i])
                    column_correct = false;
            }
            if(column_correct)
                count++;
        }
        if(count >= 2)
            return true;

        else return false;
    }
}
