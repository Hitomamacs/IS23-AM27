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
        playerGrid.getSpot(new Coordinates(0, 4)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(5, 0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(5, 4)).placeTile(pinkTile);

        assertTrue(commonGoal_8.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when not all corners are occupied
    public void checkFalseWhenNotAllCornersOccupied() {
        PlayerGrid playerGrid = new PlayerGrid();
        Tile pinkTile = new Tile(Color.PINK, 0);
        playerGrid.getSpot(new Coordinates(5, 0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(5, 4)).placeTile(pinkTile);

        assertFalse(commonGoal_8.checkGoal(playerGrid));
    }

    @Test
    //check that goal returns false when all corners are occupied with different colors
    public void checkFalseWhenAllCornersAreOccupiedDifferentColor(){
        PlayerGrid playerGrid = new PlayerGrid();
        Tile pinkTile = new Tile(Color.PINK, 0);
        Tile greenTile = new Tile(Color.GREEN, 0);
        playerGrid.getSpot(new Coordinates(0,0)).placeTile(greenTile);
        playerGrid.getSpot(new Coordinates(0,4)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(5,0)).placeTile(pinkTile);
        playerGrid.getSpot(new Coordinates(5,4)).placeTile(pinkTile);
    }
}