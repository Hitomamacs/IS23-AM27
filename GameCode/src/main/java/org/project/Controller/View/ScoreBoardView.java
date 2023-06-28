package org.project.Controller.View;

import java.util.HashMap;

/**
 * The class represents the ranking view
 */
public class ScoreBoardView {
    private HashMap<String, Integer> scoreBoard;

    /**
     * Constructor
     */
    public ScoreBoardView(){
        scoreBoard = new HashMap<>();
    }

    /**
     * Getter
     * @return scoreBoard
     */
    public HashMap<String, Integer> getScoreBoard(){
        return scoreBoard;
    }

    /**
     * The method updates the score board with a new configuration
     * @param score new configuration
     */
    public void updateScoreBoardView(HashMap<String, Integer> score){
        scoreBoard = score;
    }
}
