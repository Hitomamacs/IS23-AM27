package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.PlayerGrid;
import org.project.Model.Tile;

/**
 * TopUpState is responsible for handling the top-up phase of the game, where the player places a picked tile on his player grid.
 * It allows the player to select a column on his grid and places the tile in that column.
 * After top-up, it transitions to the appropriate next state based on the player's remaining picked tiles.
 */

public class TopUpState implements GameState {

    public int getStateID(){
        return stateID;
    }
    private final int stateID = 7;
    private GameOrchestrator gameOrchestrator;

    int selectedColumn;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public TopUpState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
        selectedColumn = -1;
    }

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     * @param selectedColumn the selected column on the player's grid
     */
    public TopUpState(GameOrchestrator orchestrator, int selectedColumn){
        this.gameOrchestrator = orchestrator;
        this.selectedColumn = selectedColumn;
    }

    /**
     * If the player has not yet finished placing his picked tiles in the grid then he remains in TopUpState
     * If the player has finished placing his picked tiles in the grid then the value of the column selected by the player
     * is set to -1 and then the current state is set to VerifyCommonGoalState
     */
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

    /**
     * Executes the top-up phase of the game.
     * It calls the `topUp()` method to perform the top-up action and saves the game state.
     * Finally, it calls the `changeState()` method to transition to the appropriate next state.
     * @throws InvalidMoveException if the top-up action is invalid
     */
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
    } */

    /**
     * Performs the top-up action by placing the picked tile on the selected column of the player's grid.
     * It checks if the selected column has enough space for the tile, and if so, it updates the player's grid
     * and triggers necessary updates.
     * @throws InvalidMoveException if the top-up action is invalid, such as selecting an invalid tile index
     *                          or placing tiles in different columns
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
                } else{throw new InvalidMoveException("Selected tile index not valid", 3);}
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

