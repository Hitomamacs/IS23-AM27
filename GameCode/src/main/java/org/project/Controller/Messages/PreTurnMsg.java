package org.project.Controller.Messages;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a message sent before a player's turn.
 * Contains information about the username and the move type (pick or top-up).
 */
public class PreTurnMsg extends Message {

    private String username;
    private boolean move_type; //True if it is a pick, false if it is a topUp
    public PreTurnMsg(String username, boolean move) {

        super(MessageID.TURN_UPDATE);
        this.username = username;
        this.move_type = move;
    }
    public String getUsername(){
        return username;
    }
    public boolean getMove_Type(){
        return move_type;
    }
}


