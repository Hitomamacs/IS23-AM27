package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameOrchestrator {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean finalRoundFlag;
    private GameState State;
    private GameBoard gameBoard;
    private List<CommonGoal> selectedCGoal;
    private PointAssigner pointAssigner;
    private TileBag tileBag;
    private List<Coordinates> pickedCoordinates;
    Game game;

    public GameState getState() {
        return State;
    }

    public void setState(GameState state) {
        State = state;
    }

    public GameOrchestrator(List<Player> players, GameBoard gameBoard, List<CommonGoal> selectedCGoal, PointAssigner pointAssigner, TileBag tileBag, Game game){
        this.players=players;
        this.currentPlayerIndex=0;
        this.finalRoundFlag=false;
        this.State = new RefillState();
        this.gameBoard=gameBoard;
        this.selectedCGoal=selectedCGoal;
        this.pointAssigner=pointAssigner;
        this.tileBag=tileBag;
        this.pickedCoordinates =new ArrayList<>();
        this.game=game;
    }

    public void changeState(GameState state){
        this.State=state;
    }

    public void excecuteState(){
        this.State.execute();
    }

    public Player getCurrentPlayer(){
        return this.players.get(this.currentPlayerIndex);
    }

    public int CurrentPlayerIndex(){
        return this.currentPlayerIndex;
    }

    public Player nextPlayer(){
        if(this.currentPlayerIndex==this.players.size()-1){
            this.currentPlayerIndex=0;
        }
        else{
            this.currentPlayerIndex++;
        }
        return this.players.get(this.currentPlayerIndex);
    }

    public void setFinalRoundFlag(boolean flag){
        this.finalRoundFlag=flag;
    }

    public boolean getFinalRoundFlag(){
        return this.finalRoundFlag;
    }

    public TileBag getTileBag(){
        return this.tileBag;
    }

    public GameBoard getGameBoard(){
        return this.gameBoard;
    }

public List<CommonGoal> getSelectedCGoal(){
        return this.selectedCGoal;
    }

    public PointAssigner getPointAssigner(){
        return this.pointAssigner;
    }

    public List<Coordinates> getPickedCoordinates(){
        return this.pickedCoordinates;
    }

    public void setPickedCoordinates(List<Coordinates> pickedCoordinates){
        this.pickedCoordinates = pickedCoordinates;
    }

    public Game getGame(){
        return this.game;
    }

}
