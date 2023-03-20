package org.example;

public class CommonGoal_8 extends CommonGoal{

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
