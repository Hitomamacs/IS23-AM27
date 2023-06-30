package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

/**
 * The StartTurnState class implements the GameState interface and represents the turn start state
 */
public class StartTurnState implements GameState {

    public int getStateID(){
        return stateID;
    }

    private final int stateID = 6;
    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public StartTurnState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    /**
     * Executes actions at the beginning of a turn in the game.
     * This method saves the game state, prints a message indicating the current player's turn,
     * and changes the game state to the appropriate state based on certain conditions.
     */
    public void execute(){
        gameOrchestrator.getGame().getPersistencer().saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
        System.out.println("It is player " + gameOrchestrator.getCurrentPlayer().getNickname() + " turn  (StartTurnState execute method)" );
        changeState();
    }

    /**
     * Changes the game state based on certain conditions.
     * If the final round flag is set to true and the current player index is 0,
     * the game state is changed to EndGameState and the current state ID is set to 2.
     * If the above conditions are not met, the game state is changed to ConnectionCheckState
     * and the current state ID is set to 1.
     * After changing the state, the new state is executed by calling the executeState() method
     * in the gameOrchestrator.
     */
    public void changeState(){
        if(gameOrchestrator.getFinalRoundFlag() && gameOrchestrator.CurrentPlayerIndex() == 0){
            gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(2);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }

        else{
            gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(1);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
