package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

public class RefillState implements GameState {

    public int getStateID(){
        return stateID;
    }

    private final int stateID = 5;
    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public RefillState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    /**
     * Changes the game state
     * If the player is not connected, it goes to StartTurnState for the next player
     * If the player is connected, it passes to the TopUpState
     */
    @Override
    public void changeState() {

        if(!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(6);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
        else {System.out.println("Entering TopUpState waiting for player " + gameOrchestrator.getCurrentPlayer().getNickname() + " to move");
            gameOrchestrator.changeState(new TopUpState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(7);
        }
    }

    /**
     * Executes the refill phase of the game.
     * It checks if the game board needs to be refilled, and if so, it refills the board by picking tiles from the tile bag.
     * After refilling, it triggers the necessary updates, such as firing property change events to update the game board.
     * Finally, it calls the `changeState()` method to transition to the appropriate next state.
     */
    @Override
    public void execute() {
        System.out.println("Checking if board needs refilling  (RefillState) ");
        if(gameOrchestrator.getGameBoard().checkBoard()){
            try {
                int needed_tiles = gameOrchestrator.getGameBoard().boardCheckNum();
                gameOrchestrator.getGameBoard().fillBoard(gameOrchestrator.getTileBag().randomPick(needed_tiles));
                gameOrchestrator.getGameBoard().firePropertyChange("boardUpdate", gameOrchestrator.getGameBoard());
                //gameOrchestrator.getGame().getView().updateView(gameOrchestrator.getGameBoard());
                System.out.println("Refilled board");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        changeState();
    }
}
