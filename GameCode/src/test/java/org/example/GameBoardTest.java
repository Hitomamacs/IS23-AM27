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
       Set<Tile> pickedTile = new HashSet<Tile>();
       Set<Tile> tile = bag.randomPick(1);
       Set<Tile> tile_copy = new HashSet<Tile>(tile);
        Board.fillBoard(tile);
        Board.printMwithTiles();
        Coordinates c1 = new Coordinates(0,3);
        pickedTile = Board.pick(c1);
        System.out.println();
        Board.printMwithTiles();
        assertEquals(pickedTile.iterator().next(), tile_copy.iterator().next());

    }

    @Test
    @DisplayName("Verify Picking 2 Tile")
    void testPick1() {
        Set<Tile> pickedTile = new HashSet<Tile>();
        Set<Tile> tile = bag.randomPick(2);
        Set<Tile> tile_copy = new HashSet<Tile>(tile);
        Board.fillBoard(tile);
        Board.printMwithTiles();
        Coordinates c1 = new Coordinates(0,3);
        Coordinates c2 = new Coordinates(0,4);
        pickedTile = Board.pick(c1,c2);
        System.out.println();
        Board.printMwithTiles();
        assertTrue(pickedTile.containsAll(tile_copy));


    }

    @Test
    @DisplayName("Verify Picking 3 Tile")
    void testPick2() {
        Set<Tile> pickedTile = new HashSet<Tile>();
        Set<Tile> tile = bag.randomPick(3);
        Set<Tile> tile_copy = new HashSet<Tile>(tile);
        Board.fillBoard(tile);
        Board.printMwithTiles();
        Coordinates c1 = new Coordinates(0,3);
        Coordinates c2 = new Coordinates(0,4);
        Coordinates c3 = new Coordinates(1,3);
        pickedTile = Board.pick(c1,c2, c3);
        System.out.println();
        Board.printMwithTiles();
        assertTrue(pickedTile.containsAll(tile_copy));
    }

    @Test
    void printM() {
        Board.printM();


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
        Board.fillBoard(bag.randomPick(10));
        assertTrue(Board.printMwithTiles());


    }
}