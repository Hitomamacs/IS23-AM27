package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    GameBoard Board;
    TileBag bag;
   @BeforeEach
   void setUp() {
       Board = new GameBoard(9,9, 4);
       bag = new TileBag();
       bag.initializeBag();

   }



    @Test
    @DisplayName("Verify Picking 1 Tile")
    void testpick() {
        Board.fillBoard(bag.randomPick(45));
        Board.printMwithTiles();
        Coordinates c1 = new Coordinates(3,3);
        Board.pick(c1);
        System.out.println();
        Board.printMwithTiles();

    }

    @Test
    void testPick1() {
        assertEquals(1,1);
    }

    @Test
    void testPick2() {
        assertEquals(1,1);
    }

    @Test
    void printM() {
        Board.printM();


    }



    @Test
    @DisplayName("Verifing fill matrix")
    void fillBoard() {
        Set<Tile> tiles = new HashSet<>(bag.randomPick(2));
        Set<Tile> tile_copy = new HashSet<>(tiles);
       Board.fillBoard(tile_copy);
       for(int i = 0; i< Board.returndim()[0]; i++){
           for (int j=0; j< Board.returndim()[1]; j++){
               if(Board.getBoard()[i][j].isOccupied() == true){
                   assertTrue(tiles.contains(Board.getBoard()[i][j].getTile()));
               }

           }
       }
    }


    @Test
    @DisplayName("Testing display function Board with Tiles refilling")
    void printMwithTiles() {
        Board.fillBoard(bag.randomPick(10));
        assertTrue(Board.printMwithTiles());


    }
}