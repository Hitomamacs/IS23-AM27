package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

public class TopUpMessage extends Message {
    private int column;
    private int tileIndex;
    private String username;
    public TopUpMessage(String username, int column, int tileIndex) {
        super(MessageID.TOPUP);
        this.username = username;
        this.column = column;
        this.tileIndex = tileIndex;
    }
    public String getUsername(){
        return username;
    }
    public int getColumn(){
        return column;
    }
    public int getTileIndex(){
        return tileIndex;
    }
}
