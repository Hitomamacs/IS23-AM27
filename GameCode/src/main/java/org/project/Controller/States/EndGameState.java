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
            gameOrchestrator.getGame().firePropertyChange("ScoreBoardUpdate", gameOrchestrator.getGame());
        }
        gameOrchestrator.getGame().setGameStarted(false);
    }

    @Override
    public void changeState() {

    }
}
