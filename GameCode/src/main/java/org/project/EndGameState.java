package org.project;


public class EndGameState implements GameState{

    private final int stateID = 2;

    transient GameOrchestrator  gameOrchestrator;
    public EndGameState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void execute() {

    }

    @Override
    public void changeState() {

    }
}
