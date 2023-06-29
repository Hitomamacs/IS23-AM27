package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.PlayerGrid;

/**
 * VerifyGrillableState is responsible for checking if the current player's grid has enough space
 * to place the picked tiles. If there is enough space, the state transitions to VerifyBoardableState.
 * If there is not enough space, an InvalidMoveException is thrown and the state remains in VerifyGrillableState.
 */

public class VerifyGrillableState implements GameState {

    private final int stateID = 10;

    public int getStateID(){
        return stateID;
    }

    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public VerifyGrillableState(GameOrchestrator orchestrator) {
        this.gameOrchestrator = orchestrator;
    }

    /**
     * Checks if there is enough space in the current player's grid to place the picked tiles.
     * @return true if there is enough space, false otherwise.
     */
    public boolean verifyGrillable(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();
        for(int i = 0; i < 5; i++){
            boolean found = grid.spaceCheck(i, n_picked_tiles);
            if(found){
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the game state based on the result of the grillable verification.
     * If there is enough space in the grid, the state transitions to VerifyBoardableState.
     * If there is not enough space, an InvalidMoveException is thrown, and the state remains in VerifyGrillableState.
     */
    @Override
    public void changeState() throws InvalidMoveException {
        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        System.out.println("Grillable test");
        if(verifyGrillable()) {
            System.out.println("Grillable passed");
            gameOrchestrator.changeState(new VerifyBoardableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(8);
            gameOrchestrator.executeState();
        }
        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(10);
            throw new InvalidMoveException("Not enough space in grid", 2);
        }
    }

    @Override
    public void execute() throws InvalidMoveException {
        changeState();
    }
}
