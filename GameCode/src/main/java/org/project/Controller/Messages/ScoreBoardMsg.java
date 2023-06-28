package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

import java.util.HashMap;

/**
 * Represents a message containing the score board information.
 * Contains a HashMap representing the scores of players.
 */
public class ScoreBoardMsg extends Message{
    private HashMap<String, Integer> scoreBoard;
    public ScoreBoardMsg(HashMap<String, Integer> scoreBoard){
        super(MessageID.SCORE_UPDATE);
        this.scoreBoard = scoreBoard;
    }
    public HashMap<String, Integer> getScoreBoard() {
        return scoreBoard;
    }
}
