package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Game {

    TopUpListener topUpListener;
    CoordinatesListener coordinatesListener;
    List<Player> players;
    GameOrchestrator orchestrator;
    GameBoard gameBoard;
    TileBag tileBag;

    PersonalGoal_Deck personalGoalDeck;

    PointAssigner pointAssigner;
    List<CommonGoal> commonGoals;

    CommonGoal_Deck commonGoalDeck;

    //TODO WHat happens if game terminates?
    public Game(){
        topUpListener = new TopUpListener(this);
        coordinatesListener = new CoordinatesListener(this);
        players = new ArrayList<>();
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
        for(int i = 0; i< num_players; i++){
            players.add(new Player());
            players.get(i).setMyPersonalGoal(personalGoalDeck.getRandom());
        }
        pointAssigner = new PointAssigner();
        pointAssigner.initialize(num_players, 2);
        orchestrator = new GameOrchestrator(players, gameBoard, commonGoals, pointAssigner, tileBag, this);
        coordinatesListener = new CoordinatesListener(this);
        topUpListener = new TopUpListener(this);
    }


    public GameOrchestrator getOrchestrator() {
        return orchestrator;
    }

    public void setOrchestrator(GameOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }
}
