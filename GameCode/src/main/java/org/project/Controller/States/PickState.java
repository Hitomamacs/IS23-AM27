package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.Coordinates;
import org.project.Model.Tile;

import java.util.ArrayList;
import java.util.List;

public class PickState implements GameState {

    private final int stateID = 4;

    public int getStateID(){
        return stateID;
    }

    private transient GameOrchestrator gameOrchestrator;

    public PickState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public void Pick() {
        List<Coordinates> coordinates = gameOrchestrator.getPickedCoordinates();
        List<Tile> pickedTiles = new ArrayList<Tile>();
        for (Coordinates c : coordinates) {
            pickedTiles.add(gameOrchestrator.getGameBoard().pick(c));
            gameOrchestrator.getGameBoard().firePropertyChange("boardUpdate",gameOrchestrator.getGameBoard());
        }
        gameOrchestrator.getCurrentPlayer().modifyPickedTiles(pickedTiles);
        gameOrchestrator.getCurrentPlayer().firePropertyChange("tilesUpdate", gameOrchestrator.getCurrentPlayer());
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
        gameOrchestrator.changeState(new RefillState(gameOrchestrator));
        gameOrchestrator.setCurr_sate_id(5);
        try {
            gameOrchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
       /* if(!gameOrchestrator.getCurrentPlayer().isConnected()){
            gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(6);
        }
        else {System.out.println("Entering TopUpState waiting for player " + gameOrchestrator.getCurrentPlayer().getNickname() + " to move");
              gameOrchestrator.changeState(new TopUpState(gameOrchestrator));
              gameOrchestrator.setCurr_sate_id(7);
        }
       */

    }


    @Override
    public void execute() {
 //       if(!DisconnectState()){
        System.out.println("Pick executing: ");
        Pick();
        System.out.println("-> model changed");
        //Now we update the Virtual View
        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        Tile[] playerTiles = gameOrchestrator.getCurrentPlayer().getPickedTiles();
        //gameOrchestrator.getGame().getView().updateView(gameOrchestrator.getGameBoard());
        //gameOrchestrator.getGame().getView().updateView(playerTiles, currentPlayer);
        gameOrchestrator.flushCoordinates();
        gameOrchestrator.getGame().getPersistencer().saveGame(gameOrchestrator, gameOrchestrator.getGame().getFilename());
        System.out.println("-> VirtualView updated ");
        changeState();
    }

}
