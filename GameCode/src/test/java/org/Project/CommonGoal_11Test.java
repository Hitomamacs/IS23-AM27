package org.Project;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CommonGoal_11Test {
    Color[][] matrix;

    @Test
    //check the first diagonal
    public void checkFirstDiagonal(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.BLUE, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.AZURE, Color.BLUE, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_11 instance and check the goal
        CommonGoal_11 commonGoal = new CommonGoal_11();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the second diagonal
    public void checkSecondDiagonal(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.BLUE, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.YELLOW, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.PINK, Color.GREEN, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.WHITE},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_11 instance and check the goal
        CommonGoal_11 commonGoal = new CommonGoal_11();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check third diagonal
    public void checkThirdDiagonal(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.BLUE, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.YELLOW, Color.GREEN, Color.WHITE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.WHITE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.WHITE, Color.PINK, Color.GREEN, Color.WHITE},
                {Color.WHITE, Color.BLUE, Color.GREEN, Color.GREEN, Color.WHITE},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_11 instance and check the goal
        CommonGoal_11 commonGoal = new CommonGoal_11();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check fourth diagonal
    public void checkFourthDiagonal(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.BLUE, Color.BLUE, Color.GREEN, Color.AZURE, Color.GREEN},
                {Color.PINK, Color.YELLOW, Color.GREEN, Color.WHITE, Color.GREEN},
                {Color.AZURE, Color.PINK, Color.WHITE, Color.GREEN, Color.WHITE},
                {Color.PINK, Color.WHITE, Color.GREEN, Color.GREEN, Color.WHITE},
                {Color.WHITE, Color.GREEN, Color.GREEN, Color.GREEN, Color.WHITE},
                {Color.GREEN, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_11 instance and check the goal
        CommonGoal_11 commonGoal = new CommonGoal_11();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
}