package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Model.PlayerGrid;

public class VerifyCommonGoalState implements GameState {
    private final int stateID = 9;

    private transient GameOrchestrator gameOrchestrator;

    public VerifyCommonGoalState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void verifyCommonGoal(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        for(int i = 0; i < gameOrchestrator.getSelectedCGoal().size(); i++){
            if(!gameOrchestrator.getCurrentPlayer().getCompletedCGoals()[i]){
                if(gameOrchestrator.getSelectedCGoal().get(i).checkGoal(grid)){
                    gameOrchestrator.getCurrentPlayer().modifyCompletedCGoals(i);
                    int points = gameOrchestrator.getPointAssigner().assignPoints(i);
                    gameOrchestrator.getCurrentPlayer().changeScore(points); //TODO add exception catches
                    //Update view
                    gameOrchestrator.getGame().getView().updateView(gameOrchestrator.getPointAssigner(), i);
                }
            }
        }
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new FullGridState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(3);
        gameOrchestrator.executeState();

    }

    @Override
    public void execute() {
        verifyCommonGoal();
        changeState();


    }

}
