package org.project;

import java.util.HashMap;

public class ScoreBoardMsg extends Message{

    private HashMap<String, Integer> scoreBoard;

    public ScoreBoardMsg(HashMap<String, Integer> scoreBoard){
        super();
        this.setMessageID(MessageID.SCORE_UPDATE);
        this.scoreBoard = scoreBoard;
    }

    public HashMap<String, Integer> getScoreBoard() {
        return scoreBoard;
    }
}
