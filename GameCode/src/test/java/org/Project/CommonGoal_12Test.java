package org.Project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_12Test {

    Color[][] matrix;

    @Test
    //check the first matrix
    public void checkFirstMatrixTrue(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,1)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,2)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,3)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();
        playerGrid.getSpot(new Coordinates(4,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the first matrix
    public void checkFirstMatrixFalse(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,1)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,2)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,3)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();
        playerGrid.getSpot(new Coordinates(4,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the first matrix
    public void checkFirstMatrixFalse2 (){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,1)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,2)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,3)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();
        playerGrid.getSpot(new Coordinates(4,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the second matrix
    public void checkSecondMatrixTrue(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the second matrix
    public void checkSecondMatrixFalse(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check the second matrix
    //there are some spots that are occupied (but they can't be occupied)
    public void checkSecondMatrixFalse2(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(2,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }


    @Test
    //check third matrix true
    public void checkThirdMatrixTrue(){

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

        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,0)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,1)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(2,0)).removeTile();
        playerGrid.getSpot(new Coordinates(2,1)).removeTile();
        playerGrid.getSpot(new Coordinates(2,2)).removeTile();
        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(3,1)).removeTile();
        playerGrid.getSpot(new Coordinates(4,0)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertTrue(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check third matrix false
    public void checkThirdMatrixFalse(){

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

        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,2)).removeTile();
        playerGrid.getSpot(new Coordinates(0,3)).removeTile();
        playerGrid.getSpot(new Coordinates(0,4)).removeTile();
        playerGrid.getSpot(new Coordinates(1,0)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,1)).removeTile();
        playerGrid.getSpot(new Coordinates(2,2)).removeTile();
        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(3,1)).removeTile();
        playerGrid.getSpot(new Coordinates(4,0)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check fourth diagonal false
    public void checkFourthMatrixFalse(){

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

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }

    @Test
    //check matrix false
    public void checkMatrixFalse(){

        PlayerGrid playerGrid = new PlayerGrid();
        matrix= new Color[][]{
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.GREEN, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.PINK, Color.PINK, Color.AZURE, Color.WHITE},
                {Color.PINK, Color.GREEN, Color.PINK, Color.PINK, Color.WHITE},
                {Color.PINK, Color.BLUE, Color.GREEN, Color.AZURE, Color.PINK},
                {Color.YELLOW, Color.BLUE, Color.GREEN, Color.AZURE, Color.WHITE},
        };

        playerGrid.quickGridSetter(matrix);

        playerGrid.getSpot(new Coordinates(3,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,0)).removeTile();
        playerGrid.getSpot(new Coordinates(0,1)).removeTile();
        playerGrid.getSpot(new Coordinates(1,2)).removeTile();
        playerGrid.getSpot(new Coordinates(1,3)).removeTile();
        playerGrid.getSpot(new Coordinates(1,4)).removeTile();
        playerGrid.getSpot(new Coordinates(3,3)).removeTile();
        playerGrid.getSpot(new Coordinates(3,4)).removeTile();
        playerGrid.getSpot(new Coordinates(4,4)).removeTile();

        playerGrid.printColorPlayerGrid();

        // create a CommonGoal_12 instance and check the goal
        CommonGoal_12 commonGoal = new CommonGoal_12();
        assertFalse(commonGoal.checkGoal(playerGrid));
    }
}



