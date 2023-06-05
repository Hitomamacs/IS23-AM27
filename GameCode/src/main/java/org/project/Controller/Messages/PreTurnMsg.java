package org.project.Controller.Messages;

import java.util.HashMap;
import java.util.List;

public class PreTurnMsg extends Message {

    private String username;
    private boolean move_type; //True if pick, false if topup
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


