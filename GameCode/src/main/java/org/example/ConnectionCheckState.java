package org.example;

public class ConnectionCheckState implements GameState{
    private GameOrchestrator gameOrchestrator;

    @Override
    public void changeState() {
        if(gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.changeState(new RefillState());
            gameOrchestrator.excecuteState();
        }
        else{
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState());
            gameOrchestrator.excecuteState();
        }


    }


    @Override
    public void execute() {
        changeState();
    }
}
