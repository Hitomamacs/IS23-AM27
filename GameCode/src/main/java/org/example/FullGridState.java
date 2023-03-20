package org.example;

public class FullGridState implements GameState{

    private GameOrchestrator gameOrchestrator;







    @Override
    public void changeState() {
        gameOrchestrator.changeState(new StartTurnState());


    }

    @Override
    public void execute() {
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().fullCheck() && gameOrchestrator.getFinalRoundFlag() == false)
            gameOrchestrator.setFinalRoundFlag(true);
        changeState();


    }
}
