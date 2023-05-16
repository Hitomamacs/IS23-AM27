package org.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class representing the client side view
 */
public class ClientView {

    private String[][] board;
    private List<Integer> pointStack;
    private HashMap<String, String[][] > gridsview;
    private HashMap<String,String[]> tilesview;
    private String popUpErrorMessage;
    private HashMap<String, Integer> scoreBoard;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BROWN = "\u001B[0;33m";

    private static final Map<String, String> colorMap;
    static {
        colorMap = new HashMap<>();
        colorMap.put("R", "\u001B[31m");
        colorMap.put("G", "\u001B[32m");
        colorMap.put("B", "\u001B[34m");
        colorMap.put("P", "\u001B[35m");
        colorMap.put("A", "\u001B[36m");
        colorMap.put("W", "\u001B[37m");
        colorMap.put("y", "\u001B[33m");
        // Add more colors as needed
    }

    public int getNum_tiles() {
        return num_tiles;
    }

    public void setNum_tiles(int num_tiles) {
        this.num_tiles = num_tiles;
    }

    private int num_tiles;

    public ClientView() {
       pointStack=new ArrayList<>();
       gridsview= new HashMap<>();
       tilesview= new HashMap<>();
       scoreBoard = new HashMap<>();
    }

    public void updateGridsView(String playerName, String[][] grid) {
        gridsview.put(playerName, grid);
    }

    public void updateTilesView(String playerName, String[] tiles) {
        tilesview.put(playerName, tiles);
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

    public String getPopUpErrorMessage() {
        return popUpErrorMessage;
    }

    public HashMap<String, Integer> getScoreBoard() {
        return scoreBoard;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public void setPointStack(List<Integer> pointStack) {
        this.pointStack = pointStack;
    }

    public void setGridsview(HashMap<String, String[][]> gridsview) {
        this.gridsview = gridsview;
    }

    public void setTilesview(HashMap<String, String[]> tilesview) {
        this.tilesview = tilesview;
    }

    public void setErrorMessage(String errorMessage) {
        this.popUpErrorMessage = errorMessage;
    }

    public void setScoreBoard(HashMap<String, Integer> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
    public void printGrid(String username) {
        String[][] grid = this.getGridsview().get(username);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == null) {
                    System.out.print(ANSI_BROWN + "□" + ANSI_RESET);
                } else {
                    String color = colorMap.get(grid[i][j]);
                    if (color == null) color = ANSI_BROWN; // Default to brown if color not found
                    System.out.print(color + "■" + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public void printBoard() {
        System.out.println("\nPrinting updated board:");
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != null){
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void printTiles(String playerName) {
        System.out.println("\nPrinting " + playerName + " updated tiles");
        String[] tiles = tilesview.get(playerName);
        for (String tile : tiles) {
            System.out.println(tile + " ");
        }
    }
}
