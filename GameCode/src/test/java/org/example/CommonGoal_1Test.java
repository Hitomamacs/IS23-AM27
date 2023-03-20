package org.example;

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
                {Color.PINK, Color.PINK, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.AZURE, Color.AZURE, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.AZURE, Color.AZURE, Color.AZURE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.AZURE, Color.AZURE, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.getSpot(new Coordinates(4,0)).removeTile();
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

  
}