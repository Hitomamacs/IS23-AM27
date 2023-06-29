package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.PlayerGrid;

/**
 * VerifyCommonGoalState is responsible for verifying if the current player has completed the common goals.
 * It checks the player's grid against the selected common goals and updates the player's completed goals and score accordingly.
 * After verification, it transitions to the FullGridState.
 */
public class VerifyCommonGoalState implements GameState {

    private final int stateID = 9;

    public int getStateID(){
        return stateID;
    }

    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public VerifyCommonGoalState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    /**
     * Verifies if the current player has completed any common goals.
     * It checks the player's grid against the selected common goals and updates the player's completed goals and score accordingly.
     */
    public void verifyCommonGoal(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        for(int i = 0; i < gameOrchestrator.getSelectedCGoal().size(); i++){
            if(!gameOrchestrator.getCurrentPlayer().getCompletedCGoals()[i]){
                if(gameOrchestrator.getSelectedCGoal().get(i).checkGoal(grid)){
                    gameOrchestrator.getCurrentPlayer().modifyCompletedCGoals(i);
                    int points = gameOrchestrator.getPointAssigner().assignPoints(i);
                    gameOrchestrator.getCurrentPlayer().changeScore(points); //TODO add exception catches
                    //Update view
                    //gameOrchestrator.getGame().getView().updateView(gameOrchestrator.getPointAssigner(), i);
                }
            }
        }
    }

    /**
     * Changes the game state to the FullGridState after verifying common goals.
     */
    @Override
    public void changeState() {
        gameOrchestrator.changeState(new FullGridState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(3);
        try {
            gameOrchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the state by verifying common goals and transitioning to the FullGridState.
     */
    @Override
    public void execute() {
        verifyCommonGoal();
        changeState();
    }
}
