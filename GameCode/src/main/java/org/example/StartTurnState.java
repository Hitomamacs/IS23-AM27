package org.example;

public class StartTurnState implements GameState{
    private GameOrchestrator gameOrchestrator;

    public void Execute(){
        changeState();



    }

    public void changeState(){
        if(gameOrchestrator.getFinalRoundFlag() && gameOrchestrator.CurrentPlayerIndex() == 0){
            gameOrchestrator.changeState(new EndGameState());
        }
        else{
            gameOrchestrator.changeState(new ConnectionCheckState());
        }
    }

}
