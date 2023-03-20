package org.example;

public class VerifyCommonGoalState implements GameState{

    private GameOrchestrator gameOrchestrator;

    public void verifyCommonGoal(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        for(int i = 0; i< gameOrchestrator.getSelectedCGoal().size(); i++){
            if(gameOrchestrator.getSelectedCGoal().get(i).checkGoal(grid)){
                if(gameOrchestrator.getCurrentPlayer().getCompletedCGoals()[i] == false){
                    gameOrchestrator.getCurrentPlayer().modifyCompletedCGoals(i);
                    int points = gameOrchestrator.getPointAssigner().assignPoints(i);
                    gameOrchestrator.getCurrentPlayer().changeScore(points); //TODO add exception catches

                }

                }
            }

        }


    @Override
    public void changeState() {
        gameOrchestrator.changeState(new FullGridState());

    }

    @Override
    public void execute() {
        verifyCommonGoal();
        changeState();


    }

}
