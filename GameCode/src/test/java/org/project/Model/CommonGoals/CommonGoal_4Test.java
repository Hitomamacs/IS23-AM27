package org.project.Model.CommonGoals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Model.Color;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.CommonGoals.CommonGoal_4;
import org.project.Model.PlayerGrid;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_4Test {

    CommonGoal commonGoal;
    PlayerGrid playerGrid;

    @BeforeEach
    void setUp(){

        commonGoal = new CommonGoal_4();
        playerGrid = new PlayerGrid();
    }

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

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.WHITE, Color.WHITE,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.AZURE, Color.AZURE, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.WHITE},
                {Color.AZURE, Color.AZURE, Color.AZURE, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

}