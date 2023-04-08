package org.Project;

public class VerifyGrillableState implements GameState{

    private GameOrchestrator gameOrchestrator;

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
        if(verifyGrillable()) {
            gameOrchestrator.changeState(new VerifyBoardableState(gameOrchestrator));
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
