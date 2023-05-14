package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.Coordinates;

import java.util.List;

public class VerifyBoardableState implements GameState {
    private final int stateID = 8;
    private transient GameOrchestrator gameOrchestrator;

    public VerifyBoardableState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public boolean verifyBoardable(){
        List<Coordinates> pickedCoordinates = gameOrchestrator.getPickedCoordinates();
        for(Coordinates c : pickedCoordinates){
            if(!gameOrchestrator.getGameBoard().verifyPickable(c)){
                return false;
            }
        }
        return true;

    }
    @Override
    public void changeState() throws InvalidMoveException {

        String currentPlayer = gameOrchestrator.getCurrentPlayer().getNickname();
        System.out.println("Boardable test");
        if(verifyBoardable()) {
            System.out.println("Boardable passed");
            gameOrchestrator.changeState(new PickState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(4);
            gameOrchestrator.executeState();
        }

        else {
            gameOrchestrator.flushCoordinates();
            gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
            gameOrchestrator.setCurr_sate_id(10);
            throw new InvalidMoveException("Tiles must be adjacent and have at least on free side");
        }

    }

    @Override
    public void execute() throws InvalidMoveException {
        changeState();
    }

}
