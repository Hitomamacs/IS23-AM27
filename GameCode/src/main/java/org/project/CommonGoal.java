package org.project;


public abstract class CommonGoal {

    private  int goal_ID;
    public abstract boolean checkGoal(PlayerGrid playerGrid);

    public Integer getGoalID() {
        return goal_ID;
    }
}
