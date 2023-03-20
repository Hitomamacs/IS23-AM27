package org.example;

public class VerifyGrillableState implements GameState{

    private GameOrchestrator gameOrchestrator;
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
        if(verifyGrillable())
            gameOrchestrator.changeState(new VerifyBoardableState());
        else
            gameOrchestrator.changeState(new VerifyGrillableState());

    }
    @Override
    public void execute() {
        changeState();
    }
}
