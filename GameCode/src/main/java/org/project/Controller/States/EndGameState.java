package org.project.Controller.States;


import org.project.Controller.Control.GameOrchestrator;
import org.project.Model.Player;

import java.util.HashMap;

public class EndGameState implements GameState {

    private final int stateID = 2;

    transient GameOrchestrator gameOrchestrator;
    public EndGameState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    @Override
    public void execute() {
        int points = 0;
        HashMap<String, Integer> score = new HashMap<>();
        for(Player p : gameOrchestrator.getPlayers()){
            points = p.verifyExtraPoints() + p.verifyExtraPoints() + p.getScore();
            score.put(p.getNickname(), points);
        }
        //gameOrchestrator.getGame().getView().updateView(score);
        //TODO will have to change some variable so that the server knows the game has ended and can close
    }

    @Override
    public void changeState() {

    }
}
