package org.example;

import java.util.HashMap;
import java.util.Map;

public class VerifyBoardableState implements GameState{
    private GameOrchestrator gameOrchestrator;


    public boolean verifyBoardable(){
        HashMap<Coordinates, Tile> pickedTiles = gameOrchestrator.getPickedTiles();
        for(Map.Entry<Coordinates, Tile> mapElement : pickedTiles.entrySet()){
            Coordinates coordinates = mapElement.getKey();
            Tile tile = mapElement.getValue();
            if(!gameOrchestrator.getGameBoard().verifyPickable(coordinates)){
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
