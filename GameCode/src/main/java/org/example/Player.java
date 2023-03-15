package org.example;


import java.util.Set;

public class Player {
    private String nickname;
   private boolean isConnected;
   private int score;
   private PlayerGrid playerGrid;
   private boolean[] CompletedCGoals;
   //TODO  we still need to create the class PersonalGoal so right now this would give an error private PersonalGoal myPersonalGoal;
   private Set<Tile> pickedTiles;
   
    public Player() {
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
    public Set<Tile> getPickedTiles() {
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
    public void setPickedTiles(Set<Tile> pickedTiles) {
        this.pickedTiles = pickedTiles;
    }
}
