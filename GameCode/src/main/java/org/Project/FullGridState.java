package org.Project;

public class FullGridState implements GameState{

    private final int stateID = 3;

    private transient GameOrchestrator gameOrchestrator;

    public FullGridState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(6);
        gameOrchestrator.executeState();


    }

    @Override
    public void execute() {
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().fullCheck() && !gameOrchestrator.getFinalRoundFlag())
            gameOrchestrator.setFinalRoundFlag(true);
        gameOrchestrator.nextPlayer();
        //TODO missing return statement if "if" is not true
        changeState();
    }
}
