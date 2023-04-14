package org.example;

import java.util.List;

public class topUpMessage {

    MessageID messageID;
    String username;

    int column;

    int tileIndex;

    public topUpMessage(String username, int column, int tileIndex) {
        this.messageID = MessageID.PICK;
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
    public MessageID getMessageID(){
        return messageID;
    }
    public int getTileIndex(){
        return tileIndex;
    }
}
