package org.Project;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerGridTest {

    PlayerGrid playerGrid;

    @BeforeEach
    void setUp() {
        playerGrid = new PlayerGrid();

    }



    @Test
    @DisplayName("Testing topUp")
    void testTopUp() {
        Coordinates coordinates = new Coordinates(5,0);
        Coordinates coordinates1 = new Coordinates(5,1);
        Coordinates coordinates2 = new Coordinates(4,0);
        Tile tile1 = new Tile(Color.GREEN, 1);
        Tile tile2 = new Tile(Color.AZURE, 2);
        playerGrid.topUp(0,tile1);
        assertEquals(tile1, playerGrid.getSpot(coordinates).getTile());
        playerGrid.topUp(1,tile2);
        assertEquals(tile2, playerGrid.getSpot(coordinates1).getTile());
        playerGrid.topUp(0,tile1);
        assertEquals(tile1, playerGrid.getSpot(coordinates2).getTile());

    }

    @Test
    void spaceCheck() {
        //fill a column with 3 tiles
        Tile tile1 = new Tile(Color.GREEN, 1);
        Tile tile2 = new Tile(Color.AZURE, 2);
        Tile tile3 = new Tile(Color.YELLOW, 3);
        playerGrid.topUp(0,tile1);
        playerGrid.topUp(0,tile2);
        playerGrid.topUp(0,tile3);
        assertTrue(playerGrid.spaceCheck(0, 2));
        assertFalse(playerGrid.spaceCheck(0, 4));
    }

    @Test
    void fullCheck() {
        Tile tile1 = new Tile(Color.GREEN, 1);
        assertFalse(playerGrid.fullCheck());
        //fill all columns
        for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++){
                playerGrid.topUp(j,tile1);
            }
        }
        assertTrue(playerGrid.fullCheck());
        playerGrid.setTile(0,0);
        assertFalse(playerGrid.fullCheck());

    }

    @Test
    void testQuickGridSetter(){

        Color[][] matrix;

        matrix = new Color[][]{{Color.PINK,Color.PINK,Color.GREEN,Color.GREEN,Color.BLUE },
                {Color.WHITE,Color.GREEN,Color.YELLOW,Color.AZURE,Color.PINK},
                {Color.AZURE, Color.PINK,Color.GREEN,Color.PINK,Color.AZURE},
                {Color.PINK,Color.GREEN,Color.YELLOW,Color.AZURE,Color.PINK},
                {Color.BLUE,Color.GREEN,Color.YELLOW,Color.AZURE,Color.PINK},
                {Color.WHITE,Color.AZURE, Color.PINK,Color.GREEN,Color.AZURE}};

        playerGrid.quickGridSetter(matrix);
        playerGrid.printColorPlayerGrid();
    }
}