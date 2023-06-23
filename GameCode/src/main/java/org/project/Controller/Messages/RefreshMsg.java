package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RefreshMsg extends Message{
    private String[][] board;
    private List<Integer> pointStack;
    private HashMap<String, String[][] > gridsview;
    private HashMap<String,String[]> tilesview;

    private HashMap<String, Integer> personalGoalViews;
    private List<Integer> commonGoalsView;
    public HashMap<String, String[]> getTilesview() {
        return tilesview;
    }
    public RefreshMsg(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsview, HashMap<String, String[]> tilesview, HashMap<String,Integer> pGoalView, List<Integer> cGoalView){
        super(MessageID.REFRESH_UPDATE);
        this.board = board;
        this.gridsview = gridsview;
        this.pointStack = pointStack;
        this.tilesview = tilesview;
        this.commonGoalsView=cGoalView;
        this.personalGoalViews=pGoalView;
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

    public HashMap<String, Integer> getPersonalGoalViews() {
        return personalGoalViews;
    }

    public void setPersonalGoalViews(HashMap<String, Integer> personalGoalViews) {
        this.personalGoalViews = personalGoalViews;
    }

    public List<Integer> getCommonGoalsView() {
        return commonGoalsView;
    }

    public void setCommonGoalsView(List<Integer> commonGoalsView) {
        this.commonGoalsView = commonGoalsView;
    }

}
