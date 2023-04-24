package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Model.PlayerGrid;

public class VerifyGrillableState implements GameState {
    private final int stateID = 10;

    private transient GameOrchestrator gameOrchestrator;

    public VerifyGrillableState(GameOrchestrator orchestrator) {
        this.gameOrchestrator = orchestrator;
    }
    public boolean verifyGrillable(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();
        for(int i = 0; i < 5; i++){
            boolean found = grid.spaceCheck(i, n_picked_tiles);
            if(found){
                return true;
            }
        }
        return false;
    }
    

    @Override
    public void changeState() {
        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        if(verifyGrillable()) {
            gameOrchestrator.changeState(new VerifyBoardableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(8);
            gameOrchestrator.executeState();
        }
        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(10);
            //Update view
            gameOrchestrator.getGame().getView().updateView(currentPlayer, "Not enough space for the selected tiles");
        }

    }
    @Override
    public void execute() {
        changeState();
    }
}
