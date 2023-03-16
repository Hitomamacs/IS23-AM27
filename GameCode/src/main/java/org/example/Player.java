package org.example;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String nickname;
   private boolean isConnected;
   private int score;
   private PlayerGrid playerGrid;
   private boolean[] CompletedCGoals;
   private PersonalGoal myPersonalGoal;
   private List<Tile> pickedTiles;
    public Player() {
        CompletedCGoals = new boolean[2];
        playerGrid = new PlayerGrid();
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
    public List<Tile> getPickedTiles() {
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
    public void setPickedTiles(List<Tile> pickedTiles) {
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
    //verifyPGoalPoints returns the points that the player receives from his personal goal
    public int verifyPGoalPoints(){

        HashMap<Coordinates,Color> colorMap;
        Tile tile;
        Coordinates coordinate;
        Color color;
        int count = 0;
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
        switch(count){  //switch to determine how many points are assigned based on number of correct tiles
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 12;
        }
    }

    //verifyExtraPoints is the method that checks how many points to assign for groups of same colored tiles
    //in playergrid adiacent to each other.
    //public int verifyExtraPoints()

}

