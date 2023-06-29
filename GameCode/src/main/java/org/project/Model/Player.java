package org.project.Model;


import com.google.gson.annotations.Expose;
import org.project.ClientPack.ObservableObject;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.project.Controller.Control.Persistencer.gson_parser;

/**
 * Class that represents the player of the game
 */

public class Player extends ObservableObject {
    @Expose
    private String nickname;
    @Expose
    private boolean isConnected;
    @Expose
    private int score;
    @Expose
    private PlayerGrid playerGrid;
    @Expose
    private boolean[] CompletedCGoals;
    //TODO Pgoal ID
    private PersonalGoal myPersonalGoal;

    public int getPersonalGoalID() {
        return personalGoalID;
    }

    @Expose
    private int personalGoalID;
    @Expose
    int selectedColumn;
    @Expose
    int tileIndex;

    @Expose
    List<PersonalGoal> personalGoals_list;
    @Expose
    private Tile[] pickedTiles;

    /**
     * Constructor
     * @param nickname player's name
     */
    public Player(String nickname) {
        this.nickname = nickname;
        CompletedCGoals = new boolean[2];
        CompletedCGoals[0] = false;
        CompletedCGoals[1] = false;
        playerGrid = new PlayerGrid();
        pickedTiles = new Tile[3];
        selectedColumn = -1;
        this.isConnected = true;

        ClassLoader classLoader=this.getClass().getClassLoader();
        String filename= "PGoals.json";
        InputStream inputStream= classLoader.getResourceAsStream(filename);
        BufferedReader read= new BufferedReader(new InputStreamReader(inputStream));
        //open file as buffer reader
        //reader = Files.newBufferedReader(Paths.get(filename.getAbsolutePath()));
        POb_2Js pOb_2Js = gson_parser.fromJson(read, POb_2Js.class);
        List<PersonalGoal > personalGoals_list = pOb_2Js.getPersonalGoals_list();
    }

    /**
     * Constructor for recovering the player class (Persistencer)
     * @param player player's name
     */
    public Player(Player player){
        this.playerGrid = player.getPlayerGrid();
        this.nickname = player.getNickname();
        this.personalGoalID = player.getPersonalGoalID();
        this.myPersonalGoal = player.getMyPersonalGoal();
        this.score = player.getScore();
        this.isConnected = player.isConnected();
        this.CompletedCGoals = player.getCompletedCGoals();
        this.selectedColumn = player.getSelectedColumn();
        this.tileIndex = player.getTileIndex();
        this.pickedTiles = player.getPickedTiles();
    }

    /**
     * Method for Persinstencer
     */
    public void initializeSupport(){
        this.setSupport(new PropertyChangeSupport (this));
    }

    /**
     * The method reads personal goals data from a file using a BufferedReader and the Gson library.
     * The personal goals read are stored in the personalGoals_list.
     * @param filename
     */
    public void personal_list_init(String filename){
        Reader reader = null;

            ClassLoader classLoader=this.getClass().getClassLoader();
            InputStream inputStream= classLoader.getResourceAsStream(filename);
            BufferedReader read= new BufferedReader(new InputStreamReader(inputStream));

        POb_2Js pOb_2Js = gson_parser.fromJson(read, POb_2Js.class);

        personalGoals_list = pOb_2Js.getPersonalGoals_list();
    }

    public String getNickname() {
        return nickname;
    }
    public int getScore() {
        return score;
    }
    public boolean isConnected() {
        return isConnected;
    }
    public PlayerGrid getPlayerGrid() {
        return playerGrid;
    }
    public boolean[] getCompletedCGoals() {
        return CompletedCGoals;
    }
    public PersonalGoal getMyPersonalGoal() {
        return myPersonalGoal;
    }
    public Tile[] getPickedTiles() {
        return pickedTiles;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setConnected(boolean connected) {
        isConnected = connected;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setPlayerGrid(PlayerGrid playerGrid) {
        this.playerGrid = playerGrid;
    }
    public void setCompletedCGoals(boolean[] completedCGoals) {
        CompletedCGoals = completedCGoals;
    }
    public void setMyPersonalGoal(PersonalGoal personalGoal){
        myPersonalGoal = personalGoal;
        personalGoalID = myPersonalGoal.getPgoal_ID();
    }
    
    public int recoverPersonalGoal() {
        if (personalGoalID < 0 || personalGoalID > 12) {
            throw new IllegalArgumentException("Invalid personal goal ID: " + personalGoalID);
        }
        myPersonalGoal = new PersonalGoal(personalGoalID, personalGoals_list.get(personalGoalID).getGoals());
        return 1;
    }
    public void setPickedTiles(Tile[] pickedTiles) {
        this.pickedTiles = pickedTiles;
    }

    /**
     * The method is used to increment the score in Player.
     * Score can only be increased so if a negative int is passed it throws an exception
     * @param points
     */
    public void changeScore(int points){
        if (points < 0)
            throw new IllegalArgumentException("Points can only be added");
        score = score + points;
    }

    /**
     * The method changes the boolean that indicates if a player has already accomplished a certain common goal
     * @param position This is the position of the goal in the Array
     */
    public void modifyCompletedCGoals(int position){
        if(position < 0 || position > 1)
            throw new IllegalArgumentException("Position must be in {0,1}");
        CompletedCGoals[position] = !CompletedCGoals[position];
    }

    /**
     * The method puts the set of picked tiles returned by the pick state in the
     * player pickedTiles array, don't need to worry about the set being bigger than 3 as that check is made
     * by the method that passes the set.
     * @param tiles Picked tiles
     */
    public void modifyPickedTiles(List<Tile> tiles){
       for(int i = 0; i < tiles.size(); i++){
           pickedTiles[i] = tiles.get(i);
       }
    }

    /**
     * The methods returns true if the pickedTiles list is empty
     */
    public boolean pickedTilesIsEmpty(){
        boolean result = true;
        for(int i = 0; i < 3 && result; i++)
            if(pickedTiles[i] != null)
                result = false;
        return result;
    }

    /**
     * @return Number of tiles in player's pickedTiles
     */
    public int pickedTilesNum(){
        int result = 0;
        for(int i = 0; i < 3; i++){
            if(pickedTiles[i] != null)
                result++;
        }
        return result;
    }

    /**
     * The method allows player to select a tile from the tiles in his hand (pickedTiles)
     * @param position of the tile in pickedTiles
     * @return the tile that the player wants to topUp
     */
    public Tile selectTile(int position){
        Tile tile;
        tile = pickedTiles[position];
        pickedTiles[position] = null;
        return tile;
    }

    /**
     * @return the points that the player receives from his personal goal card
     */
    public int verifyPGoalPoints() {
        List<Goal> goals = myPersonalGoal.getGoals();
        long count = 0;

        for (Goal goal : goals) {
            Spot spot = playerGrid.getSpot(goal.getCoordinates());
            if (spot.isOccupied() && spot.getTile().getColor().equals(goal.getColor())) {
                count++;
            }
        }
        return calculateResult((int) count);
    }

    /**
     * The method calculates the points that the player has made with his personal goal card
     * @param count number of colored tiles in the correct position
     * @return personal goal points
     */
    private int calculateResult(int count) {
        return switch (count) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }

    /**
     * The method checks how many points to assign for groups of same colored tiles adjacent to each other in player grid.
     * We decided to use DFS (Depth first search).
     * If you have at least three tiles connected of the same color you get 2 points,
     * if you have four tiles you get 3 points,
     * if you have five tiles you get 4 points,
     * if you have six or more tiles you have 8 points.
     * @return points
     */
    public int verifyExtraPoints() {
        boolean[][] visited = new boolean[6][5];
        int result = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Spot spot = getSpot(i, j);
                if (spot.isOccupied() && !visited[i][j]) {
                    int count = verifyExtraPointHelper(i, j, spot.getTile().getColor(), visited);
                    result += calculatePoints(count);
                }
            }
        }
        return result;
    }

    private Spot getSpot(int i, int j) {
        return playerGrid.getSpot(new Coordinates(i, j));
    }

    private int calculatePoints(int count) {
        if (count == 3)
            return 2;
        else if (count == 4)
            return 3;
        else if (count == 5)
            return 5;
        else if (count > 5)
            return 8;
        return 0;
    }

    /**
     * The method is used in verifyExtraPoints to verify is we are in an admissible position in the grid and if the spot is occupied
     * @param i coordinate of the position of the tile we want to verify
     * @param j coordinate of the position of the tile we want to verify
     * @param color of the tile in the spot of the player grid
     * @param visited true or false
     * @return points
     */
    public int verifyExtraPointHelper(int i, int j, Color color, boolean[][] visited) {
        if (!isValidPos(i, j, playerGrid.getGrid().length, playerGrid.getGrid()[0].length)) {
            return 0;
        }
        Spot spot = getSpot(i, j);
        if (!spot.isOccupied() || spot.getTile().getColor() != color || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        return 1 +
                verifyExtraPointHelper(i + 1, j, color, visited) +
                verifyExtraPointHelper(i - 1, j, color, visited) +
                verifyExtraPointHelper(i, j + 1, color, visited) +
                verifyExtraPointHelper(i, j - 1, color, visited);
    }

    static boolean isValidPos(int i, int j, int n, int m) {
        return i >= 0 && j >= 0 && i < n && j < m;
    }
    public int getSelectedColumn() {
        return selectedColumn;
    }
    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }
    public int getTileIndex() {
        return tileIndex;
    }
    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }
}