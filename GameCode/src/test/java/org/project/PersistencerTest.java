package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistencerTest {
    Game game;
    GameOrchestrator orchestrator;
    GameState state;

    Persistencer persistencer;


    GameOrchestrator restored;

    @BeforeEach
    void setup(){
        persistencer = new Persistencer();

        game = new Game();
        for(int i = 0; i < 4; i++){
            game.getUsers().add(new User("Spike", true));
        }
        game.gameInit(4);
        orchestrator = new GameOrchestrator(game.players, game.gameBoard, game.commonGoals, game.pointAssigner, game.tileBag, game);
        state = new StartTurnState(orchestrator);
        for(int i = 0; i < 4; i++){
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
        persistencer.saveGame(orchestrator, "save_test");


    }



    @Test
    void load_all() {
        restored = persistencer.load_all("save_test");
        assertTrue(restored.getSelectedCGoal().get(0).getClass() == orchestrator.getSelectedCGoal().get(0).getClass());

    }
}