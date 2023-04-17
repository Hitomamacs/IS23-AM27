package org.project;

import java.util.List;

public class VerifyBoardableState implements GameState{
    private final int stateID = 8;
    private transient GameOrchestrator gameOrchestrator;

    public VerifyBoardableState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public boolean verifyBoardable(){
        List<Coordinates> pickedCoordinates = gameOrchestrator.getPickedCoordinates();
        for(Coordinates c : pickedCoordinates){
            if(!gameOrchestrator.getGameBoard().verifyPickable(c)){
                return false;
            }
        }
        return true;

    }
    @Override
    public void changeState() {
        if(verifyBoardable()) {
            gameOrchestrator.changeState(new PickState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(4);
            gameOrchestrator.executeState();
        }

        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(10);
        }

    }

    @Override
    public void execute() {
        changeState();
    }

}
