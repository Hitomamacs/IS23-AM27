package org.project;

public class topUpMessage extends Message{

    MessageID messageID;
    String username;
    int column;
    int tileIndex;
    public topUpMessage(MessageID ID,String username, int column, int tileIndex) {
        super(username);
        this.messageID = MessageID.TOPUP;
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
