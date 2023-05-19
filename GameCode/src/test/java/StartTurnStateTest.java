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
import org.project.Model.Player;
import org.project.Model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StartTurnStateTest {

    Game game;
    GameOrchestrator orchestrator;
    GameState state;

    @BeforeEach
    void setup(){
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
    }
    //In this first test we have that the player is connected and his pickedTiles are empty as the game has
    //just started. So the next state will be the verifyGrillable state and the board will have been refilled
    @Test
    void goesToRefill(){
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
        assertEquals(0, orchestrator.CurrentPlayerIndex());
        assertFalse(orchestrator.getGameBoard().checkBoard());
    }
    //In this other case we check what happens if the player is signaled as disconnected, The next state has
    //to be verifyGrillable but of the following player.
    //Process is the following StartTurn1--->ConnectionState1--->StartTurn2--->ConnectionState2--->VerifyGrillable2
    //where 1 stands for player 1 and 2 for player 2
    @Test
    void goesToNextStartTurn(){
        orchestrator.getCurrentPlayer().setConnected(false);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
        assertEquals(1, orchestrator.CurrentPlayerIndex());
        assertFalse(orchestrator.getGameBoard().checkBoard());
    }
    //Have to check that startTurn goes to connectionCheck and connectionCheck goes to TopUp if player is
    //connected and has some tiles in his hand
    @Test
    void goesToTopUp(){
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Color.PINK, 1));
        tiles.add(new Tile(Color.AZURE, 2));
        tiles.add(new Tile(Color.BLUE, 3));

        orchestrator.getCurrentPlayer().modifyPickedTiles(tiles);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }
        assertTrue(orchestrator.getState() instanceof TopUpState);
        assertEquals(0, orchestrator.CurrentPlayerIndex());
        assertTrue(orchestrator.getGameBoard().checkBoard());
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
        game.getPersistencer().saveGame(orchestrator, "saving");
    }

}