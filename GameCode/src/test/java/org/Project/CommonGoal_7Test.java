package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_7Test {

    CommonGoal commonGoal;
    PlayerGrid playerGrid;
    @BeforeEach
    void setUp() {
        commonGoal = new CommonGoal_7();
        playerGrid = new PlayerGrid();
    }


    @Test
    void test1_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.PINK, Color.PINK, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK, Color.WHITE, Color.WHITE, Color.WHITE},
                {Color.PINK, Color.WHITE, Color.AZURE, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test2_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK,Color.WHITE,Color.AZURE, Color.GREEN},
                {Color.PINK, Color.PINK, Color.AZURE, Color.YELLOW, Color.WHITE},
                {Color.PINK, Color.YELLOW, Color.GREEN, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK, Color.WHITE, Color.WHITE, Color.WHITE},
                {Color.PINK, Color.WHITE, Color.AZURE, Color.WHITE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    void test3_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK},
                {Color.PINK, Color.PINK,Color.PINK,Color.PINK, Color.PINK},
                {Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK},
                {Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK},
                {Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK},
                {Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
}