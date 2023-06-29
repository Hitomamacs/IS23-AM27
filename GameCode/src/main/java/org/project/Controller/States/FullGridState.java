package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

/**
 * FullGridState represents the game state when a player's grid is full.
 * It handles transitioning to the StartTurnState and checking for the final round of the game.
 */

public class FullGridState implements GameState {

    private final int stateID = 3;

    public int getStateID(){
        return stateID;
    }

    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public FullGridState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    /**
     * Changes the game state to the StartTurnState.
     */
    @Override
    public void changeState() {
        gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(6);
        try {
            gameOrchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the state by checking if the player's grid is full and updating the final round flag.
     * It then moves to the next player and changes the state to the StartTurnState.
     */
    @Override
    public void execute() {
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().fullCheck() && !gameOrchestrator.getFinalRoundFlag())
            gameOrchestrator.setFinalRoundFlag(true);
            //TODO update view broadcast pop up that warns final round
        gameOrchestrator.nextPlayer();
        //TODO missing return statement if "if" is not true
        changeState();
    }
}
