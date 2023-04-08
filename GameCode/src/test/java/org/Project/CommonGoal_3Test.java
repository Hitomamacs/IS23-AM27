package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_3Test {

    Color[][] matrix;

    @Test
    //check pink(4), green(4), blue(4), white(4) is true
    public void check1(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.BLUE, Color.PINK, Color.PINK, Color.AZURE, Color.GREEN},
                {Color.AZURE, Color.YELLOW, Color.BLUE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.BLUE, Color.BLUE, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_3 instance and check the goal
        CommonGoal_3 commonGoal = new CommonGoal_3();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check pink(4), green(4), blue(4) is false
    public void check2(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.BLUE, Color.PINK, Color.PINK, Color.AZURE, Color.GREEN},
                {Color.AZURE, Color.YELLOW, Color.BLUE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.YELLOW},
                {Color.YELLOW, Color.BLUE, Color.BLUE, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_3 instance and check the goal
        CommonGoal_3 commonGoal = new CommonGoal_3();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check pink(4), green(4), blue(4), white(4), azure(4) is false
    public void check3(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.BLUE, Color.PINK, Color.PINK, Color.AZURE, Color.GREEN},
                {Color.AZURE, Color.YELLOW, Color.BLUE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.BLUE, Color.BLUE, Color.YELLOW, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_3 instance and check the goal
        CommonGoal_3 commonGoal = new CommonGoal_3();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }
    @Test
    //check pink(4), green(4), blue(4), white(5) is false
    public void check4(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.BLUE, Color.PINK, Color.PINK, Color.AZURE, Color.GREEN},
                {Color.AZURE, Color.YELLOW, Color.BLUE, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.BLUE, Color.BLUE, Color.WHITE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_3 instance and check the goal
        CommonGoal_3 commonGoal = new CommonGoal_3();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

}