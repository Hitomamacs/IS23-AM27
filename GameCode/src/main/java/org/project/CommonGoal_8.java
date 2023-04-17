package org.project;

public class CommonGoal_8 extends CommonGoal{

    /** method that verifies the CommonGoal_8 in which the
     * four corners of the playerGrid must have tiles of the same color
     * I check if the Spot in position 0,0 in the playerGrid is occupied, if not, I exit,
     * if it is, I check the color of the Tile and do the same check on the other three corners
     * of the playerGrid
     */
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
