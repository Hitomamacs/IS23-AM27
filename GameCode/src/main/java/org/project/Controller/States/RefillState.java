package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

public class RefillState implements GameState {

    public int getStateID(){
        return stateID;
    }

    private final int stateID = 5;
    private transient GameOrchestrator gameOrchestrator;

    public RefillState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }


    @Override
    public void changeState() {
        /*System.out.println("Server waiting for " + gameOrchestrator.getCurrentPlayer().getNickname() + " to pick tiles"  );
        gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(10); */

        if(!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(6);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
        else {System.out.println("Entering TopUpState waiting for player " + gameOrchestrator.getCurrentPlayer().getNickname() + " to move");
            gameOrchestrator.changeState(new TopUpState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(7);
        }


    }

    @Override
    //TODO refresh view (update)
    public void execute() {
        System.out.println("Checking if board needs refilling  (RefillState) ");
        if(gameOrchestrator.getGameBoard().checkBoard()){
            try {
                int needed_tiles = gameOrchestrator.getGameBoard().boardCheckNum();
                gameOrchestrator.getGameBoard().fillBoard(gameOrchestrator.getTileBag().randomPick(needed_tiles));
                gameOrchestrator.getGameBoard().firePropertyChange("boardUpdate", gameOrchestrator.getGameBoard());
                //gameOrchestrator.getGame().getView().updateView(gameOrchestrator.getGameBoard());
                System.out.println("Refilled board");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        changeState();
    }
}
