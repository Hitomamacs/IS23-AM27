package org.example;

/**
 * this class represents common goal 10 where there must be five tiles of the same type
 * forming an X.
 */
public class CommonGoal_10 extends CommonGoal{

    /**
     *This method runs along a smaller matrix (where the centers of the X-rays can be).
     * After checking that the positions are not empty, check that they all have the same color.
     * @return true if the common goal is completed
     */
    public boolean checkGoal(PlayerGrid playerGrid) {
        int i,j;
        int color;

        //I scroll through the centers of the star that are on a smaller matrix
        for(i=1;i<5;i++){
            for(j=1;j<4;j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i-1,j-1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i-1,j+1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i+1,j-1)).isOccupied() &&
                    playerGrid.getSpot(new Coordinates(i+1,j+1)).isOccupied())
                {
                    color=playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal();
                    if(playerGrid.getSpot(new Coordinates(i-1,j-1)).getTile().getColor().ordinal()==color &&
                            playerGrid.getSpot(new Coordinates(i-1,j+1)).getTile().getColor().ordinal()==color &&
                            playerGrid.getSpot(new Coordinates(i+1,j-1)).getTile().getColor().ordinal()==color &&
                            playerGrid.getSpot(new Coordinates(i+1,j+1)).getTile().getColor().ordinal()==color){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
