package org.project.Controller.States;


import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

public class FullGridState implements GameState {

    private final int stateID = 3;

    private transient GameOrchestrator gameOrchestrator;

    public FullGridState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(6);
        try {
            gameOrchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void execute() {
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().fullCheck() && !gameOrchestrator.getFinalRoundFlag())
            gameOrchestrator.setFinalRoundFlag(true);
            //TODO update view broadcast pop up that warns final round
        gameOrchestrator.nextPlayer();
        //TODO missing return statement if "if" is not true
        changeState();
    }
}
