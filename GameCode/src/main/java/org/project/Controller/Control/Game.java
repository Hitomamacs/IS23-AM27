package org.project.Controller.Control;
import com.google.gson.annotations.Expose;
import org.project.Controller.Server.Server;
import org.project.Model.*;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.CommonGoals.CommonGoal_Deck;
import org.project.ClientPack.ObservableObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a Game within the system. It contains all elements of the game like
 * the server, players, game board, tile bag, point assigner, goal decks and orchestrator.
 * It inherits from ObservableObject class to enable other objects to observe its changes.
 */

public class Game extends ObservableObject{
    public Server getServer() {
        return server;
    }
    public void setServer(Server server) {
        this.server = server;
    }
    private Server server;
    private Persistencer persistencer;
    private boolean gameStarted;
    @Expose
    private List<Player> players;

    public void setPersistencer(Persistencer persistencer) {
        this.persistencer = persistencer;
    }

    private GameOrchestrator orchestrator;
    @Expose
    private GameBoard gameBoard;
    @Expose
    private TileBag tileBag;
    @Expose
    private int numPlayers;
    private PersonalGoal_Deck personalGoalDeck;
    @Expose
    private PointAssigner pointAssigner;

    public void setCommonGoals(List<CommonGoal> commonGoals) {
        this.commonGoals = commonGoals;
    }

    private List<CommonGoal> commonGoals;
    private CommonGoal_Deck commonGoalDeck;
    private HashMap<String, Integer> scoreboard;
    private String filename;
    //TODO WHat happens if game terminates?

    /**
     * constructor
     */
    public Game(){
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        numPlayers = 2;
    }

    public Game(GameOrchestrator orchestrator){
        this.gameBoard = orchestrator.getGameBoard();
        this.tileBag = orchestrator.getTileBag();
        this.players = new ArrayList<>();
        this.persistencer = new Persistencer();
        for(Player player : orchestrator.getPlayers()){
            this.players.add(new Player(player));
        }
    }

    public Game(Server server){
        this.server = server;
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        this.numPlayers = 4;
        scoreboard = new HashMap<>();
    }

    /**
     * Initializes the game with the specified players.
     *
     * @param players The list of players for the game.
     */
    public void gameInit(List<Player> players){
        System.out.println("\nInitializing game (Game method gameInit)");
        this.numPlayers = players.size();
        for(Player player : players){
            this.players.add(player);
        }
        gameStarted = true;
        tileBag = new TileBag();
        tileBag.initializeBag();
        gameBoard = new GameBoard(9,9,this.numPlayers);
        commonGoalDeck = new CommonGoal_Deck();
        commonGoalDeck.fillDeck();
        personalGoalDeck = new PersonalGoal_Deck();
        personalGoalDeck.fillDeck("test_1.json");
        pointAssigner = new PointAssigner();
        pointAssigner.initialize(this.numPlayers, 2);
        orchestrator = new GameOrchestrator(players, gameBoard, commonGoals, pointAssigner, tileBag, this);
        //fillGrids(); //TODO remember to remove
        filename = persistencer.get_file_name(orchestrator); //TODO WRONG!!!! save names once all users logged, missing logic rn
    }
    public GameOrchestrator getOrchestrator() {
        return orchestrator;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
    public void setOrchestrator(GameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    /**
     * Sets the game started flag to the specified value.
     *
     * @param value The value indicating whether the game has started.
     */
    public void setGameStarted(boolean value){
        gameStarted = value;
        firePropertyChange("GameStateUpdate", gameStarted);
    }
    public String getFilename() {
        return filename;
    }
    public Persistencer getPersistencer() {
        return persistencer;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public TileBag getTileBag() {
        return tileBag;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public PersonalGoal_Deck getPersonalGoalDeck() {
        return personalGoalDeck;
    }
    public PointAssigner getPointAssigner() {
        return pointAssigner;
    }
    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }
    public CommonGoal_Deck getCommonGoalDeck() {
        return commonGoalDeck;
    }
    public boolean getGameStarted() {
        return gameStarted;
    }
    public HashMap<String, Integer> getScoreboard(){
        return scoreboard;
    }
    public void setScoreboard(HashMap<String, Integer> score){
        scoreboard = score;
    }
    //TODO remove this function later on, just used to start the game with almost full grids for testing
    public void fillGrids(){
        for(Player player : players){
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    player.getPlayerGrid().topUp(j, tileBag.pickSingle());
                }
            }
            player.getPlayerGrid().topUp(0, tileBag.pickSingle());
            player.getPlayerGrid().topUp(1, tileBag.pickSingle());
            player.getPlayerGrid().topUp(2, tileBag.pickSingle());
        }
    }

    /**
     * This method randomly selects a personal goal from the personal goal deck for each player
     * and assigns it as their own personal goal. It then notifies the observers by firing a
     * property change event with the property name "PGoalUpdate".
     */
    public void pickPersonalGoals(){
        for(Player player : players){
            player.setMyPersonalGoal(personalGoalDeck.getRandom());
            player.firePropertyChange("PGoalUpdate", player);
        }
    }

    /**
     * This method selects two random common goals from the common goal deck and adds them to the
     * list of common goals for the game. It then fires a property change event with the property
     * name "CGoalUpdate" to notify the observers about the updated common goals.
     */
    public void pickCommonGoals(){
        commonGoals.add(commonGoalDeck.getRandom());
        commonGoals.add(commonGoalDeck.getRandom());
        this.firePropertyChange("CGoalUpdate", this);
    }
    public Player getPlayerFromUsername(String username){
        for(Player player : this.getPlayers()){
            if(player.getNickname().equals(username)){
                return player;
            }
        }
        return null;
    }

}
