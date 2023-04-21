package org.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * classe che rappresenta la view lato client
 */
public class ClientView {

    String[][] board;
    List<Integer> pointStack;
    HashMap<String, String[][] > gridsview;
    HashMap<String,String[]> tilesview;
    String errorMessage;

    public ClientView() {
       pointStack=new ArrayList<>();
       gridsview= new HashMap<>();
       tilesview= new HashMap<>();
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
}
