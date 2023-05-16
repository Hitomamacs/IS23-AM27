package org.project.Controller.Control;

import org.project.Controller.Server.Server;
import org.project.Controller.States.Exceptions.InvalidMoveException;
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
    public void startGame(){
        this.view = new VirtualView(lobby, game);
        List<User> playerOrder = new ArrayList<>(lobby);
        Random random = new Random();
        for(int i = 0; i< numPlayers; i++){
            int rand_index = random.nextInt(playerOrder.size());
            String nickname = playerOrder.remove(rand_index).getUsername();
            this.game.getPlayers().add(new Player(nickname));
        }
        this.game.gameInit(numPlayers);
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

    }
    public void linkModel2View(){
        for(Player player : game.getPlayers()){
            player.addPropertyChangeListener(view.getTilesUpdateListener());
            player.addPropertyChangeListener(view.getGridUpdateListener());
            player.addPropertyChangeListener(view.getPGoalUpdateListener());
        }
        game.getGameBoard().addPropertyChangeListener(view.getBoardUpdateListener());
        game.addPropertyChangeListener(view.getCGoalUpdateListener());
    }
    public boolean create_game(String username, boolean connectionType, int numPlayers){
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
        System.out.println("\nAlready an existing game  (Server login method)");
        return false;
    }
    public boolean join(String username, boolean connectionType){
        System.out.println("\nReceived login request from " + username + " to join game  (Server login method)");
        //TODO checks once persistence has been implemented
        if(!lobby.isEmpty()){
            for (User user : lobby) {
                if (user.getUsername().equals(username) && user.isConnected()) {
                    System.out.println("\n" + username + " is already in use in the game  (Server login method)");
                    return false;
                } else if (user.getUsername().equals(username) && !user.isConnected()) {
                    System.out.println("\nplayer"+ username+ "has reconnected");
                    user.setConnected(true);
                    return true;
                }
            }

            User user = new User(username, connectionType);
            user.addPropertyChangeListener(this.UserConnectionListener);
            lobby.add(user);
            System.out.println("\n" + username + " added to game  (Server login method)");

            return true;
        }
        System.out.println("\n" + "A game needs to be created first  (Server login method)");
        return false;
    }
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
                    if (orchestrator.getCurrentPlayer().pickedTilesNum() == num_tiles - 1) {
                        System.out.println("Valid topUp sending update (Server topUp method)");
                        server.send(view.getGridViews().get(username), view.getTilesViews().get(username));
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
        }
        System.out.println("TopUp request ignored as game has not started yet  (Server topUp method)");
        return false;
    }
    public boolean quit(String username){
        if(game.getGameStarted()){
            System.out.println("Server handling quit message (Server quit method)");
            for (User user : lobby) {
                if (user.getUsername().equals(username) && user.isConnected()) {
                    user.setConnected(false);
                }
            }
            String text=username+"quitted";
            server.sendInfo(text);
        }
        return false;
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
    public void handleStateException(Exception e, String username){
        String info = e.getMessage();
        server.sendInfo(info, getUser(username));
    }
    PropertyChangeListener UserConnectionListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("ConnectionUpdate".equals(evt.getPropertyName())) {
                User user = (User)evt.getNewValue();
                String username = user.getUsername();

                Player player = game.getPlayerFromUsername(username);
                if(player != null){
                    player.setConnected(user.isConnected());
                }
            }
        }

    };
    public static void main(String[] args){
        try {
            Controller controller = new Controller();
            controller.getServer().serverInit();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
