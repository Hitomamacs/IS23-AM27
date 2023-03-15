package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    GameBoard Board;
    TileBag bag;
   @BeforeEach
   void setUp() {
       Board = new GameBoard(9,9, 4);
       bag = new TileBag();
       bag.initializeBag();
       assertEquals(1,1);
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
       Board.fillBoard(bag.randomPick(45));
       assertEquals(1,1);
    }


}