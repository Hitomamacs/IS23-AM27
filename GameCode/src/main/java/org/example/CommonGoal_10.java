package org.example;
public class CommonGoal_10 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid) {
        int i,j;
        int color;

        for(i=1;i<4;i++){
            for(j=1;j<5;j++){
                color=playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor().ordinal();
                if(playerGrid.getSpot(new Coordinates(i-1,j-1)).getTile().getColor().ordinal()==color &&
                    playerGrid.getSpot(new Coordinates(i-1,j+1)).getTile().getColor().ordinal()==color &&
                    playerGrid.getSpot(new Coordinates(i+1,j-1)).getTile().getColor().ordinal()==color &&
                    playerGrid.getSpot(new Coordinates(i+1,j+1)).getTile().getColor().ordinal()==color){
                    return true;
                }
            }
        }
        return false;
    }
}
