package org.example;
public class CommonGoal_10 extends CommonGoal{

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
