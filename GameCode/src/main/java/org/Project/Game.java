package org.Project;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

<<<<<<< HEAD:GameCode/src/main/java/org/Project/Game.java
    Persistencer persistencer;

    TopUpListener topUpListener;
    CoordinatesListener coordinatesListener;

=======
    List<User> users;
>>>>>>> Sockets:GameCode/src/main/java/org/example/Game.java
    List<Player> players;
    GameOrchestrator orchestrator;
    GameBoard gameBoard;
    TileBag tileBag;

    int numPlayers;
    PersonalGoal_Deck personalGoalDeck;

    PointAssigner pointAssigner;
    List<CommonGoal> commonGoals;

    CommonGoal_Deck commonGoalDeck;



    //TODO WHat happens if game terminates?
    public Game(){
        users = new ArrayList<>();
        players = new ArrayList<>();
        persistencer = new Persistencer();
        commonGoals = new ArrayList<>();
    }
    public void gameInit(int num_players){
        tileBag = new TileBag();
        tileBag.initializeBag();
        gameBoard = new GameBoard(9,9,num_players);
        commonGoalDeck = new CommonGoal_Deck();
        commonGoalDeck.fillDeck();
        personalGoalDeck = new PersonalGoal_Deck();
        personalGoalDeck.fillDeck();
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
}
