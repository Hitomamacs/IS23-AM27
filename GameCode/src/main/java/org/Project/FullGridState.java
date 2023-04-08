package org.Project;

public class FullGridState implements GameState{

    private GameOrchestrator gameOrchestrator;

    public FullGridState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
        gameOrchestrator.executeState();


    }

    @Override
    public void execute() {
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().fullCheck() && !gameOrchestrator.getFinalRoundFlag())
            gameOrchestrator.setFinalRoundFlag(true);

        gameOrchestrator.nextPlayer();
        changeState();
    }
}
