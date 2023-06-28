package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

public class StartTurnState implements GameState {

    public int getStateID(){
        return stateID;
    }

    private final int stateID = 6;
    private transient GameOrchestrator gameOrchestrator;

    public StartTurnState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void execute(){
        gameOrchestrator.getGame().getPersistencer().saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
        System.out.println("It is player " + gameOrchestrator.getCurrentPlayer().getNickname() + " turn  (StartTurnState execute method)" );
        changeState();



    }

    public void changeState(){
        if(gameOrchestrator.getFinalRoundFlag() && gameOrchestrator.CurrentPlayerIndex() == 0){
            gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(2);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(1);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
