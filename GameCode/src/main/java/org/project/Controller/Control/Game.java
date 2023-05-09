package org.project.Controller.Control;

import org.project.Controller.Server.Server;
import org.project.Controller.View.VirtualView;
import org.project.Model.*;
import org.project.Model.CommonGoals.CommonGoal;
import org.project.Model.CommonGoals.CommonGoal_Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    private Server server;
    private Persistencer persistencer;

    private List<User> users;

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

    private VirtualView view;

    private String filename;

    public int getUsersSize(){
        return users.size();
    }



    //TODO WHat happens if game terminates?
    public Game(){
        users = new ArrayList<>();
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        numPlayers = 2;
    }
    public Game(Server server){
        this.server = server;
        users = new ArrayList<>();
        players = new ArrayList<>();
        commonGoals = new ArrayList<>();
        persistencer = new Persistencer();
        this.numPlayers = 4;
    }
    public void gameInit(int num_players){
        System.out.println("\nInitializing game (Game method gameInit)");
        this.numPlayers = num_players;
        gameStarted = true;
        tileBag = new TileBag();
        tileBag.initializeBag();
        gameBoard = new GameBoard(9,9,num_players);
        commonGoalDeck = new CommonGoal_Deck();
        commonGoalDeck.fillDeck();
        personalGoalDeck = new PersonalGoal_Deck();
        personalGoalDeck.fillDeck("test_1.json");
        commonGoals.add(commonGoalDeck.getRandom());
        commonGoals.add(commonGoalDeck.getRandom());

        List<User> playerOrder = new ArrayList<>(users);
        Random random = new Random();
        for(int i = 0; i< num_players; i++){
            int rand_index = random.nextInt(playerOrder.size());
            String nickname = playerOrder.remove(rand_index).getUsername();
            players.add(new Player(nickname));
            players.get(i).setMyPersonalGoal(personalGoalDeck.getRandom());
        }
        pointAssigner = new PointAssigner();
        pointAssigner.initialize(num_players, 2);
        orchestrator = new GameOrchestrator(players, gameBoard, commonGoals, pointAssigner, tileBag, this);
        view = new VirtualView(users);
        filename = persistencer.get_file_name(orchestrator); //TODO WRONG!!!! save names once all users logged, missing logic rn

    }


    public GameOrchestrator getOrchestrator() {
        return orchestrator;
    }

    public List<User> getUsers() {
        return users;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public void setOrchestrator(GameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    public VirtualView getView(){ return view; }

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
}
