package org.example;

public class CommonGoal_8 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid){

        if(playerGrid.getSpot(new Coordinates(0, 0)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(4, 0)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(0,5)).isOccupied() &&
                playerGrid.getSpot(new Coordinates(4, 5)).isOccupied() &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(4,0)).getTile().getColor()) &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(0,5)).getTile().getColor()) &&
                (playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor() ==
                        playerGrid.getSpot(new Coordinates(4,5)).getTile().getColor())) {
            return true;
        }
        return false;
    }
}
