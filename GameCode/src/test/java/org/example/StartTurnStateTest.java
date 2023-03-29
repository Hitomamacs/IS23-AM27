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
        orchestrator.nextPlayer();
    }
    @Test
    void goesToRefill(){
        orchestrator.executeState();
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        orchestrator.getGameBoard().printBoardColor();
    }

}