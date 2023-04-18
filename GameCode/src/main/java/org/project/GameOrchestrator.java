package org.project;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class GameOrchestrator {

    @Expose
    private int curr_sate_id;
    @Expose
    private List<Player> players;
    @Expose
    private int currentPlayerIndex;
    @Expose
    private boolean finalRoundFlag;

    //TODO state id
    private GameState State;
    @Expose
    private GameBoard gameBoard;

    //TODO common goal id
    private List<CommonGoal> selectedCGoal;
    @Expose
    private List<Integer> selectedCGoal_int;
    @Expose
    private PointAssigner pointAssigner;
    @Expose
    private TileBag tileBag;
    @Expose
    private List<Coordinates> pickedCoordinates;
    @Expose
    private transient Game game;

    public GameState getState() {
        return State;
    }

    public void setState(GameState state) {
        State = state;
    }

    public void setSelectedCGoal(List<CommonGoal> selectedCGoal) {
        this.selectedCGoal = selectedCGoal;
    }

    public GameOrchestrator(List<Player> players, GameBoard gameBoard, List<CommonGoal> selectedCGoal, PointAssigner pointAssigner, TileBag tileBag, Game game){
        this.players=players;
        this.currentPlayerIndex=0;
        this.finalRoundFlag=false;
        this.State = new StartTurnState(this);
        this.gameBoard=gameBoard;
        this.selectedCGoal=selectedCGoal;
        this.pointAssigner=pointAssigner;
        this.tileBag=tileBag;
        this.pickedCoordinates =new ArrayList<>();
        this.game=game;
        this.selectedCGoal_int = new ArrayList<>();
        for(CommonGoal cg: selectedCGoal){
            this.selectedCGoal_int.add(cg.getGoalID());
        }
    }

    public void changeState(GameState state){
        this.State=state;
    }

    public void executeState(){
        this.State.execute();
    }

    public Player getCurrentPlayer(){
        return this.players.get(this.currentPlayerIndex);
    }

    public Player getPlayer(int i){
        return this.players.get(i);
    }

    public int CurrentPlayerIndex(){
        return this.currentPlayerIndex;
    }

    public List<Player> getPlayers(){
        return this.players;
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

    public void flushCoordinates(){
        pickedCoordinates.clear();
    }

    public int getCurr_sate_id() {
        return curr_sate_id;
    }

    public List<Integer> get_selected_cgoal_int(){
        return this.selectedCGoal_int;
    }



    public void setCurr_sate_id(int curr_sate_id) {
        this.curr_sate_id = curr_sate_id;
    }
}
