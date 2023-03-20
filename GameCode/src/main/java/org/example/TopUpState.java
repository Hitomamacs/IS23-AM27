package org.example;

public class TopUpState implements GameState{
    private GameOrchestrator gameOrchestrator;
    @Override
    public void changeState() {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    public void disconnectChange(){

    }

    public void topUp(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();

    }
}
