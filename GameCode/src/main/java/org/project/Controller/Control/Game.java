package org.project.Controller.Control;
import org.project.Controller.Server.Server;
import org.project.Controller.View.VirtualView;
import org.project.Model.*;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.CommonGoals.CommonGoal_Deck;
import org.project.ObservableObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private List<Player> players;
    private GameOrchestrator orchestrator;
    private GameBoard gameBoard;
    private TileBag tileBag;
    private int numPlayers;
    private PersonalGoal_Deck personalGoalDeck;
    private PointAssigner pointAssigner;
    private List<CommonGoal> commonGoals;
    private CommonGoal_Deck commonGoalDeck;
    private String filename;
    //TODO WHat happens if game terminates?
    public Game(){
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        numPlayers = 2;
    }
    public Game(Server server){
        this.server = server;
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        this.numPlayers = 4;
    }
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
    public void pickPersonalGoals(){
        for(Player player : players){
            player.setMyPersonalGoal(personalGoalDeck.getRandom());
            player.firePropertyChange("PGoalUpdate", player);
        }
    }
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
