
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
    void setup() {
        tiles = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        game = new Game();
        for (int i = 0; i < 4; i++) {
            players.add(new Player("player" + i));
        }
        game.gameInit(players);
        orchestrator = new GameOrchestrator(game.getPlayers(), game.getGameBoard(), game.getCommonGoals(), game.getPointAssigner(), game.getTileBag(), game);
        state = new StartTurnState(orchestrator);
        for (int i = 0; i < 4; i++) {
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
        Coordinates c1 = new Coordinates(0, 4);
        Coordinates c2 = new Coordinates(0, 3);
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);

        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.setPickedCoordinates(coordinates);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }

        assertTrue(orchestrator.getState() instanceof TopUpState);
        System.out.print(orchestrator.CurrentPlayerIndex());
        System.out.print("\n");

    }
    //The first test checks for a series of topUp moves as the player has selected two tiles
    //With this test we see if the selected column memorization works, what happens if an empty tile index
    //is selected, different column from previous is selected, and finally what happens when the TopUp is
    //correctly completed
}