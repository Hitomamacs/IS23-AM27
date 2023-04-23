package org.project;

public class StartTurnState implements GameState{

    private final int stateID = 6;
    private transient GameOrchestrator gameOrchestrator;

    public StartTurnState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void execute(){
        gameOrchestrator.getGame().persistencer.saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
        changeState();



    }

    public void changeState(){
        if(gameOrchestrator.getFinalRoundFlag() && gameOrchestrator.CurrentPlayerIndex() == 0){
            gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(2);
            gameOrchestrator.executeState();

        }
        else{
            gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(1);
            gameOrchestrator.executeState();
        }
    }

}
