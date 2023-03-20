package org.example;

import java.util.List;

public class Game {
    List<Player> players;
    GameOrchestrator orchestrator;
    GameBoard gameBoard;
    TileBag tileBag;
    //Todo Add Deck;

    PointAssigner pointAssigner;
    List<CommonGoal> commonGoals;
}
