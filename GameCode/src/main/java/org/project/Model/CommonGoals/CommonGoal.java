package org.project.Model.CommonGoals;

import org.project.Model.PlayerGrid;

/**
 * The class represents a common goal card of the game
 */
public abstract class CommonGoal {
    private  int goal_ID;

    /**
     * The method checks if a player has completed a common goal card
     * @param playerGrid player's grid
     * @return true if the common goal is completed by the player, false otherwise
     */
    public abstract boolean checkGoal(PlayerGrid playerGrid);
    public Integer getGoalID() {
        return goal_ID;
    }
}
