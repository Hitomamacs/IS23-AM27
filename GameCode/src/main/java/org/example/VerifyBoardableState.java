package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyBoardableState implements GameState{
    private GameOrchestrator gameOrchestrator;


    public boolean verifyBoardable(){
        List<Coordinates> pickedCoordinates = gameOrchestrator.getPickedCoordinates();
        for(Coordinates c : pickedCoordinates){;
            if(!gameOrchestrator.getGameBoard().verifyPickable(c)){
                return false;
            }
        }
        return true;

    }
    @Override
    public void changeState() {
        if(verifyBoardable())
            gameOrchestrator.changeState(new PickState());
        else
            gameOrchestrator.changeState(new VerifyGrillableState());


    }

    @Override
    public void execute() {
        changeState();
    }

}
