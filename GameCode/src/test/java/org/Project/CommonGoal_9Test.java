package org.Project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommonGoal_9Test {

    @Test
    //check that goal returns true when the matrix is occupied with only pink tiles
    public void checkTrueWhenAllPink() {
        // create a PlayerGrid with all Pink tiles
        PlayerGrid playerGrid = new PlayerGrid();
        // create a pinkTile
        Tile pinkTile = new Tile(Color.PINK, 0);
        //fill all columns with pink tiles
        for (int i = 4; i >= 0; i--) {
            for (int j = 5; j >= 0; j--) {
                playerGrid.topUp(i, pinkTile);
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
        //create a greenTile
        Tile greenTile = new Tile(Color.GREEN, 0);
        //fill all columns with green tiles
        for(int i = 4; i >= 0; i--){
            for(int j = 5; j >= 0; j--){
                playerGrid.topUp(i, greenTile);
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
    public void checkFalseWhenNot8TilesSameColor() {
        // create a PlayerGrid
        PlayerGrid playerGrid = new PlayerGrid();
        for (int i = 5; i > 0; i--) { //iteration row
            for (int j = 0; j < 6; j++) {
                // add some occupied tiles of different colors
                if (i == 4) {
                    playerGrid.topUp(i, new Tile(Color.PINK,0));
                } else if (i == 3) {
                    playerGrid.topUp(i, new Tile(Color.YELLOW,0));
                } else if (i == 2) {
                    playerGrid.topUp(i, new Tile(Color.GREEN,0));
                }
            }
        }

        // create a CommonGoal_9 instance and check the goal
        CommonGoal_9 commonGoal = new CommonGoal_9();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }
}