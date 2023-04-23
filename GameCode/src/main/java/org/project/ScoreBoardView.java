package org.project;

import java.util.HashMap;

public class ScoreBoardView {

    private HashMap<String, Integer> scoreBoard;

    public ScoreBoardView(){
        scoreBoard = new HashMap<>();
    }
    public HashMap<String, Integer> getScoreBoard(){
        return scoreBoard;
    }
    public void updateScoreBoardView(HashMap<String, Integer> score){
        scoreBoard = score;
    }
}
