package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonGoal_9Test {

    @Test
    //check that goal returns true when the matrix is occupied with only pink tiles
    public void checkTrueWhenAllPink() {
        // create a PlayerGrid with all Pink tiles
        PlayerGrid playerGrid = new PlayerGrid();
        // create a pinkTile
        Tile pinkTile = new Tile(Color.PINK, 0);
        //fill all columns
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                playerGrid.topUp(j, pinkTile);
            }
        }
        // create a CommonGoal_9 instance and check the goal
        CommonGoal_9 commonGoal = new CommonGoal_9();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns true when the matrix is occupied with only green tiles
    public void checkTrueWhenAllGreen(){
        //create a PlayerGrid with all green tiles
        PlayerGrid playerGrid = new PlayerGrid();
        for(int i=0; i<5; i++){
            for(int j=0; j<6; j++){
                playerGrid.getSpot(new Coordinates(i, j)).placeTile(new Tile(Color.GREEN, 0));
            }
        }

        // create a CommonGoal_9 instance and check the goal
        CommonGoal_9 commonGoal = new CommonGoal_9();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when there aren't tiles in the matrix
    public void checkFalseWhenNoColors() {
        // create a PlayerGrid with no occupied tiles
        PlayerGrid playerGrid = new PlayerGrid();

        // create a CommonGoal_9 instance and check the goal
        CommonGoal_9 commonGoal = new CommonGoal_9();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when only some spot are occupied with less than eight tile per color
    public void checkFalseWhenOnlySomeColorsHave8TilesOccupying() {
        // create a PlayerGrid
        PlayerGrid playerGrid = new PlayerGrid();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                // add some occupied tiles of different colors
                if (i == 0 && j < 8) {
                    playerGrid.getSpot(new Coordinates(i, j)).placeTile(new Tile(Color.PINK,0));
                } else if (i == 1 && j < 8) {
                    playerGrid.getSpot(new Coordinates(i, j)).placeTile(new Tile(Color.YELLOW,0));
                } else if (i == 2 && j < 8) {
                    playerGrid.getSpot(new Coordinates(i, j)).placeTile(new Tile(Color.GREEN,0));
                }
            }
        }

        // create a CommonGoal_9 instance and check the goal
        CommonGoal_9 commonGoal = new CommonGoal_9();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }



}