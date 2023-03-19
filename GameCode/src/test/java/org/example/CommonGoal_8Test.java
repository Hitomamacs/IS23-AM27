package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonGoal_8Test {

    CommonGoal_8 commonGoal_8;

    @BeforeEach
    void setUp(){
        commonGoal_8 = new CommonGoal_8();
    }

    @Test
    //check that the goal returns true when all corners are occupied with the same color
    public void checkAllCornersOccupiedSameColor() {
        PlayerGrid playerGrid = new PlayerGrid();
        Tile pinkTile = new Tile(Color.PINK, 0);
        playerGrid.getSpot(new Coordinates(0, 0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(4, 0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(0, 5)).placeTile(pinkTile);
        /*for(int i=5; i>0; i--){ //fisso la riga
            for(int j=0; j<1; j++){ //riempio la colonna
                playerGrid.topUp(j, pinkTile);
            }
        }
        for(int i=5; i>0; i--){
            for(int j=4; j<5; j++){
                playerGrid.topUp(j, pinkTile);
            }
        }*/
        playerGrid.getSpot(new Coordinates(4, 5)).placeTile(pinkTile);

        assertTrue(commonGoal_8.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when not all corners are occupied
    public void checkFalseWhenNotAllCornersOccupied() {
        PlayerGrid playerGrid = new PlayerGrid();
        Tile pinkTile = new Tile(Color.PINK, 0);
        playerGrid.getSpot(new Coordinates(0, 0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(0, 5)).placeTile(pinkTile);

        assertFalse(commonGoal_8.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when all corners are occupied with different colors
    public void checkFalseWhenAllCornersAreOccupiedDifferentColor(){
        PlayerGrid playerGrid = new PlayerGrid();
        Tile pinkTile = new Tile(Color.PINK, 0);
        Tile greenTile = new Tile(Color.GREEN, 0);
        playerGrid.getSpot(new Coordinates(0,0)).placeTile(greenTile);
        playerGrid.getSpot(new Coordinates(4,0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(0,5)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(4,5)).placeTile(pinkTile);

        assertFalse(commonGoal_8.checkGoal(playerGrid));
    }
}