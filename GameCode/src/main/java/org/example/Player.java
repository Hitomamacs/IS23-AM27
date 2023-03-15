package org.example;


import java.util.List;

public class Player {
    private String nickname;
   private boolean isConnected;
   private int score;
   private PlayerGrid playerGrid;
   private boolean[] CompletedCGoals;
   //TODO  we still need to create the class PersonalGoal so right now this would give an error private PersonalGoal myPersonalGoal;
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
    public void setPickedTiles(List<Tile> pickedTiles) {
        this.pickedTiles = pickedTiles;
    }

    //Change score is the method used to increment the score in player. Score can only increase so if a negative
    //int is passed it throws an exception
    public void changeScore(int points){
        if (points < 0)
            throw new IllegalArgumentException("Points can only be added");
        score = score + points;
    }
    public void modifyCompletedCGoals(int position){
        if(position  >= 2 || position < 0)
            throw new IllegalArgumentException("Position must be part of {0,1}");  //alternatively if(position >= CompletedCGoals.lenght)
        CompletedCGoals[position] = ! CompletedCGoals[position];
    }
}

