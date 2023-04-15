package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    @BeforeEach
    void setUp() {
        player = new Player("randomName");
    }

    @Test
    void testChangeScore() {
        player.setScore(0);
        player.changeScore(5);
        assertEquals(player.getScore(), 5);
        player.changeScore(0);
        assertEquals(player.getScore(), 5);
        //TODO test for exception missing if I pass negative int
    }
    @Test
    void testExceptionChangeScore(){
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{player.changeScore(-1);} );
    }
    @Test
    void testModifyCompletedCGoals(){
        boolean[] test;
         test = player.getCompletedCGoals();
         assertFalse(test[0]);
         assertFalse(test[1]);
         player.modifyCompletedCGoals(0);
         test = player.getCompletedCGoals();//makes no sense now as getter is returning a direct reference but in case
                                            //getter is changed to return a copy
         assertTrue(test[0]);
         assertFalse(test[1]);
         player.modifyCompletedCGoals(1);
         test = player.getCompletedCGoals();
         assertTrue(test[0]);
         assertTrue(test[1]);
         //TODO test exception in case position is not in {0,1}
    }
    @Test
    void testExceptionModifyCompletedCGoals(){
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{player.modifyCompletedCGoals(2);} );
    }
    @Test
    void testModifyPickedTiles(){
        List<Tile> tiles;
        tiles = new ArrayList<>();

        tiles.add(new Tile(Color.PINK, 0));
        tiles.add(new Tile(Color.GREEN, 1));
        tiles.add(new Tile(Color.WHITE, 2));

        player.modifyPickedTiles(tiles);

        assertEquals(player.getPickedTiles()[0].getIdTile(), 0);
        assertEquals(player.getPickedTiles()[1].getIdTile(), 1);
        assertEquals(player.getPickedTiles()[2].getIdTile(), 2);

    }
    @Test
    void testSelectTile(){
        List<Tile> tiles;
        Tile tile1 = new Tile(Color.PINK, 0);
        Tile tile2 = new Tile(Color.GREEN, 1);
        Tile tile3 = new Tile(Color.WHITE, 2);
        tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);

        player.modifyPickedTiles(tiles);
        assertEquals(player.selectTile(0), tile1);
        assertEquals(player.selectTile(1), tile2);
        assertEquals(player.selectTile(2), tile3);
        assertTrue(player.pickedTilesIsEmpty());

    }
    @Test
    void testPickedTilesIsEmpty(){
        List<Tile> tiles;
        tiles = new ArrayList<>();
        tiles.add(new Tile(Color.PINK, 0));    //Creating a set I can pass to modifyPickedTiles
        tiles.add(new Tile(Color.GREEN, 1));
        tiles.add(new Tile(Color.WHITE, 2));

        assertTrue(player.pickedTilesIsEmpty());
        player.modifyPickedTiles(tiles);
        assertFalse(player.pickedTilesIsEmpty());
    }
    @Test
    void testPickedTilesNum(){
        //TODO
    }
    @Test
    void testVerifyPGoalsPoints(){
        Tile tile;
        PersonalGoal PGoal= new PersonalGoal_1();
        PGoal.initialize();
        player.setMyPersonalGoal(PGoal);

        assertEquals(player.verifyPGoalPoints(), 0);

        for(int i = 0; i < 6; i++) {
            tile = new Tile(Color.PINK, 0);
            player.getPlayerGrid().topUp(0, tile);
        }
        assertEquals(player.verifyPGoalPoints(), 1);
        for(int i = 0; i < 6; i++) {   //This fills column 2 with blue tiles, bottom tile is supposed to be azure though
            tile = new Tile(Color.BLUE, 0);  //so points should increase up to 2 and not 4
            player.getPlayerGrid().topUp(2,tile);
        }
        assertEquals(player.verifyPGoalPoints(), 2);
        for(int i = 0; i < 5; i++){
            tile = new Tile(Color.GREEN, 0);
            player.getPlayerGrid().topUp(4,tile);
        }
        assertEquals(player.verifyPGoalPoints(), 4);
        for(int i = 0; i < 4; i++){
            tile = new Tile(Color.WHITE, 0);
            player.getPlayerGrid().topUp(3,tile);
        }
        assertEquals(player.verifyPGoalPoints(), 6);
        for(int i = 0; i < 3; i++){
            tile = new Tile(Color.YELLOW, 0);
            player.getPlayerGrid().topUp(1,tile);
        }
        assertEquals(player.verifyPGoalPoints(), 9);

    }


    @Test
    void verifyExtraPoints() {
        Tile tile;
        PersonalGoal PGoal = new PersonalGoal_1();
        PGoal.initialize();
        player.setMyPersonalGoal(PGoal);

        assertEquals(player.verifyExtraPoints(), 0);
        Tile tile1 = new Tile(Color.PINK, 0);
        Tile tile2 = new Tile(Color.PINK, 1);
        Tile tile3 = new Tile(Color.PINK, 2);
        player.getPlayerGrid().topUp(0, tile1);
        player.getPlayerGrid().topUp(0, tile2);
        player.getPlayerGrid().topUp(0, tile3);
        assertEquals(player.verifyExtraPoints(), 2);
        Tile tile4 = new Tile(Color.PINK, 3);
        Tile tile5 = new Tile(Color.PINK, 4);
        Tile tile6 = new Tile(Color.PINK, 5);
        player.getPlayerGrid().topUp(0, tile4);
        player.getPlayerGrid().topUp(0, tile5);
        player.getPlayerGrid().topUp(0, tile6);
        assertEquals(player.verifyExtraPoints(), 8);
        Tile tile7 = new Tile(Color.AZURE, 6);
        Tile tile8 = new Tile(Color.AZURE, 7);
        Tile tile9 = new Tile(Color.AZURE, 8);
        player.getPlayerGrid().topUp(1, tile7);
        player.getPlayerGrid().topUp(2, tile8);
        player.getPlayerGrid().topUp(3, tile9);
        assertEquals(player.verifyExtraPoints(), 10);
        Tile tile10 = new Tile(Color.AZURE, 9);
        Tile tile11 = new Tile(Color.AZURE, 10);
        Tile tile12 = new Tile(Color.AZURE, 11);
        player.getPlayerGrid().topUp(1, tile10);
        player.getPlayerGrid().topUp(1, tile11);
        player.getPlayerGrid().topUp(1, tile12);
        assertEquals(player.verifyExtraPoints(), 16);

    }

    @Test
    @DisplayName("testing multiple streaks")
    public void MultipleExtratest(){
        Tile tile;
        PersonalGoal PGoal = new PersonalGoal_1();
        PGoal.initialize();
        Tile tile1 = new Tile(Color.PINK, 0);
        Tile tile2 = new Tile(Color.PINK, 1);
        Tile tile3 = new Tile(Color.PINK, 2);
        Tile tile7 = new Tile(Color.AZURE, 6);
        Tile tile8 = new Tile(Color.AZURE, 7);
        Tile tile9 = new Tile(Color.AZURE, 8);
        player.getPlayerGrid().topUp(1, tile7);
        player.getPlayerGrid().topUp(2, tile8);
        player.getPlayerGrid().topUp(3, tile9);
        assertEquals(player.verifyExtraPoints(), 2);
        player.getPlayerGrid().topUp(0, tile1);
        player.getPlayerGrid().topUp(0, tile2);
        player.getPlayerGrid().topUp(0, tile3);
        assertEquals(player.verifyExtraPoints(), 4);
        player.getPlayerGrid().printPlayerGrid();
    }

    @Test
    @DisplayName("testing multiple streaks")
    public void MultipolExtraStrangetest(){
        Tile tile;
        PersonalGoal PGoal = new PersonalGoal_1();
        PGoal.initialize();
        Tile tile1 = new Tile(Color.PINK, 0);
        Tile tile2 = new Tile(Color.PINK, 1);
        Tile tile3 = new Tile(Color.PINK, 2);
        Tile tile7 = new Tile(Color.AZURE, 6);
        Tile tile8 = new Tile(Color.AZURE, 7);
        Tile tile9 = new Tile(Color.AZURE, 8);
        Tile tile10 = new Tile(Color.GREEN, 9);
        Tile tile11 = new Tile(Color.GREEN, 10);
        Tile tile12 = new Tile(Color.GREEN, 11);
        Tile tile13 = new Tile(Color.GREEN, 12);
        Tile tile14 = new Tile(Color.GREEN, 13);
        Tile tile15 = new Tile(Color.GREEN, 14);
        player.getPlayerGrid().topUp(1, tile1);
        player.getPlayerGrid().topUp(2, tile2);
        player.getPlayerGrid().topUp(3, tile3);
        player.getPlayerGrid().topUp(3, tile7);
        player.getPlayerGrid().topUp(3, tile8);
        player.getPlayerGrid().topUp(3, tile9);
        player.getPlayerGrid().topUp(4, tile10);
        player.getPlayerGrid().topUp(4, tile11);
        player.getPlayerGrid().topUp(4, tile12);
        player.getPlayerGrid().topUp(4, tile13);
        player.getPlayerGrid().topUp(3, tile14);
        player.getPlayerGrid().printColorPlayerGrid();
        assertEquals(player.verifyExtraPoints(), 7);

    }


}