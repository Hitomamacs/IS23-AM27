package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.PlayerGrid;
import org.project.Model.Tile;

public class TopUpState implements GameState {

    public int getStateID(){
        return stateID;
    }
    private final int stateID = 7;
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
        if(!gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty()){
            gameOrchestrator.changeState(new TopUpState(gameOrchestrator, selectedColumn));
            gameOrchestrator.setCurr_sate_id(7);

        }
        else {
            gameOrchestrator.getCurrentPlayer().setSelectedColumn(-1);
            gameOrchestrator.changeState(new VerifyCommonGoalState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(9);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void execute() throws InvalidMoveException {
        topUp();
        gameOrchestrator.getGame().getPersistencer().saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
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
    public void topUp() throws InvalidMoveException {

        Tile tile;
        int index = 0;
        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        if(selectedColumn == -1)
            selectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();

        if(selectedColumn == gameOrchestrator.getCurrentPlayer().getSelectedColumn()) {
            PlayerGrid grid = gameOrchestrator.getCurrentPlayer().getPlayerGrid();
            int n_picked_tiles = gameOrchestrator.getCurrentPlayer().pickedTilesNum();
            if (grid.spaceCheck(selectedColumn, n_picked_tiles)) {
                index = gameOrchestrator.getCurrentPlayer().getTileIndex();
                if(gameOrchestrator.getCurrentPlayer().getPickedTiles()[index] != null) {
                    //Update view
                    Tile[] pickedTiles = gameOrchestrator.getCurrentPlayer().getPickedTiles();
                    Tile[] pickedTilesCopy = new Tile[3];
                    for (int i = 0; i < pickedTiles.length; i++) {
                        pickedTilesCopy[i] = pickedTiles[i];
                    }
                    tile = gameOrchestrator.getCurrentPlayer().selectTile(index);
                    gameOrchestrator.getCurrentPlayer().getPlayerGrid().topUp(selectedColumn, tile);
                    gameOrchestrator.getCurrentPlayer().firePropertyChange("gridUpdate",gameOrchestrator.getCurrentPlayer());
                    gameOrchestrator.getCurrentPlayer().firePropertyChange("tilesUpdate", gameOrchestrator.getCurrentPlayer());
                }else{throw new InvalidMoveException("Selected tile index not valid", 3);}
            }
            else{
                selectedColumn = -1;
                throw new InvalidMoveException("Selected column doesn't have enough space", 3);
            }
            //if the selected column doesn't have enough space the player has to change it, to allow
            //the player to change it the default state selectedColumn has to be set back to -1
        }
        else throw new InvalidMoveException("Tiles must be placed in the same column", 3);
    }

}

