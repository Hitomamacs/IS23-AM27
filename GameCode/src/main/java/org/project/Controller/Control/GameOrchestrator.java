package org.project.Controller.Control;

import com.google.gson.annotations.Expose;
import org.project.Controller.Control.Game;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Controller.States.GameState;
import org.project.Controller.States.StartTurnState;
import org.project.Model.*;
import org.project.Model.CommonGoals.CommonGoal;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameOrchestrator class is responsible for managing the game orchestration, including players, current game state,
 * game board, selected common goals, point assigner, tile bag, and other game-related information.
 */

public class GameOrchestrator {

    @Expose
    private int curr_sate_id;

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

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

    public List<Integer> getSelectedCGoal_int() {
        return selectedCGoal_int;
    }

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

    public void setGame(Game game) {
        this.game = game;
    }

    @Expose
    private  Game game;

    public GameState getState() {
        return State;
    }

    public void setState(GameState state) {
        State = state;
    }

    public void setSelectedCGoal(List<CommonGoal> selectedCGoal) {
        this.selectedCGoal = selectedCGoal;
    }

    /**
     * Constructs a GameOrchestrator object with the specified players, game board, selected common goals, point assigner,
     * tile bag, and game.
     *
     * @param players          the list of players participating in the game
     * @param gameBoard        the game board
     * @param selectedCGoal    the list of selected common goals
     * @param pointAssigner    the point assigner
     * @param tileBag          the tile bag
     * @param game             the game object
     */

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
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
    }


    public void setSelectedCGoal_int() {
        for(CommonGoal cg: selectedCGoal){
            this.selectedCGoal_int.add(cg.getGoalID());
        }
    }

    /**
     * Changes the current state of the game to the specified state.
     *
     * @param state the new game state
     */
    public void changeState(GameState state){
        this.State=state;
    }

    /**
     * Executes the current game state. Throws an InvalidMoveException if an invalid move is made.
     *
     * @throws InvalidMoveException if an invalid move is made
     */

    public void executeState()throws InvalidMoveException {
        this.setCurr_sate_id(this.State.getStateID());
        this.State.execute();
    }

    public Player getCurrentPlayer(){
        return this.players.get(this.currentPlayerIndex);
    }

    public Player getPlayer(int i){
        return this.players.get(i);
    }
    public Player getPlayer(String username){
        List<Player> players = getPlayers();
        int i = 0;
        while(!players.get(i).getNickname().equals(username)){
            i++;
        }
        return players.get(i);
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
