package org.Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_10Test {

    CommonGoal commonGoal;
    PlayerGrid playerGrid;
    @BeforeEach
    void setUp() {
        commonGoal = new CommonGoal_10();
        playerGrid = new PlayerGrid();
    }
    //test 1-4 for testing corners
    @Test
    void test1_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test2_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.AZURE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test3_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test4_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.WHITE, Color.PINK},
                {Color.PINK, Color.PINK, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    void test5_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.YELLOW, Color.WHITE, Color.YELLOW, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.YELLOW, Color.WHITE, Color.YELLOW, Color.AZURE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    void test6_checkGoal() {
        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.GREEN, Color.YELLOW, Color.WHITE, Color.AZURE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }
}