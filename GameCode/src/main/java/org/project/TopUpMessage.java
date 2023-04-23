package org.project;

public class TopUpMessage extends Message{
    int column;
    int tileIndex;
    public TopUpMessage(MessageID ID,String username, int column, int tileIndex) {
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
