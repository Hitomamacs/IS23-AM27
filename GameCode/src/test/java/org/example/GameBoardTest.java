package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    void pick() {
        assertEquals(1,1);
    }

    @Test
    void testPick() {
        assertEquals(1,1);
    }

    @Test
    void testPick1() {
        assertEquals(1,1);
    }

    @Test
    void printM() {
        Board.printM();


    }



    @Test
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


}