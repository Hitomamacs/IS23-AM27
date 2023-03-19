package org.example;

public class CommonGoal_12 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid){
        //counter for the matrix
        int i=0;
        int j=0;
        int counter_1=0, counter_2=0, counter_3=0, counter_4=0;

        for(i=0; i<5; i++){
            for(j=0; j<i; j++){
                if(playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i + 1, j + 1)).isOccupied()){
                    counter_1++;
                }
            }
        }
        if(counter_1==15){
            return true;
        }

        for(i=1; i<5; i++){
            for(j=1; j<6; j++){
                if(playerGrid.getSpot(new Coordinates(i, j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i - 1, j - 1)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i - 1, j - 1)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(i, j)).getTile().getColor()){
                    counter_2++;
                }
            }
        }
        if(counter_2==15){
            return true;
        }
        return false;
    }
}
