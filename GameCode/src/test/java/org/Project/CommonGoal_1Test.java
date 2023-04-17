package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_1Test {
    CommonGoal commonGoal;
    PlayerGrid playerGrid;

    @BeforeEach
    void setUp(){

        commonGoal = new CommonGoal_1();
        playerGrid = new PlayerGrid();
    }
    
    @Test
    void test1_CheckGoal(){
        Color[][] matrix;
        boolean result = false;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.GREEN, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.PINK, Color.PINK, Color.AZURE, Color.WHITE, Color.WHITE},
                {Color.PINK, Color.AZURE, Color.GREEN, Color.WHITE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        //playerGrid.getSpot(new Coordinates(4,0)).removeTile();
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test2_CheckGoal(){
        playerGrid = new PlayerGrid();
        playerGrid.getSpot(new Coordinates(4,0)).placeTile(new Tile(Color.YELLOW, 0));
        playerGrid.getSpot(new Coordinates(5,0)).placeTile(new Tile(Color.YELLOW, 0));
        playerGrid.getSpot(new Coordinates(4,1)).placeTile(new Tile(Color.YELLOW, 0));
        playerGrid.getSpot(new Coordinates(5,1)).placeTile(new Tile(Color.YELLOW, 0));
        playerGrid.getSpot(new Coordinates(1,4)).placeTile(new Tile(Color.AZURE, 0));
        playerGrid.getSpot(new Coordinates(2,3)).placeTile(new Tile(Color.AZURE, 0));
        playerGrid.getSpot(new Coordinates(1,3)).placeTile(new Tile(Color.AZURE, 0));
        playerGrid.getSpot(new Coordinates(2,4)).placeTile(new Tile(Color.AZURE, 0));
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));

    }


}