package org.example;

public class CommonGoal_11 extends CommonGoal{

    /**
     * method that checks the CommonGoal_11 in which the shape that must be
     * satisfied is represented by a diagonal of five elements
     * there are four possible diagonals that can be identified in the playerGrid and
     * I treat them as four separate cases: first I identify the first Spot where a Tile should be,
     * if it isn't there I interrupt, if there is I continue along the diagonal and check that the
     * Spots visited are occupied and that the Tiles have the same color as the first one I visited
     */

    public boolean checkGoal(PlayerGrid playerGrid) {
        //counters for the matrix
        int i; //row
        int j; //column
        int counterDiagonal_1 = 0, counterDiagonal_2 = 0, counterDiagonal_3=0, counterDiagonal_4=0;

        if(playerGrid.getSpot(new Coordinates(0,0)).isOccupied()){
            counterDiagonal_1++;
            for(i=1; i<5; i++){
                if(playerGrid.getSpot(new Coordinates(i,i)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,i)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(0,0)).getTile().getColor()){
                    counterDiagonal_1++;
                    if(counterDiagonal_1==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(1,0)).isOccupied()){
            counterDiagonal_2++;
            for(i=2, j=1; i<6 && j<5; i++, j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(1,0)).getTile().getColor()){
                    counterDiagonal_2++;
                    if(counterDiagonal_2==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(0,4)).isOccupied()){
            counterDiagonal_3++;
            for(i=1, j=3; i<5 && j>=0; i++, j--){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(0,4)).getTile().getColor()){
                    counterDiagonal_3++;
                    if(counterDiagonal_3==5){
                        return true;
                    }
                }
            }
        }

        if(playerGrid.getSpot(new Coordinates(1,4)).isOccupied()){
            counterDiagonal_4++;
            for(i=2, j=3; i<6 && j>=0; i++, j--){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() &&
                        playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor() ==
                                playerGrid.getSpot(new Coordinates(1,4)).getTile().getColor()){
                    counterDiagonal_4++;
                    if(counterDiagonal_4==5){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

