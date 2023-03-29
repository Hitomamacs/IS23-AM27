package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartTurnStateTest {

    Game game;
    GameOrchestrator orchestrator;
    GameState state;

    @BeforeEach
    void setup(){
        game = new Game();
        game.gameInit(4);
        orchestrator = new GameOrchestrator(game.players, game.gameBoard, game.commonGoals, game.pointAssigner, game.tileBag, game);
        state = new StartTurnState(orchestrator);
        for(int i = 0; i < 4; i++){
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
    }
    //In this first test we have that the player is connected and his pickedTiles are empty as the game has
    //just started. So the next state will be the verifygrillable state and the board will have been refilled
    @Test
    void goesToRefill(){
        orchestrator.executeState();
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
        assertEquals(0, orchestrator.CurrentPlayerIndex());
    }
    //In this other case we check what happens if the player is signaled as disconnected, The next state has
    //to be startTurn but of the following player
     @Test
    void goesToNextStartTurn(){
        orchestrator.getCurrentPlayer().setConnected(false);
        orchestrator.executeState();
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
         assertEquals(1, orchestrator.CurrentPlayerIndex());

     }

}