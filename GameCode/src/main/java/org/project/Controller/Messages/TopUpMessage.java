package org.project.Controller.Messages;

import org.project.Controller.Messages.Message;
import org.project.Controller.Messages.MessageID;

public class TopUpMessage extends Message {
    int column;
    int tileIndex;
    public TopUpMessage(String username, int column, int tileIndex) {
        super(username);
        this.setMessageID(MessageID.TOPUP);
        this.column = column;
        this.tileIndex = tileIndex;
    }
    public int getColumn(){
        return column;
    }
    public int getTileIndex(){
        return tileIndex;
    }
}
