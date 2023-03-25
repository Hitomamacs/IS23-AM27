package org.example;

public class TopUpState implements GameState{
    private GameOrchestrator gameOrchestrator;

    int selectedColumn = -1;
    @Override
    public void changeState() {
        if(!gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty())
            gameOrchestrator.changeState(new TopUpState());
        else {
            gameOrchestrator.changeState(new VerifyCommonGoalState());
            gameOrchestrator.excecuteState();
        }

    }

    @Override
    public void execute() {
        if(!disconnectChange())
            topUp();//TODO change state not in top-up


    }

    public boolean disconnectChange(){
        if (!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState());
            return true;
        }
        return false;


    }
 //TODO here we consider the column is always the same
    public void topUp(){
        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();
        int column = gameOrchestrator.getCurrentPlayer().getSelectedColumn();
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().spaceCheck(column, n_picked_tiles)){
               gameOrchestrator.getCurrentPlayer().getPlayerGrid().topUp(column, gameOrchestrator.getCurrentPlayer().selectTile(gameOrchestrator.getCurrentPlayer().getTileIndex()));
               changeState();
            }

        else{
            changeState();
        }

    }
}
