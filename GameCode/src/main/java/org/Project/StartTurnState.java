package org.Project;

public class StartTurnState implements GameState{
    private transient GameOrchestrator gameOrchestrator;

    public StartTurnState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void execute(){
        changeState();



    }

    public void changeState(){
        if(gameOrchestrator.getFinalRoundFlag() && gameOrchestrator.CurrentPlayerIndex() == 0){
            gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
            gameOrchestrator.executeState();
        }
        else{
            gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
            gameOrchestrator.executeState();
        }
    }

}
