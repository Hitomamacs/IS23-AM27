package org.project.Model;


import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.project.Controller.Control.Persistencer.gson_parser;

public class Player {
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
        try {
            reader = Files.newBufferedReader(Paths.get("test_1.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<HashMap<Coordinates, Color>> personalGoals_list = gson_parser.fromJson(reader, ArrayList.class);

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

    public int recoverPersonalGoal(){
        switch (personalGoalID){
            case 1:
                myPersonalGoal = new PersonalGoal(1, personalGoals_list.get(0));
                break;
            case 2:
                myPersonalGoal = new PersonalGoal(2, personalGoals_list.get(1));
                break;
            case 3:
                myPersonalGoal = new PersonalGoal(3, personalGoals_list.get(2));
                break;
            case 4:
                myPersonalGoal = new PersonalGoal(4, personalGoals_list.get(3));
                break;
            case 5:
                myPersonalGoal = new PersonalGoal(5, personalGoals_list.get(4));
                break;
            case 6:
                myPersonalGoal = new PersonalGoal(6, personalGoals_list.get(5));
                break;
            case 7:
                myPersonalGoal = new PersonalGoal(7, personalGoals_list.get(6));
                break;
            case 8:
                myPersonalGoal = new PersonalGoal(8, personalGoals_list.get(7));
                break;
            case 9:
                myPersonalGoal = new PersonalGoal(9, personalGoals_list.get(8));
                break;
            case 10:
                myPersonalGoal = new PersonalGoal(10, personalGoals_list.get(9));
                break;
            case 11:
                myPersonalGoal = new PersonalGoal(11, personalGoals_list.get(10));
                break;
            case 12:
                myPersonalGoal = new PersonalGoal(12, personalGoals_list.get(11));
                break;

        }
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
    public int verifyPGoalPoints(){

        Map<Coordinates,Color> colorMap;
        Tile tile;
        Coordinates coordinate;
        Color color;
        int count = 0;
        int result = 0;
        colorMap = myPersonalGoal.getColoredGoal();
        //for loop iterates on hash map and checks for each coordinate if there is a tile and if the color is correct
        //in which case count is incremented
        for (Map.Entry<Coordinates, Color> mapElement : colorMap.entrySet()){
            coordinate = mapElement.getKey();
            color = mapElement.getValue();
            if(playerGrid.getSpot(coordinate).isOccupied()){
                tile = playerGrid.getSpot(coordinate).getTile();
                if(tile.getColor() == color){
                    count++;
                }
            }
        }
        switch (count) {//switch to determine how many points are assigned based on number of correct tiles
            case 0 -> result = 0;
            case 1 -> result = 1;
            case 2 -> result = 2;
            case 3 -> result = 4;
            case 4 -> result = 6;
            case 5 -> result = 9;
            case 6 -> result = 12;
        }
        return result;
    }

    //verifyExtraPoints is the method that checks how many points to assign for groups of same colored tiles
    //in playergrid adjacent to each other. You could use DFS or BFS but I decided to use DFS
    //if you have at least 3 tile connected ofthe same color you get 2 points if you have 4 3 points and if you have 5 5 points and i you have 6+ 8
    //if you have in a grid 2 groups of 3 tiles of the same color you get 4 points.
    //in verifyextrapoint helper we need also to verify if we are in an ammissible position in the grid and if the tile is occupied
    // we are using the class coordinates to represent the position in the grid
    public int verifyExtraPoints(){
        boolean[][] visited;
        int i,j;
        int count = 0;
        int result = 0;
        Color color;
        visited = new boolean[6][5];
        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                visited[i][j] = false;
            }
        }
        for(i = 0; i < 6; i++){
            for(j = 0; j < 5; j++){
                if(playerGrid.getSpot(new Coordinates(i,j)).isOccupied() && !visited[i][j]){
                    color = playerGrid.getSpot(new Coordinates(i,j)).getTile().getColor();
                    count = verifyExtraPointHelper(i,j,color,visited);
                    if(count >= 3){
                        if(count == 3)
                            result = result + 2;
                        else if(count == 4)
                            result = result + 3;
                        else if(count == 5)
                            result = result + 5;
                        else
                            result = result + 8;
                    }
                }
            }
        }
        return result;
    }

    public int verifyExtraPointHelper(int i, int j, Color color, boolean[][] visited){
        int count = 1;
        visited[i][j] = true;
        if(isValidPos(i + 1,j,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) && playerGrid.getSpot(new Coordinates(i+1,j)).isOccupied() && playerGrid.getSpot(new Coordinates(i+1,j)).getTile().getColor() == color && !visited[i + 1][j]){
            count = count + verifyExtraPointHelper(i + 1,j,color,visited);
        }
        if(isValidPos(i - 1,j,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) && playerGrid.getSpot(new Coordinates(i-1,j)).isOccupied() && playerGrid.getSpot(new Coordinates(i-1,j)).getTile().getColor() == color && !visited[i - 1][j]){
            count = count + verifyExtraPointHelper(i - 1,j,color,visited);
        }
        if(isValidPos(i,j + 1,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) && playerGrid.getSpot(new Coordinates(i,j+1)).isOccupied() && playerGrid.getSpot(new Coordinates(i,j+1)).getTile().getColor() == color && !visited[i][j + 1]){
            count = count + verifyExtraPointHelper(i,j + 1,color,visited);
        }
        if(isValidPos(i,j - 1,playerGrid.getGrid().length,playerGrid.getGrid()[0].length) && playerGrid.getSpot(new Coordinates(i,j-1)).isOccupied() && playerGrid.getSpot(new Coordinates(i,j-1)).getTile().getColor() == color && !visited[i][j - 1]){
            count = count + verifyExtraPointHelper(i,j - 1,color,visited);
        }
        return count;
    }
    static boolean isValidPos(int i, int j, int n, int m)
    {
        if (i < 0 || j < 0 || i > n - 1 || j > m - 1) {
            return false;
        }
        return true;
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

