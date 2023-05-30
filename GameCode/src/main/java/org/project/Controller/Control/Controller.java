package org.project.Controller.Control;

import org.project.Controller.Messages.RefreshMsg;
import org.project.Controller.Server.Server;
import org.project.Controller.States.Exceptions.InvalidMoveException;
import org.project.Controller.States.GameState;
import org.project.Controller.States.StartTurnState;
import org.project.Controller.States.TopUpState;
import org.project.Controller.States.VerifyGrillableState;
import org.project.Controller.View.BoardView;
import org.project.Controller.View.GridView;
import org.project.Controller.View.TilesView;
import org.project.Controller.View.VirtualView;
import org.project.Model.Color;
import org.project.Model.Coordinates;
import org.project.Model.Player;
import org.project.Model.Tile;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private List<User> lobby;
    private Server server;
    private Game game;

    private GameOrchestrator orchestrator;
    private VirtualView view;

    private int numPlayers = 4;

    public Controller() throws RemoteException {

        this.server = new Server(this);
        this.lobby = new ArrayList<>();
        this.game = new Game(server);
    }
    public int getNumPlayers(){
        return this.numPlayers;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
    public List<User> getLobby(){
        return this.lobby;
    }
    public Server getServer(){
        return this.server;
    }
    public Game getGame(){
        return this.game;
    }
    public VirtualView getView(){
        return this.view;
    }

    public void refresh(){
        this.lobby = new ArrayList<>();
        this.game = new Game(server);
    }
    public void startGame(){
        this.view = new VirtualView(lobby, game);
        List<Player> playerOrder = playerOrder(lobby);
        this.game.gameInit(playerOrder);
        this.linkModel2View();
        this.game.pickCommonGoals();
        this.game.pickPersonalGoals();
        this.orchestrator = this.game.getOrchestrator();
        //Next try and catch we don't really expect any exceptions as the executeState() method will
        //be passing in between states that don't throw exceptions
        try {
            this.orchestrator.executeState();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        this.server.send(this.view);
        this.warnNextPlayer();

    }
    public void linkModel2View(){
        for(Player player : game.getPlayers()){
            player.addPropertyChangeListener(view.getTilesUpdateListener());
            player.addPropertyChangeListener(view.getGridUpdateListener());
            player.addPropertyChangeListener(view.getPGoalUpdateListener());
        }
        game.getGameBoard().addPropertyChangeListener(view.getBoardUpdateListener());
        game.addPropertyChangeListener(view.getCGoalUpdateListener());
        game.addPropertyChangeListener(view.getScoreBoardListener());
    }
    public List<Player> playerOrder(List<User> users){
        Random random = new Random();
        List<User> usersCopy = new ArrayList<>(users);
        List<Player> playerOrder = new ArrayList<>();
        for(int i = 0; i< users.size(); i++){
            int rand_index = random.nextInt(usersCopy.size());
            String nickname = usersCopy.remove(rand_index).getUsername();
            playerOrder.add(new Player(nickname));
        }
        return playerOrder;
    }
    /**
     * method for logging the FIRST player through the nickname.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     * @return true if the action was successful
     */
    public boolean create_game(String username, boolean connectionType, int numPlayers) throws InvalidLoginException {
        System.out.println("\nServer received request to create game with " + numPlayers + " players   (Server login method)");
        if(lobby.isEmpty()) {
            User user = new User(username, connectionType);
            user.addPropertyChangeListener(this.UserConnectionListener);
            lobby.add(user);
            game.setNumPlayers(numPlayers);
            this.numPlayers = numPlayers;

            System.out.println("\nServer has created new game  (Server login method)");
            return true;
        }
        //Means a game has already been created
        //Should probably do another check to see if numPlayers is acceptable
        else {
            System.out.println("\nAlready an existing game  (Server login method)");
            throw new InvalidLoginException("Already an existing game");
        }
    }

    /**
     * method to join the game: if it is the first time you connect,
     * you do a real login in which you add the client reference to the players list,
     * otherwise it means that the player wants to reconnect and in this case if the
     * username inserted corresponds to the username of a player who is in the list and
     * whose boolean connected is set to false, then the action is accepted
     * The method checks that the nickname is different for each logged in player.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @return true if the action was successful
     */
    public boolean join(String username, boolean connectionType) throws InvalidLoginException {
        System.out.println("\nReceived login request from " + username + " to join game  (Server login method)");
        //TODO checks once persistence has been implemented
        if(!lobby.isEmpty()){
            for (User user : lobby) {
                if (user.getUsername().equals(username) && user.isConnected()) {
                    System.out.println("\n" + username + " is already in use in the game  (Server login method)");
                    throw new InvalidLoginException(username + " is already in use in the game");

                } else if (user.getUsername().equals(username) && !user.isConnected()) {

                    System.out.println("\nplayer"+ username+ "has reconnected");
                    user.setConnected(true);
                    user.setConnectionType(connectionType);
                    return true;
                }
            }
            if(!(lobby.size() >= numPlayers)) {
                User user = new User(username, connectionType);
                user.addPropertyChangeListener(this.UserConnectionListener);
                lobby.add(user);
                System.out.println("\n" + username + " added to game  (Server login method)");
                return true;
            }else{
                throw new InvalidLoginException("Game is full, if you are rejoining make sure to be using the same username");
            }
        }
        throw new InvalidLoginException("No available game to join");
    }
    public void refreshRequest(String username){
        if(game.getGameStarted()) {
            server.refresh(username, view);
        }
    }

    /**
     * method that allows the client to take tiles from the board
     * @param username player's name
     * @param coordinates coordinates of the tiles to be taken
     * @return true if the action was successful
     */
    public boolean pick(String username, List<Coordinates> coordinates){
        if(game.getGameStarted()) {
            System.out.println("Server handling pick message (Server pick method)");
            //Check if it's actually the players turn (we will make sure client can't send moves if it isn't
            //his turn so this check is redundant)
            if(correctPlayer(username)) {
                //Have to check if the model is actually in VerifyGrillableState waiting for pick move
                if(orchestrator.getState() instanceof VerifyGrillableState) {
                    System.out.println("Pick message from correct player checking validity...  (Server pick method) ");
                    orchestrator.setPickedCoordinates(coordinates);
                    try {
                        orchestrator.executeState();
                    } catch(Exception e){
                        handleStateException(e, username);
                    }
                    //now if the coordinates were valid then the pieces have been picked and put in players pickedTiles
                    if(!orchestrator.getCurrentPlayer().pickedTilesIsEmpty()) {
                        //If successful the view has been updated so need to send it to all
                        server.send(view.getBoardView(), view.getTilesViews().get(username));
                        this.warnNextPlayer();
                        return true;
                    }
                } else {
                    System.out.println("Pick request ignored from current player as it is not the current state  (Server pick method)");
                    return false;
                }
            }
            //else it either wasn't players turn or the coordinates weren't valid and are still waiting for
            //valid input
            else{System.out.println("Wrong player for pick  (Server pick method)");
                return false;}
        }
        else System.out.println("Pick request ignored as game has not started yet  (Server pick method)");
        return false;

    }

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param username player's name
     * @param column Player's Choice Column
     * @param tileIndex
     * @return true if the action was successful
     */
    public boolean topUp(String username, int column, int tileIndex){
        if (game.getGameStarted()) {
            System.out.println("Server handling topUp message (Server topUp method)");
            int num_tiles = 0;
            if (correctPlayer(username)) {
                if (orchestrator.getState() instanceof TopUpState) {
                    System.out.println("Correct player for topUp  (Server topUp method)");
                    num_tiles = orchestrator.getCurrentPlayer().pickedTilesNum();
                    orchestrator.getCurrentPlayer().setSelectedColumn(column);
                    orchestrator.getCurrentPlayer().setTileIndex(tileIndex);
                    try {
                        orchestrator.executeState();
                    } catch (InvalidMoveException e) {
                        handleStateException(e, username);
                    }
                    if (orchestrator.getPlayer(username).pickedTilesNum() == num_tiles - 1) {
                        System.out.println("Valid topUp sending update (Server topUp method)");
                        server.send(view.getGridViews().get(username), view.getTilesViews().get(username));
                        this.warnNextPlayer();
                        return true;
                    }
                } else {
                    System.out.println("Ignoring topUp request from current player as it is not the current state  (Server topUp method)");
                    return false;
                }
            } else {
                System.out.println("Wrong player for topUp (Server topUp method)");
                return false;
            }
        }else{
        System.out.println("TopUp request ignored as game has not started yet  (Server topUp method)");}
        return false;
    }

    /**
     * method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param username player's name
     */
    public boolean quit(String username){
        System.out.println("Server handling quit message (Server quit method)");
        for (User user : lobby) {
            if (user.getUsername().equals(username)) {
                user.setConnected(false);
                String text = username + " quitted";
                System.out.println("Player " + username + " has quit");
                server.sendInfo(text);
                warnNextPlayer();
                }
        }
        return true;
    }
    public boolean correctPlayer(String username){
        return orchestrator.getCurrentPlayer().getNickname().equals(username);
    }
    public User getUser(String username){
        for(User user : lobby){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public void warnNextPlayer(){
        String playerName = this.orchestrator.getCurrentPlayer().getNickname();
        GameState state = this.orchestrator.getState();
        String Info = " ";
        if(state instanceof TopUpState){
            Info ="Waiting for player " + playerName + " to top up";
            server.turn_Refresh(playerName, false);
        }
        else if(state instanceof VerifyGrillableState){
            Info ="Waiting for player " + playerName + " pick tiles";
            server.turn_Refresh(playerName, true);
        }
        if(!Info.equals(" ")){
            this.server.sendInfo(Info);
        }
    }
    public void handleStateException(Exception e, String username){
        String info = e.getMessage();
        server.sendInfo(info, getUser(username));
    }
    PropertyChangeListener UserConnectionListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("ConnectionUpdate".equals(evt.getPropertyName())) {
                User user = (User) evt.getNewValue();
                String username = user.getUsername();

                Player player = game.getPlayerFromUsername(username);
                if (player != null) {
                    player.setConnected(user.isConnected());
                }
                if (user.isConnected()) {
                    server.sendInfo("Player " + username + " has joined the game");
                } else {
                    server.sendInfo("Player " + username + " has disconnected");
                    server.removeUser(username, user.getConnectionType());
                    if(!game.getGameStarted()){
                        lobby.remove(user);
                    }
                    //Turn checks
                    if (game.getGameStarted() && correctPlayer(username)) {
                        GameOrchestrator orchestrator = game.getOrchestrator();
                        orchestrator.changeState(new StartTurnState(orchestrator));
                        while (!orchestrator.getCurrentPlayer().isConnected()) {
                            orchestrator.nextPlayer();
                        }
                        try {
                            orchestrator.executeState();
                        } catch (InvalidMoveException e) {
                            throw new RuntimeException(e);
                    }
                }
            }
            }
        }

    };
    public static void main(String[] args){
        try {
            Controller controller = new Controller();
            while(true) {
                controller.getServer().serverInit();
                //If game has ended I need to refresh to wait for future games
                //The refresh just cleans up lobby and game but the server has to be refreshed
                //at the end of the serverInit method
                controller.refresh();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
