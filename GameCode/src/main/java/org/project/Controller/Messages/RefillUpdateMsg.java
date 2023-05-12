package org.project.Controller.Messages;

import org.project.Model.Coordinates;

import java.util.List;

public class RefillUpdateMsg extends Message{

    String[][] board;

    public RefillUpdateMsg(String[][] board) {
        super();
        this.setMessageID(MessageID.REFILL_UPDATE);
        this.board = board;
    }
}
