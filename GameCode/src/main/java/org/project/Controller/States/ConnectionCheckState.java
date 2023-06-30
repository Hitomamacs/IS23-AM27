package org.project.Controller.States;

import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Model.Player;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ConnectionCheckState controls that the player is connected through its isConnected boolean.
 * If the player is disconnected just skips his turn.
 * If the player is connected there are two possibilities:
 * 1. the player has some pickedTiles because he had previously disconnected during topUp state and now has to place the tiles
 * 2. the player simply has to pick some tiles from the board and in this case we have to check if the board needs refilling
 */

public class ConnectionCheckState implements GameState {

    private final int stateID = 1;

    private Timer timer = new Timer();

    private boolean endTimer = false;

    private Timer insideTimer = new Timer();
    private transient GameOrchestrator gameOrchestrator;

    /**
     * Constructor
     * @param orchestrator reference to GameOrchestrator
     */
    public ConnectionCheckState(GameOrchestrator orchestrator){
        this.gameOrchestrator = orchestrator;
    }

    public int getStateID(){
        return stateID;
    }

    /**
     * Changes the game state based on certain conditions.
     * If the current player is connected:
     *   If the player has no picked tiles, it changes the state to VerifyGrillableState
     *   and sets the current state ID to 10.
     *   If the player has picked tiles, it changes the state to TopUpState, passing the previous selected column,
     *   and sets the current state ID to 7.
     * If the player is disconnected, it advances to the next player and changes the state to StartTurnState,
     * setting the current state ID to 6.
     * After changing the state, the new state is executed by calling the executeState() method in the game orchestrator.
     */
    @Override
    public void changeState() {
        int numPlayers = countOnlinePlayers();
        if (numPlayers == 1) {
            //set a timer that checks if the player reconnects
            //if he doesn't reconnect in time, the game ends
            //if he reconnects, the timer is stopped
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int numPlayers = countOnlinePlayers();
                    insideTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (countOnlinePlayers() > 1) {
                                timer.cancel();
                                timer.purge();
                                insideTimer.cancel();
                                insideTimer.purge();
                                gameOrchestrator.changeState(new ConnectionCheckState(gameOrchestrator));
                                gameOrchestrator.setCurr_sate_id(1);
                                try {
                                    gameOrchestrator.executeState();
                                } catch (InvalidMoveException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }, 0, 1000);
                    if (endTimer && countOnlinePlayers() == 1) {
                        gameOrchestrator.changeState(new EndGameState(gameOrchestrator));
                        gameOrchestrator.setCurr_sate_id(2);
                        try {
                            gameOrchestrator.executeState();
                        } catch (InvalidMoveException e) {
                            throw new RuntimeException(e);
                        }
                        timer.cancel();
                        timer.purge();
                    }
                    endTimer = true;
                }

            }, 0, 30000);


        }
        numPlayers = countOnlinePlayers();
        if (numPlayers > 1) {
            if (gameOrchestrator.getCurrentPlayer().isConnected()) {
                numPlayers = countOnlinePlayers();

                if (gameOrchestrator.getCurrentPlayer().pickedTilesIsEmpty()) {
                    System.out.println("Server waiting for " + gameOrchestrator.getCurrentPlayer().getNickname() + " to pick tiles");
                    gameOrchestrator.changeState(new VerifyGrillableState(gameOrchestrator));
                    gameOrchestrator.setCurr_sate_id(10);
                } else {
                    int previousSelectedColumn = gameOrchestrator.getCurrentPlayer().getSelectedColumn();
                    gameOrchestrator.changeState(new TopUpState(gameOrchestrator, previousSelectedColumn));
                    gameOrchestrator.setCurr_sate_id(7);
                /* Without this check if player had disconnected in between TopUps he could place remaining tiles in
                  a different column, note that this means that at the end of the TopUp phase of a player the
                  Selected column in player has to be set to the default value -1. This is because if the player
                  disconnected before his first TopUp the connectionCheckState would force him to use the same
                  column of the previous TopUp phase. In stead this way in that scenario the TopUps selectedColumn
                  would be -1 and the player would be allowed to select a new column */
                }
            } else {
                gameOrchestrator.nextPlayer();
                gameOrchestrator.changeState(new StartTurnState(gameOrchestrator));
                gameOrchestrator.setCurr_sate_id(6);
                try {
                    gameOrchestrator.executeState();
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public int countOnlinePlayers(){
        List<Player> players = gameOrchestrator.getPlayers();
        int onlinePlayers = 0;
        for(Player player : players){
            if(player.isConnected()){
                onlinePlayers++;
            }
        }
        return onlinePlayers;

    }

    @Override
    public void execute() {
        changeState();
    }
}
