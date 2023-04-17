package org.project;

import java.util.ArrayList;
import java.util.List;

public class PickState implements GameState {

    private GameOrchestrator gameOrchestrator;

    public PickState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void Pick() {
        List<Coordinates> coordinates = gameOrchestrator.getPickedCoordinates();
        List<Tile> pickedTiles = new ArrayList<Tile>();
        for (Coordinates c : coordinates) {
            pickedTiles.add(gameOrchestrator.getGameBoard().pick(c));
        }
        gameOrchestrator.getCurrentPlayer().modifyPickedTiles(pickedTiles);
    }

    /*
    public boolean DisconnectState() {
        if (!gameOrchestrator.getCurrentPlayer().isConnected()) {
            gameOrchestrator.nextPlayer();
            gameOrchestrator.changeState(new StartTurnState());
            return true;
        }
        return false;

    }
*/
    @Override
    public void changeState() {
        if(!gameOrchestrator.getCurrentPlayer().isConnected())
            gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
        else gameOrchestrator.changeState(new TopUpState(gameOrchestrator));


    }


    @Override
    public void execute() {
 //       if(!DisconnectState()){
        Pick();
        gameOrchestrator.flushCoordinates();
        changeState();
    }

}
