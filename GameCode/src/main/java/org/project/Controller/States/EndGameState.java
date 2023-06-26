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

        System.out.println("Entered EndGameState");
        int points = 0;
        HashMap<String, Integer> score = new HashMap<>();
        for(Player p : gameOrchestrator.getPlayers()){
            points = p.verifyExtraPoints() + p.verifyPGoalPoints() + p.getScore();
            score.put(p.getNickname(), points);
            gameOrchestrator.getGame().setScoreboard(score);
            gameOrchestrator.getGame().firePropertyChange("ScoreBoardUpdate", gameOrchestrator.getGame());
            //System.out.println("Player" + p.getNickname() + "has scored" + points+ "points");
        }
        System.out.println("About to set gameStarted to false (EndGameState)");
        gameOrchestrator.getGame().setGameStarted(false);
    }

    @Override
    public void changeState() {

    }
}
