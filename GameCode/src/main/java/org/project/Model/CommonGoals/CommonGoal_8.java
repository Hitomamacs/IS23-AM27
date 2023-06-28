package org.project.Model.CommonGoals;


import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

/**
 * Four tiles of the same type at the four corners of the Library.
 */
public class CommonGoal_8 extends CommonGoal {

    private final int goal_ID = 8;
    public Integer getGoalID() {
        return goal_ID;
    }

    public boolean checkGoal(PlayerGrid playerGrid){

        if(playerGrid.getSpot(new Coordinates(0, 0)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(0, 4)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(5,0)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(5, 4)).isOccupied() &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(0,4)).getTile().getColor()) &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(5,0)).getTile().getColor()) &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(5,4)).getTile().getColor())) {
            return true;
        }
        return false;
    }
}
