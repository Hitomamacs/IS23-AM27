package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PickState implements GameState{

    private GameOrchestrator gameOrchestrator;

    public void Pick(){
        HashMap<Coordinates, Tile> pickedTiles = gameOrchestrator.getPickedTiles();
        gameOrchestrator.getCurrentPlayer().modifyPickedTiles((Set<Tile>) pickedTiles.values()); //TODO see if it works
    }

    @Override
    public void changeState() {
        if(gameOrchestrator.getCurrentPlayer().isConnected())
            gameOrchestrator.changeState(new TopUpState());
        else
            gameOrchestrator.changeState(new ReplaceState());


    }

    @Override
    public void execute() {
        Pick();
        changeState();
    }
}
