package org.Project;

public class CommonGoal_8 extends CommonGoal{

    private static int goal_ID = 8;

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
