
package org.project.Controller.States;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Controller.Control.Game;
import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.Control.User;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Controller.States.GameState;
import org.project.Controller.States.StartTurnState;
import org.project.Controller.States.VerifyCommonGoalState;
import org.project.Model.Color;
import org.project.Model.CommonGoals.CommonGoal_1;
import org.project.Model.CommonGoals.CommonGoal_6;
import org.project.Model.Player;
import org.project.Model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerifyCommonGoalStateTest {

    Game game;
    GameOrchestrator orchestrator;
    GameState state;

    List<Tile> tiles;

    @BeforeEach
    void setup() {
        tiles = new ArrayList<>();
        game = new Game();
        for(int i = 0; i < 4; i++){
            game.getPlayers().add(new Player("player" + i));
        }
        game.gameInit(4);
        orchestrator = new GameOrchestrator(game.getPlayers(), game.getGameBoard(), game.getCommonGoals(), game.getPointAssigner(), game.getTileBag(), game);
        state = new StartTurnState(orchestrator);
        for (int i = 0; i < 4; i++) {
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
    }

    @Test
    void completedGoal(){
        Color[][] matrix;
        matrix = new Color[][]{
                {Color.PINK, Color.PINK,     null  ,     null,        null},
                {Color.PINK, Color.PINK,     null  ,     null ,       null},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.GREEN, Color.YELLOW},
                {Color.YELLOW, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.AZURE, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.AZURE, Color.AZURE, Color.PINK}};
        orchestrator.getCurrentPlayer().getPlayerGrid().quickGridSetter(matrix);
        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");

        //In the next two lines we change the selectedCGoals, very anti-object-oriented approach as using
        //a getter to modify the actual structure but fastest way and still valid to check
//        orchestrator.getSelectedCGoal().remove(0);

        orchestrator.getSelectedCGoal().add(0, new CommonGoal_1());
        orchestrator.getSelectedCGoal().add(1, new CommonGoal_6());

        orchestrator.changeState(new VerifyCommonGoalState(orchestrator));
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }

        //Now the next two asserts are to check if the VerifyCommonGoal has worked correctly so by
        //changing them, changing the playerGrid, changing the common goals and changing the stacks
        // of the pointAssigner all cases can be checked
        assertEquals(16, orchestrator.getPlayer(0).getScore());
        assertTrue(orchestrator.getPlayer(0).getCompletedCGoals()[1]);
        assertTrue(orchestrator.getPlayer(0).getCompletedCGoals()[0]);
        assertEquals(6, (int) orchestrator.getPointAssigner().getStackList().get(0).peek());
        assertEquals(6, (int) orchestrator.getPointAssigner().getStackList().get(1).peek());

        matrix = new Color[][]{
                {Color.PINK, Color.PINK,     null  ,     null,        null},
                {Color.PINK, Color.PINK,     null  ,     null ,       null},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.GREEN, Color.YELLOW},
                {Color.YELLOW, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.AZURE, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.AZURE, Color.AZURE, Color.PINK}};
        orchestrator.getCurrentPlayer().getPlayerGrid().quickGridSetter(matrix);
        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");

        orchestrator.changeState(new VerifyCommonGoalState(orchestrator));
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }

        assertEquals(12, orchestrator.getPlayer(1).getScore());
        assertTrue(orchestrator.getPlayer(1).getCompletedCGoals()[1]);
        assertTrue(orchestrator.getPlayer(1).getCompletedCGoals()[0]);
        assertEquals(4, (int) orchestrator.getPointAssigner().getStackList().get(0).peek());
        assertEquals(4, (int) orchestrator.getPointAssigner().getStackList().get(1).peek());


        matrix = new Color[][]{
                {Color.PINK, Color.PINK,     null  ,     null,        null},
                {Color.PINK, Color.PINK,     null  ,     null ,       null},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.GREEN, Color.YELLOW},
                {Color.YELLOW, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.AZURE, Color.AZURE, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.AZURE, Color.AZURE, Color.PINK}};
        orchestrator.getCurrentPlayer().getPlayerGrid().quickGridSetter(matrix);
        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();
        System.out.print("\n");

        orchestrator.changeState(new VerifyCommonGoalState(orchestrator));
        orchestrator.getCurrentPlayer().modifyCompletedCGoals(1);
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
        }

        assertEquals(4, orchestrator.getPlayer(2).getScore());
        assertTrue(orchestrator.getPlayer(2).getCompletedCGoals()[1]);
        assertTrue(orchestrator.getPlayer(2).getCompletedCGoals()[0]);
        assertEquals(2, orchestrator.getPointAssigner().getStackList().get(0).peek());
        assertEquals(4, (int) orchestrator.getPointAssigner().getStackList().get(1).peek());

    }
}