package org.example;


import java.util.*;

public class Player {
    private String nickname;
   private boolean isConnected;
   private int score;
   private PlayerGrid playerGrid;
   private boolean[] CompletedCGoals;
   private PersonalGoal myPersonalGoal;

   private Tile[] pickedTiles;
    public Player() {
        CompletedCGoals = new boolean[2];
        playerGrid = new PlayerGrid();
        pickedTiles = new Tile[3];

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
    public void modifyPickedTiles(Set<Tile> tiles){
       int i;
       i = 0;
       for(Tile tile : tiles){
           pickedTiles[i] = tile;
           i++;
       }
    }
    //pickedTilesIsEmpty returns true if the pickedtiles array is empty
    public boolean pickedTilesIsEmpty(){
        boolean result = true;
        int i = 0;
        while(i < 3 && result){
            if(pickedTiles[i] != null)
                result = false;
            i++;

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

        HashMap<Coordinates,Color> colorMap;
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
        switch(count){//switch to determine how many points are assigned based on number of correct tiles
            case 0:
                result = 0;
                break;
            case 1:
                result = 1;
                break;
            case 2:
                result = 2;
                break;
            case 3:
                result = 4;
                break;
            case 4:
                result = 6;
                break;
            case 5:
                result = 9;
                break;
            case 6:
                result = 12;
                break;
        }
        return result;
    }

    //verifyExtraPoints is the method that checks how many points to assign for groups of same colored tiles
    //in playergrid adjacent to each other.
    //public int verifyExtraPoints()

}

