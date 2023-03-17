package org.example;

import javax.swing.plaf.IconUIResource;
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
    private HashMap<Coordinates, Tile> pickedTiles;
    Game game;

    public GameOrchestrator(List<Player> players, GameBoard gameBoard, List<CommonGoal> selectedCGoal, PointAssigner pointAssigner, TileBag tileBag, Game game){
        this.players=players;
        this.currentPlayerIndex=0;
        this.finalRoundFlag=false;
        this.State = new RefillState();
        this.gameBoard=gameBoard;
        this.selectedCGoal=selectedCGoal;
        this.pointAssigner=pointAssigner;
        this.tileBag=tileBag;
        this.pickedTiles=new HashMap<>();
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

    public HashMap<Coordinates, Tile> getPickedTiles(){
        return this.pickedTiles;
    }

    public void setPickedTiles(HashMap<Coordinates, Tile> pickedTiles){
        this.pickedTiles=pickedTiles;
    }

    public Game getGame(){
        return this.game;
    }

}
