package org.example;
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
                gameOrchestrator.changeState(new TopUpState(gameOrchestrator));
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
