package org.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * class representing the client side view
 */
public class ClientView {

    private String[][] board;
    private List<Integer> pointStack;
    private HashMap<String, String[][] > gridsview;
    private HashMap<String,String[]> tilesview;
    private String errorMessage;
    private HashMap<String, Integer> scoreBoard;

    public ClientView() {
       pointStack=new ArrayList<>();
       gridsview= new HashMap<>();
       tilesview= new HashMap<>();
       scoreBoard = new HashMap<>();
    }


    public String[][] getBoard() {
        return board;
    }

    public HashMap<String, String[][]> getGridsview() {
        return gridsview;
    }

    public List<Integer> getPointStack() {
        return pointStack;
    }

    public HashMap<String, String[]> getTilesview() {
        return tilesview;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HashMap<String, Integer> getScoreBoard() {
        return scoreBoard;
    }
}
