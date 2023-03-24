package org.example;

public class CommonGoal_12 extends CommonGoal{

    public boolean checkGoal(PlayerGrid playerGrid){
        //counter for the matrix
 /*       int i=0;
        int j=0;
        int counter_1=0, counter_2=0, counter_3=0, counter_4=0;

        //first case
        for(i = 1; i < 6; i++) {
            for (j = 0; j < 5; j++) {
                if((j >= i) && (playerGrid.getSpot(new Coordinates(i, j)).isOccupied())){
                    break;
                }

                if((j < i) && (playerGrid.getSpot(new Coordinates(i, j)).isOccupied())){
                    counter_1++;
                    if (counter_1 == 15) {
                        return true;
                    }
                }
            }
        }

        //second case
        for(i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if((j > i) && (playerGrid.getSpot(new Coordinates(i, j)).isOccupied())){
                    break;
                }

                if((j <= i) && (playerGrid.getSpot(new Coordinates(i, j)).isOccupied())){
                    counter_2++;
                    if (counter_2 == 15) {
                        return true;
                    }
                }
            }
        }

        //third case
        for(i = 1; i < 6; i++){
            for(j = 4; j >= 0; j--){
                if();
            }
        }
    */
        /*for(i = 0; i < 5; i++){
            for(j = 4; j >= i; j--){
                if(playerGrid.getSpot(new Coordinates(i, j)).isOccupied()){
                    counter_3++;
                    if(counter_3==15){
                        return true;
                    }
                }
            }
        }*/

        /*for(i = 1; i < 6; i++){
            for(j = 0; j < 5; j++){
                if(5-i>j){

                }
            }
        }
        for(i = 5; i > 0; i--){
            for(j = i-1; j >= 0; j--){
                if(playerGrid.getSpot(new Coordinates(i, j)).isOccupied()){
                    counter_4++;
                    if(counter_4==15){
                        return true;
                    }
                }
            }
        }*/

        return false;
    }


}
