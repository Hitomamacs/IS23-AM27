package org.project.Controller.States;

//ConnectionCheckState controls that the player is connected through its isConnected boolean,
//if the player is disconnected just skips his turn. If the player is connected two possibilities are left
//the player has some pickedTiles as he had previously disconnected during topUp state and now has to place
//the tiles, or the player simply has to pick some tiles from the board in which case we have to check if
//the board needs refilling

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;

public class ConnectionCheckState implements GameState {

    private final int stateID = 1;
    private transient GameOrchestrator gameOrchestrator;

    public ConnectionCheckState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public int getStateID(){
        return stateID;
    }

    @Override
    public void changeState() {
        if(gameOrchestrator.getCurrentPlayer().isConnected()){
            if(gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty()) {
                System.out.println("Server waiting for " + gameOrchestrator.getCurrentPlayer().getNickname() + " to pick tiles"  );
                gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
                gameOrchestrator.setCurr_sate_id(10);
                //gameOrchestrator.changeState(new RefillState(gameOrchestrator));
                //gameOrchestrator.setCurr_sate_id(5);
                /*try {
                    gameOrchestrator.executeState();
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }
                */

            }
            else{
                int previousSelectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();
                gameOrchestrator.changeState(new TopUpState(gameOrchestrator, previousSelectedColumn));
                gameOrchestrator.setCurr_sate_id(7);
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
            gameOrchestrator.setCurr_sate_id(6);
            try {
                gameOrchestrator.executeState();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }


    }


    @Override
    public void execute() {
        changeState();
    }
}
