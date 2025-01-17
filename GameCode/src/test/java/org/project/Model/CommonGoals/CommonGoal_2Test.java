package org.project.Model.CommonGoals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Model.Color;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.CommonGoals.CommonGoal_2;
import org.project.Model.Coordinates;
import org.project.Model.PlayerGrid;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_2Test {
    CommonGoal commonGoal;
    PlayerGrid playerGrid;

    @BeforeEach
    void setUp(){
        commonGoal = new CommonGoal_2();
        playerGrid = new PlayerGrid();
    }

    @Test
    void test1_CheckGoal(){
        Color[][] matrix;
        boolean result = false;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.AZURE},
                {Color.GREEN, Color.PINK,Color.WHITE,Color.PINK, Color.GREEN},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.WHITE, Color.WHITE},
                {Color.BLUE, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.PINK, Color.YELLOW, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    void test2_CheckGoal(){
        Color[][] matrix;
        boolean result = false;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.PINK, Color.GREEN},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.WHITE, Color.WHITE},
                {Color.BLUE, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.PINK, Color.YELLOW, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.printColorPlayerGrid();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

}