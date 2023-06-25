package org.project.Model;


import com.google.gson.annotations.Expose;
import org.project.ClientPack.ObservableObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.project.Controller.Control.Persistencer.gson_parser;

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
    @Expose
   private int personalGoalID;
    @Expose
   int selectedColumn;
    @Expose
   int tileIndex;

    @Expose
    List<HashMap<Coordinates, Color>> personalGoals_list;

   private Tile[] pickedTiles;
    public Player(String nickname) {
        this.nickname = nickname;
        CompletedCGoals = new boolean[2];
        playerGrid = new PlayerGrid();
        pickedTiles = new Tile[3];
        Reader reader = null;
        selectedColumn = -1;
        this.isConnected = true;

        ClassLoader classLoader=this.getClass().getClassLoader();
        String filename= "test_1.json";
        InputStream inputStream= classLoader.getResourceAsStream(filename);
        BufferedReader read= new BufferedReader(new InputStreamReader(inputStream));
        //open file as buffer reader
        //reader = Files.newBufferedReader(Paths.get(filename.getAbsolutePath()));
        ArrayList<HashMap<Coordinates, Color>> personalGoals_list = gson_parser.fromJson(read, ArrayList.class);

    }
    public void personal_list_init(String filename){
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         personalGoals_list = gson_parser.fromJson(reader, List.class);
    }


    public String getNickname() {
        return nickname;
    }

    public boolean setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
        return isConnected;
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
        myPersonalGoal = new PersonalGoal(personalGoalID, personalGoals_list.get(personalGoalID ));
        return 1;
    }

    public void setPickedTiles(Tile[] pickedTiles) {
        this.pickedTiles = pickedTiles;
    }
    //changeScore is the method used to increment the score in player. Score can only increase so if a negative
    //int is passed it throws an exception
    public void changeScore(int points){
        if (points < 0)
            throw new IllegalArgumentException("Points can only be added");
        score = score + points;
    }
    //modifyCompletedCGoals is a method that allows me to change the boolean that indicates if a player
    //has already accomplished a certain common goal
    public void modifyCompletedCGoals(int position){
        if(position < 0 || position > 1)
            throw new IllegalArgumentException("Position must be in {0,1}");
        CompletedCGoals[position] = !CompletedCGoals[position];
    }
    //modifypickedTiles allows me to put the set of picked tiles returned by the pick state in the
    //player pickedTiles array, don't need to worry about the set being bigger than 3 as that check is made
    //by the method that passes the set
    public void modifyPickedTiles(List<Tile> tiles){
       for(int i = 0; i < tiles.size(); i++){
           pickedTiles[i] = tiles.get(i);
       }
    }
    //pickedTilesIsEmpty returns true if the pickedtiles list is empty
    public boolean pickedTilesIsEmpty(){
        boolean result = true;
        for(int i = 0; i < 3 && result; i++)
            if(pickedTiles[i] != null)
                result = false;
        return result;
    }
    //Tells me number of tiles in players pickedTiles
    public int pickedTilesNum(){
        int result = 0;
        for(int i = 0; i < 3; i++){
            if(pickedTiles[i] != null)
                result++;
        }
        return result;
    }
    //selectTile allows player to select a tile from the tiles in his hand(pickedTiles)
    public Tile selectTile(int position){
        Tile tile;
        tile = pickedTiles[position];
        pickedTiles[position] = null;
        return tile;
    }
    //verifyPGoalPoints returns the points that the player receives from his personal goal
    public int verifyPGoalPoints() {
        /*Map<Coordinates, Color> colorMap = myPersonalGoal.getColoredGoal();

        long count = colorMap.entrySet().stream()
                .filter(entry -> {
                    Spot spot = playerGrid.getSpot(entry.getKey());
                    return spot.isOccupied() && spot.getTile().getColor() == entry.getValue();
                })
                .count();

        return calculateResult((int) count);*/
        return 0;
    }


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

    //verifyExtraPoints is the method that checks how many points to assign for groups of same colored tiles
    //in playergrid adjacent to each other. You could use DFS or BFS but I decided to use DFS
    //if you have at least 3 tile connected ofthe same color you get 2 points if you have 4 3 points and if you have 5 5 points and i you have 6+ 8
    //if you have in a grid 2 groups of 3 tiles of the same color you get 4 points.
    //in verifyextrapoint helper we need also to verify if we are in an ammissible position in the grid and if the tile is occupied
    // we are using the class coordinates to represent the position in the grid
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

