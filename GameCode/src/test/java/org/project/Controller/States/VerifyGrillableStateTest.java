
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerifyGrillableStateTest {

    Game game;
    GameOrchestrator orchestrator;
    GameState state;

    //The VerifyGrillableState can have the following outcomes: the passed input isn't valid because there
    //isn't a column with enough space, the input isn't valid because the passed coordinates aren't acceptable
    //they are not adjacent or don't have free sides, the input is valid and the state evolves.
    //Note by input what actually happens is that another class in the controller has been waked up by an
    //event and through callback has modified the orchestrators pickedCoordinates, these coordinates are then
    //checked by this state and the BoardableState to check if they are valid.

    @BeforeEach
    void setup(){
        game = new Game();
        for(int i = 0; i < 4; i++){
            game.getUsers().add(new User("Spike", true));
        }
        game.gameInit(4);
        orchestrator = new GameOrchestrator(game.getPlayers(), game.getGameBoard(), game.getCommonGoals(), game.getPointAssigner(), game.getTileBag(), game);
        state = new StartTurnState(orchestrator);
        for(int i = 0; i < 4; i++){
            orchestrator.getCurrentPlayer().setConnected(true);
            orchestrator.nextPlayer();
        }
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }
    //Outcome 1: there is a column with enough space, the BoardableState is executed, the coordinates are valid
    //the PickState is executed, then the state evolves till the TopUpState where it waits for a new event.
    @Test
    void testValidCoordinates(){
        Coordinates c1 = new Coordinates(0,4);
        Coordinates c2 = new Coordinates(0, 3);
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);

        //Print the board first to check if it's full
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();

        //Simulate what the other classes of the controller will do. Change the model
        orchestrator.setPickedCoordinates(coordinates);

        //Now that the model has been changed we call the state execution, since the coordinates are valid
        //should lead to the TopUpState. Also check that the tiles have actually been picked by the board
        //and are now in the players picked tiles
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        assertTrue(orchestrator.getState() instanceof TopUpState);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
        assertFalse(orchestrator.getCurrentPlayer().pickedTilesIsEmpty());
        assertTrue(orchestrator.getPickedCoordinates().isEmpty());//The PickState flushes the pickedCoordinates
        assertEquals(0, orchestrator.CurrentPlayerIndex());

        for(int i = 0; i < coordinates.size(); i++){
            System.out.print(orchestrator.getCurrentPlayer().getPickedTiles()[i].getColor());
            System.out.print(" ");
        }
    }
    //In the next test there is enough space in the grid but the selected coordinates are not valid
    //either not adjacent or don't have free sides.
    @Test
    void testSpaceButNotValid(){
        Coordinates c1 = new Coordinates(1,4);
        Coordinates c2 = new Coordinates(1, 3);
        Coordinates c3 = new Coordinates(1, 5);
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);

        //Print the board first to check if it's full
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();

        //Simulate what the other classes of the controller will do. Change the model
        orchestrator.setPickedCoordinates(coordinates);

        //Execute the state and check that tiles have not been picked from the board and
        //we are back in VerifyGrillableState
        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        assertTrue(orchestrator.getPickedCoordinates().isEmpty());//The VerifyBoardable calls the flush
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();

    }
    @Test
    void testNoSpace(){
        Coordinates c1 = new Coordinates(1,4);
        Coordinates c2 = new Coordinates(1, 3);
        Coordinates c3 = new Coordinates(1, 5);
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);

        orchestrator.setPickedCoordinates(coordinates);
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();

        Color[][] matrix;
        matrix = new Color[][]{{Color.PINK, Color.PINK, Color.GREEN, Color.GREEN, Color.AZURE},
                {Color.GREEN, Color.PINK,Color.WHITE,Color.PINK, Color.GREEN},
                {Color.WHITE, Color.PINK, Color.AZURE, Color.WHITE, Color.WHITE},
                {Color.BLUE, Color.AZURE, Color.GREEN, Color.BLUE, Color.WHITE},
                {Color.AZURE, Color.PINK, Color.PINK, Color.YELLOW, Color.WHITE},
                {Color.YELLOW, Color.PINK, Color.PINK, Color.AZURE, Color.PINK}};
        orchestrator.getCurrentPlayer().getPlayerGrid().quickGridSetter(matrix);
        orchestrator.getCurrentPlayer().getPlayerGrid().printColorPlayerGrid();

        try {
            orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        assertTrue(orchestrator.getState() instanceof VerifyGrillableState);
        assertTrue(orchestrator.getPickedCoordinates().isEmpty());//The verifyGrillable calls the flush
        orchestrator.getGameBoard().printBoardColor();
        orchestrator.getGameBoard().printMwithTiles();
    }
}