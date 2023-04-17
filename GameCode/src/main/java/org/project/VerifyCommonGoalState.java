package org.project;

public class VerifyCommonGoalState implements GameState{

    private GameOrchestrator gameOrchestrator;

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
                }
            }
        }
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new FullGridState(gameOrchestrator));
        gameOrchestrator.executeState();

    }

    @Override
    public void execute() {
        verifyCommonGoal();
        changeState();


    }

}
