package org.project.Model.CommonGoals;

import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

import java.util.ArrayList;

/**
 * Two lines formed each from 5 different types of tiles
 */
public class CommonGoal_6 extends CommonGoal {

    private final int goal_ID = 6;
    public Integer getGoalID() {
        return goal_ID;
    }

    public boolean checkGoal(PlayerGrid playerGrid) {

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