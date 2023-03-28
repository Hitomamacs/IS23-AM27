package org.example;

public class TopUpState implements GameState{
    private GameOrchestrator gameOrchestrator;

    int selectedColumn;

    public TopUpState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
        selectedColumn = -1;
    }
    public TopUpState(GameOrchestrator orchestrator, int selectedColumn){
        this.gameOrchestrator = orchestrator;
        this.selectedColumn = selectedColumn;
    }
    @Override
    public void changeState() {
        if(!gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty())
            gameOrchestrator.changeState(new TopUpState(gameOrchestrator, selectedColumn));
        else {
            gameOrchestrator.changeState(new VerifyCommonGoalState(gameOrchestrator));
            gameOrchestrator.executeState();
        }

    }

    @Override
    public void execute() {
  //      if(!disconnectChange())
            topUp();//TODO change state not in top-up
            changeState();


    }
/*
    public boolean disconnectChange(){
        if (!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState());
            return true;
        }
        return false;


    }

 */
 //TODO here we consider the column is always the same
    public void topUp(){

        Tile tile;
        int index = 0;
        if(selectedColumn == -1)
            selectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();

        PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
        int n_picked_tiles =  gameOrchestrator.getPickedCoordinates().size();
        if(gameOrchestrator.getCurrentPlayer().getPlayerGrid().spaceCheck(selectedColumn, n_picked_tiles)){
            index = gameOrchestrator.getCurrentPlayer().getTileIndex();
            tile = gameOrchestrator.getCurrentPlayer().selectTile(index);
            gameOrchestrator.getCurrentPlayer().getPlayerGrid().topUp(selectedColumn,tile);
        }
        changeState();
    }

}

