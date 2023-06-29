package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Model.Player;

import java.util.HashMap;

/**
 * The EndGameState class represents the game state when the game has ended.
 * It calculates the final scores for each player and updates the scoreboard.
 */

public class EndGameState implements GameState {

    private final int stateID = 2;
    transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public EndGameState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public int getStateID(){
        return stateID;
    }

    /**
     * Executes the game state. It calculates the final scores for each player,
     * updates the scoreboard, and sets the "gameStarted" flag to false in order to indicate
     * that the game has ended.
     */
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
