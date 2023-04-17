package org.Project;

import java.util.List;

public class VerifyBoardableState implements GameState{
    private GameOrchestrator gameOrchestrator;

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
            gameOrchestrator.executeState();
        }

        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
        }

    }

    @Override
    public void execute() {
        changeState();
    }

}
