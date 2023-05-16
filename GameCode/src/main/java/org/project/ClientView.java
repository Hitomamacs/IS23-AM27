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
    private HashMap<String, Integer> personalGoalViews;
    private List<Integer> commonGoalView;

    private static final String ANSI_RESET = "\u001b[0m";
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
        colorMap.put("Y", "\u001B[33m");
        // Add more colors as needed
    }

    private static final Map<String, String> backgroundColorMap;
    static {
        backgroundColorMap = new HashMap<>();
        backgroundColorMap.put("I", "\u001b[40m");  //red dove non puoi prendere le cose
        backgroundColorMap.put("G", "\u001b[42m");  //green
        backgroundColorMap.put("B", "\u001b[44m");  //blue
        backgroundColorMap.put("P", "\u001b[45m");  //pink
        backgroundColorMap.put("A", "\u001b[46m");  //azure
        backgroundColorMap.put("W", "\u001b[47m");  //white
        backgroundColorMap.put("Y", "\u001b[43m");  //yellow
        backgroundColorMap.put("N", "\u001b[40m");  //black quando hai già pescato la tile e il posto diventa vuoto
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
                    System.out.print(ANSI_BROWN + "   " + ANSI_RESET);
                } else {
                    String backgroundColor = backgroundColorMap.get(grid[i][j]);
                    if (backgroundColor == null) backgroundColor = ANSI_BROWN; // Default to brown if color not found, usless check
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println(" 0  1  2  3  4");
    }

    public void printBoard() {
        System.out.println("\nPrinting updated board:");
        System.out.println("   0  1  2  3  4  5  6  7  8");
        for(int i = 0; i < board.length; i++){
            //System.out.println(i);
            System.out.print(i + " ");
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != null){
                    //System.out.println(j);
                    String backgroundColor = backgroundColorMap.get(board[i][j]);
                    System.out.print(backgroundColor + "   " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public void printTiles(String playerName) {
        int count = 0;
        System.out.println("\nPrinting " + playerName + " updated tiles");
        String[] tiles = tilesview.get(playerName);
        for (String tile : tiles) {
            String backgroundColor = backgroundColorMap.get(tile);
            System.out.println(count + " " + backgroundColor + "   " + ANSI_RESET);
            count++;
        }
    }

    public void printPersonalGoal(String playerName){
        System.out.println("\nPrinting " + playerName + " personal Goal : "+ personalGoalViews.get(playerName));
    }

    public void printCommonGoal(){
        System.out.println("\n Printing common goals ");
        for(Integer id: commonGoalView){
            System.out.println(id + " ");
        }
    }


    public HashMap<String, Integer> getPersonalGoalViews() {
        return personalGoalViews;
    }

    public void setPersonalGoalViews(HashMap<String, Integer> personalGoalViews) {
        this.personalGoalViews = personalGoalViews;
    }

    public List<Integer> getCommonGoalView() {
        return commonGoalView;
    }

    public void setCommonGoalView(List<Integer> commonGoalView) {
        this.commonGoalView = commonGoalView;
    }
}
