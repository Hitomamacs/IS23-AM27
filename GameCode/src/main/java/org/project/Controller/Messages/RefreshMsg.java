package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a message for refreshing the game state.
 * Contains information about the game board, point stack, grid views, tile views,
 * personal goal views and common goal views.
 */

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
    public HashMap<String, Integer> getPersonalGoalViews() {
        return personalGoalViews;
    }
    public List<Integer> getCommonGoalsView(){
        return commonGoalsView;
    }

    /**
     * Constructor
     * @param board the game board
     * @param pointStack the point stack
     * @param gridsview the grid views for each player
     * @param tilesview the tile views for each player
     * @param cGoalView the common goal views
     * @param pGoalView the personal goal views for each player
     */
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
    public void setPersonalGoalViews(HashMap<String, Integer> personalGoalViews) {
        this.personalGoalViews = personalGoalViews;
    }
    public void setCommonGoalsView(List<Integer> commonGoalsView) {
        this.commonGoalsView = commonGoalsView;
    }
}
