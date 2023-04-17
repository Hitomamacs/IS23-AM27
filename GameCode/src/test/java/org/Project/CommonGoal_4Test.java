package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_4Test {

    CommonGoal commonGoal;
    PlayerGrid playerGrid;

    @BeforeEach
    void setUp(){

        commonGoal = new CommonGoal_4();
        playerGrid = new PlayerGrid();
    }

    //test to see that if there are three equal boxes, you consider me only one pair and not both
    @Test
    void test1_CheckGoal(){
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.PINK, Color.PINK, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK, Color.WHITE, Color.GREEN, Color.WHITE},
                {Color.PINK, Color.WHITE, Color.AZURE, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    void test2_CheckGoal(){
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.WHITE, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.PINK, Color.PINK, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK, Color.AZURE, Color.GREEN, Color.WHITE},
                {Color.PINK, Color.PINK, Color.AZURE, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    //test to see if it takes me the zig zag shapes
    @Test
    void test3_CheckGoal(){
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.WHITE, Color.AZURE,Color.PINK,Color.PINK, Color.PINK},
                {Color.YELLOW, Color.GREEN, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.WHITE, Color.PINK},
                {Color.PINK, Color.WHITE, Color.AZURE, Color.GREEN, Color.WHITE},
                {Color.GREEN, Color.GREEN, Color.YELLOW, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
}