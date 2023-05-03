package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;

public class StartTurnState implements GameState {

    private final int stateID = 6;
    private transient GameOrchestrator gameOrchestrator;

    public StartTurnState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void execute(){
        gameOrchestrator.getGame().getPersistencer().saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
        System.out.println("\nIt is player " + gameOrchestrator.getCurrentPlayer().getNickname() + " turn  (StartTurnState execute method)" );
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
