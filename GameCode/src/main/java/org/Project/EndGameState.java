package org.Project;

public class EndGameState implements GameState{

    GameOrchestrator gameOrchestrator;
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
