package org.project;

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
        //TODO da rifare

    }



    @Test
    void printM() {
        System.out.println("Print");
        Board.printM();
        System.out.println();


    }

    @Test
    @DisplayName("Testing exception")
    void ExcTest(){
       Coordinates c1 = new Coordinates(3,3);
      Throwable exc = assertThrows(IllegalStateException.class,() -> Board.pick(c1) );

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
        System.out.println("Print with tiles");
        Board.fillBoard(bag.randomPick(10));
        assertTrue(Board.printMwithTiles());


    }

    @Test
    @DisplayName("Testing board check and fill of tiles already on board")
    void checkBoard() {
       Coordinates c1 = new Coordinates(0,3);
        Board.fillBoard(bag.randomPick(1));
        Board.printMwithTiles();
        Board.printBoardColor();
        assertTrue(Board.checkBoard());
        Board.fillBoard(bag.randomPick(1));
        Board.pick(c1);
        assertTrue(Board.checkBoard());
        Board.fillBoard(bag.randomPick(10));
        assertFalse(Board.checkBoard());
    }

    @Test
    @DisplayName("Checking boardnum function")
    void boardCheckNum() {
       Coordinates c1 = new Coordinates(0,0);
       try {
           int n = Board.boardCheckNum();
           assertEquals(n, 45);

       }catch (Exception e){
           System.out.println("Board is does not need to be refilled");
       }

       Board.fillBoard(bag.randomPick(1));
       try {
           int n = Board.boardCheckNum();
           assertEquals(n, 44);
       }catch (Exception e){
           System.out.println("Board  does not need to be refilled");
       }
       Board.fillBoard(bag.randomPick(1));
         try {
              assertThrows(Exception.class, () -> Board.boardCheckNum());

    }catch (Exception e){
        System.out.println("Board  does not need to be refilled");
    }
}

    @Test
    void verifyPickable() {
         Board.fillBoard(bag.randomPick(1));
         Coordinates c1 = new Coordinates(0,3);
         Coordinates c2 = new Coordinates(0,4);
         Coordinates c3 = new Coordinates(2,3);
         Coordinates c4 = new Coordinates(3,2);
         Board.printMwithTiles();
         assertTrue(Board.verifyPickable(c1));
         Board.fillBoard(bag.randomPick(1));
         assertTrue(Board.verifyPickable(c2));
         Board.fillBoard(bag.randomPick(12));
         Board.printMwithTiles();
         Board.printBoardColor();
         assertFalse(Board.verifyPickable(c3));
         assertTrue(Board.verifyPickable(c4));

    }
}