package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PickState implements GameState{

    private GameOrchestrator gameOrchestrator;

    public void Pick(){
        List<Coordinates> coordinates = gameOrchestrator.getPickedCoordinates();
        List<Tile> pickedTiles = new ArrayList<Tile>();
        for(Coordinates c : coordinates){
            pickedTiles.add(gameOrchestrator.getGameBoard().pick(c));
        }
        gameOrchestrator.getCurrentPlayer().modifyPickedTiles(pickedTiles);
    }

    @Override
    public void changeState() {
        gameOrchestrator.changeState(new TopUpState());

    }


    @Override
    public void execute() {
        Pick();
        changeState();
    }
}
