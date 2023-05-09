package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.View.PopUpView;
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
    public void changeState() {

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
            //Update view
            gameOrchestrator.getGame().getView().updateView(currentPlayer, "Tiles must be adjacent and have at least one free side");
            PopUpView view = gameOrchestrator.getGame().getView().getPopUpViews().get(currentPlayer);
            gameOrchestrator.getGame().getSupport().firePropertyChange("popUp", null, view);
        }

    }

    @Override
    public void execute() {
        changeState();
    }

}
