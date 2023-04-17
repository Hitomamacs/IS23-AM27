package org.project;

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
            gameOrchestrator.getCurrentPlayer().setSelectedColumn(-1);
            gameOrchestrator.changeState(new VerifyCommonGoalState(gameOrchestrator));
            gameOrchestrator.executeState();
        }

    }

    @Override
    public void execute() {
  //      if(!disconnectChange())
            topUp();
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
    public void topUp(){

        Tile tile;
        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        int index = 0;
        if(selectedColumn == -1)
            selectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();

        if(selectedColumn == gameOrchestrator.getCurrentPlayer().getSelectedColumn()) {
            PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
            int n_picked_tiles = gameOrchestrator.getCurrentPlayer().pickedTilesNum();
            if (grid.spaceCheck(selectedColumn, n_picked_tiles)) {
                index = gameOrchestrator.getCurrentPlayer().getTileIndex();
                if(gameOrchestrator.getCurrentPlayer().getPickedTiles()[index] != null) {
                    //Update the view
                    Tile[] pickedTiles = gameOrchestrator.getCurrentPlayer().getPickedTiles();
                    gameOrchestrator.getGame().getView().updateView(pickedTiles, currentPlayer, index, selectedColumn);
                    tile = gameOrchestrator.getCurrentPlayer().selectTile(index);
                    gameOrchestrator.getCurrentPlayer().getPlayerGrid().topUp(selectedColumn, tile);
                }
            }
            else {
                selectedColumn = -1;
                gameOrchestrator.getGame().getView().updateView(currentPlayer, "The selected column doesn't have enough space");
            }
            //if the selected column doesn't have enough space the player has to change it, to allow
            //the player to change it the default state selectedColumn has to be set back to -1
        }
        else gameOrchestrator.getGame().getView().updateView(currentPlayer, "You have to place tiles in the same column");
    }

}

