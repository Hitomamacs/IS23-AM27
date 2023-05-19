
package org.project.Controller.States;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.Control.Game;
import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.Control.User;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Controller.States.GameState;
import org.project.Controller.States.StartTurnState;
import org.project.Controller.States.TopUpState;
import org.project.Controller.States.VerifyGrillableState;
import org.project.Model.Color;
import org.project.Model.Coordinates;
import org.project.Model.Player;
import org.project.Model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopUpStateTest {

    private Game game;
    private GameOrchestrator orchestrator;
    private GameState state;

    private List<Tile> tiles;

    @BeforeEach
    void setup(){
        tiles = new ArrayList<>();
        game = new Game();
        for(int i = 0; i < 4; i++){
            game.getPlayers().add(new Player("player" + i));
        }
        game.gameInit(4);
        orchestrator = new GameOrchestrator(game.getPlayers(), game.getGameBoard(), game.getCommonGoals(), game.getPointAssigner(), game.getTileBag(), game);
        state = new StartTurnState(orchestrator);
        for(int i = 0; i < 4; i++){
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
        Coordinates c1 = new Coordinates(0,4);
        Coordinates c2 = new Coordinates(0, 3);
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);

        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.setPickedCoordinates(coordinates);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        assertTrue(orchestrator.getState() instanceof TopUpState);
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

    }
    //The first test checks for a series of topUp moves as the player has selected two tiles
    //With this test we see if the selected column memorization works, what happens if an empty tile index
    //is selected, different column from previous is selected, and finally what happens when the TopUp is
    //correctly completed
    @Test
    void testTopUp(){

        orchestrator.getCurrentPlayer().setSelectedColumn(0);
        orchestrator.getCurrentPlayer().setTileIndex(0);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");
        assertTrue(orchestrator.getState() instanceof TopUpState);
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

        //Now we simulate another input from the client but with a different column so the expected result
        //is to still be in the TopUpState and with the tile in pickedTiles[1] untouched
        assertEquals(0, ((TopUpState) orchestrator.getState()).selectedColumn);
        orchestrator.getCurrentPlayer().setSelectedColumn(1);
        orchestrator.getCurrentPlayer().setTileIndex(1);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");
        assertTrue(orchestrator.getState() instanceof TopUpState);
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

        //Now we see what happens if the player selects an empty position from his pickedTiles
        //ideally we remain in the topUpState and nothing changes
        //Note all these controls will be done client side as well, they are redundant but needed to avoid
        //crashing in case the client is not working correctly
        orchestrator.getCurrentPlayer().setSelectedColumn(0);
        orchestrator.getCurrentPlayer().setTileIndex(0);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");
        assertTrue(orchestrator.getState() instanceof TopUpState);
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

        //Finally we receive a valid input, VerifyCommonGoalState and FullGridState will be called, finally
        //the StartTurnState will be called and since the player is connected we will end up in the next players
        //VerifyGrillableState
        orchestrator.getCurrentPlayer().setSelectedColumn(0);
        orchestrator.getCurrentPlayer().setTileIndex(1);


        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

        orchestrator.getPlayer(0).getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");
        //Since the TopUp phase has finished successfully the players selectedColumn has to be restored to -1
        assertEquals(orchestrator.getPlayer(0).getSelectedColumn(), -1);
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        assertEquals(orchestrator.CurrentPlayerIndex(), 1);
        orchestrator.getGameBoard().printBoardColor();
    }
    //The missing case is if the selectedColumn does not have enough space for all the tiles
    //the state will remain in TopUp nothing else will change except the new TopUp state has
    //selectedColumn set to -1 to allow the player to change it.
    @Test
    void notEnoughSpace(){

        for(int i = 0; i < 5; i++) {
            orchestrator.getCurrentPlayer().getPlayerGrid().getSpot(new Coordinates(5-i ,0)).placeTile(new Tile(Color.PINK, 1));
        }
        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");

        orchestrator.getCurrentPlayer().setSelectedColumn(0);
        orchestrator.getCurrentPlayer().setTileIndex(0);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        assertTrue(orchestrator.getState() instanceof TopUpState);
        assertEquals(((TopUpState) orchestrator.getState()).selectedColumn, -1);

        orchestrator.getCurrentPlayer().setSelectedColumn(1);
        orchestrator.getCurrentPlayer().setTileIndex(0);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        orchestrator.getCurrentPlayer().setSelectedColumn(0);
        orchestrator.getCurrentPlayer().setTileIndex(1);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        assertTrue(orchestrator.getState() instanceof TopUpState);
        assertEquals(((TopUpState) orchestrator.getState()).selectedColumn, 1);
        orchestrator.getCurrentPlayer().setSelectedColumn(1);
        orchestrator.getCurrentPlayer().setTileIndex(1);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }


        orchestrator.getPlayer(0).getPlayerGrid().printColorPlayerGrid();
        orchestrator.getGameBoard().printBoardColor();
    }
}