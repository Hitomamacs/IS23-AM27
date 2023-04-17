package org.Project;
//ConnectionCheckState controls that the player is connected through its isConnected boolean,
//if the player is disconnected just skips his turn. If the player is connected two possibilities are left
//the player has some pickedTiles as he had previously disconnected during topUp state and now has to place
//the tiles, or the player simply has to pick some tiles from the board in which case we have to check if
//the board needs refilling

public class ConnectionCheckState implements GameState{
    private GameOrchestrator gameOrchestrator;

    public ConnectionCheckState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void changeState() {
        if(gameOrchestrator.getCurrentPlayer().isConnected()){
            if(gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty()) {
                gameOrchestrator.changeState(new RefillState(gameOrchestrator));
                gameOrchestrator.executeState();
            }
            else{
                int previousSelectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();
                gameOrchestrator.changeState(new TopUpState(gameOrchestrator, previousSelectedColumn));
                //Otherwise if player had disconnected in between TopUps he could place remaining tiles in
                //a different column, note that this means that at the end of the TopUp phase of a player the
                //Selected column in player has to be set to the default value -1.This is because had the player
                //disconnected before his first TopUp the connectionCheckState would force him to use the same
                //column of the previous TopUp phase. In stead this way in that scenario the TopUps selectedColumn
                //would be -1 and the player would be allowed to select a new column
            }
        }
        else{
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
            gameOrchestrator.executeState();
        }


    }


    @Override
    public void execute() {
        changeState();
    }
}
