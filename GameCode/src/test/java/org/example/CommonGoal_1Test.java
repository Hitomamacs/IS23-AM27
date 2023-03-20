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
    }

    @Test
    boolean test1_CheckGoal(){
        Color[][] matrix;
        boolean result = false;

        matrix = new Color[][]{{Color.PINK, Color.AZURE, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.GREEN, Color.GREEN, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.AZURE, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.AZURE, Color.AZURE, Color.GREEN, Color.WHITE},
                {Color.PINK, Color.AZURE, Color.GREEN, Color.WHITE, Color.PINK}};
        result = commonGoal.checkGoal(playerGrid);
        playerGrid.printColorPlayerGrid();
        return result;
    }

}