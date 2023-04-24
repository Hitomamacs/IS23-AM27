package org.project.Controller.Messages;

import org.project.Controller.Messages.Message;
import org.project.Controller.Messages.MessageID;

import java.util.HashMap;
import java.util.List;

public class RefreshMsg extends Message {

    private String[][] board;
    private List<Integer> pointStack;
    private HashMap<String, String[][] > gridsview;
    private HashMap<String,String[]> tilesview;

    public HashMap<String, String[]> getTilesview() {
        return tilesview;
    }

    public RefreshMsg(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsview, HashMap<String, String[]> tilesview){
        super();
        this.setMessageID(MessageID.REFRESH_UPDATE);
        this.board = board;
        this.gridsview = gridsview;
        this.pointStack = pointStack;
        this.tilesview = tilesview;
    }
    public RefreshMsg(String username, String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsview, HashMap<String, String[]> tilesview){
        super(username);
        this.setMessageID(MessageID.REFRESH_UPDATE);
        this.board = board;
        this.gridsview = gridsview;
        this.pointStack = pointStack;
        this.tilesview = tilesview;
    }

    public String[][] getBoard() {
        return board;
    }

    public List<Integer> getPointStack() {
        return pointStack;
    }

    public HashMap<String, String[][]> getGridsview() {
        return gridsview;
    }
}
