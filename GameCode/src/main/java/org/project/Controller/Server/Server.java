package org.project.Controller.Server;

import org.project.ClientPack.RMIClient;
import org.project.Controller.Control.*;
import org.project.Controller.Messages.*;
import org.project.Controller.View.*;
import org.project.Model.Coordinates;
import org.project.ClientPack.RMIClientInterface;
import org.project.Model.Player;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * main server
 */

public class Server {

    /**
     * reference to Game
     */
    private Game game;
    /**
     * reference to Controller
     */
    private Controller controller;
    /**
     * object for synchronized
     */
    private final Object lock = new Object();
    /**
     * number of connected player
     */
    private int connectedPlayers;


    /**
     * RMI server
     */
    private static RMIServerApp rmiServer;
    /**
     * SOCKET server
     */
    private static SocketServer socketServer;

    public int getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(int connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }

    public Object getLock(){
        return lock;
    }

    /**
     * constructor
     */

    public Server(Controller controller) throws RemoteException {

        this.controller = controller;
        game = controller.getGame();
        socketServer= new SocketServer(this, Settings.SOCKET_PORT);
        rmiServer= new RMIServerApp(this);
        connectedPlayers=0;
    }

    /**
     * Initializes a new game using the provided controller.
     * Sets the game object obtained from the controller as the current game.
     * Resets the number of connected players to 0.
     *
     * @param controller the controller used to start the new game
     */
    public void newGame(Controller controller){
        game = controller.getGame();
        connectedPlayers = 0;
    }

    public String getName(List<User> Lobby){
        List<String> names = new ArrayList<>();
        for(User p : Lobby){
            names.add(p.getUsername());
        }
        List<String> sorted_list = names.stream().sorted().toList();
        String file_name = "";
        for(String s : sorted_list){
            file_name += s;
        }
        return file_name;
    }

    /**
     * Initializes the server for the game.
     * Starts an RMI server on the port specified in Settings.RMI_PORT.
     * Starts a thread for the socket server.
     * Waits until the number of players in the controller's lobby is equal to the desired number of players.
     * Once the number of players reaches the desired count, starts the game by calling the startGame() method on the controller.
     */
    public void serverInit() {
        int rmiPort = Settings.RMI_PORT;
        int socketPort = Settings.SOCKET_PORT;

        try {
            rmiServer.startRMIServer(rmiPort);
            new Thread(socketServer).start();
            while (true) {

                synchronized (lock) {
                    while (this.controller.getLobby().size() != this.controller.getNumPlayers()) {
                        //Make the current thread wait until notified
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Thread.sleep(1000);
                    }
                }
                String name = getName(this.controller.getLobby())+".json";
                if(Files.exists(Paths.get(name)))
                    this.controller.recoverGame(name);
                else
                    this.controller.startGame();
                synchronized (lock) {
                    while (controller.getGame().getGameStarted()) {
                        try {
                            System.out.println("Checking connections");
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Server flushing");
                Path paths = Paths.get(name);
                File file = new File(paths.toUri());
                if(file.exists()) {
                    file.delete();
                };
                getRmiServer().flushRMIClients();
                getSocketServer().flushSocketClients();
                controller.refresh();
                newGame(controller);
            }

            } catch(Exception e){
                e.printStackTrace();
            }

    }
    public SocketServer getSocketServer() {
        return socketServer;
    }
    public RMIServerApp getRmiServer() {
        return rmiServer;
    }
    public Controller getController(){
        return controller;
    }
 /*   public void serverInit(String[] args) throws RemoteException {

        int rmiPort=1234;
        int socketPort = Settings.SOCKET_PORT;

        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[1]));
        }

        try{
            rmiServer.startRMIServer(rmiPort);
            new Thread(socketServer).start();

            while (server.game.getUsersSize() != server.game.getNumPlayers()) {
                Thread.sleep(1000);
            }
            server.game.gameInit( server.game.getNumPlayers());
            server.orchestrator = (server.game.getOrchestrator()); //FA CAGARE AVERE SOLO UN RIFERIMENTO
            server.orchestrator.executeState();
            server.send(server.game.getView());
            int a = 1;
        }catch(Exception e){
            e.printStackTrace();
        }

    }
*/
    //METODI DEL SERVER CHIAMATI DA RMI/SOCKET SERVER

    /**
     * method that allows the client to take tiles from the board
     * @param username player's name
     * @param coordinates coordinates of the tiles to be taken
     */
    public boolean pick(String username, List<Coordinates> coordinates){
        return controller.pick(username, coordinates);
    }
    public boolean chat(String username, String text){
        return controller.chat(username, text);
    }

    public boolean chat(String username, String text, String receiver){return controller.chat(username, text, receiver);}

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param username player's name
     * @param column Player's Choice Column
     * @param tileIndex
     */
    public boolean topUp(String username, int column, int tileIndex) {
        return controller.topUp(username, column, tileIndex);
    }

    /**
     * Sets a player as disconnected based on the provided username.
     * If the username is not null, it sets the connected status of the corresponding user to false
     * and decrements the count of connected players.
     * This action triggers a listener or event to handle the disconnection.
     *
     * @param username the username of the player to be set as disconnected
     */
    void set_player_disconnected(String username){
        if(username != null) {
            controller.getUser(username).setConnected(false);
            connectedPlayers--;
        }
    }
    public void stopKeepAlive(String username, boolean connectionType){
        if(connectionType){
            socketServer.getSocketClients().get(username).stopKeepAlive();
        }
    }
    /**
     * method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     */
    public synchronized boolean join(String username, boolean connectionType) throws InvalidLoginException {
        return controller.join(username, connectionType);
    }
    /**
     * method for logging the FIRST player through the nickname.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     */
    public synchronized boolean create_game(String username, boolean connectionType, int numPlayers) throws InvalidLoginException {
        return controller.create_game(username, connectionType, numPlayers);
    }
    /**
     * method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param username player's name
     */
    public boolean quit(String username){
        return controller.quit(username);
    }

    //METODI PER LA VIEW

    //Next are the methods for sending information to the clients, two smaller methods sendRMI and sendSocket
    //and a more general method used for broadcasting messages to all the clients (which iterates on the two
    //client hash maps and then calls the previously mentioned methods), when the message has to be sent to a
    //single client we directly use the smaller methods


    //Sent at the beginning of the game and updates the client view (Could be the one sent if we decide
    //to implement a refresh view method called from the client, in this case though it should be sent just
    //to the client who has requested it)
    /**
     * Sends the game view to the specified VirtualView.
     * Retrieves the game state from the provided VirtualView and organizes it into various data structures.
     * These include the game board, the point stack, the grid views, the tile views, the personal goal views,
     * and the common goal view.
     *
     * @param view the VirtualView to which the game view will be sent
     */
    public void send(VirtualView view){

        String[][] board = view.getBoardView().getBoard();
        List<Integer> pointStack = view.getPointStackView().getPointList();
        HashMap<String, String[][]> gridsview = new HashMap<>();
        HashMap<String, String[]> tilesview = new HashMap<>();
        HashMap<String, Integer> personalGoalview=new HashMap<>();
        List<Integer> commonGoalview= view.getCommonGoalsView();

        for(Map.Entry<String, GridView> mapElement : view.getGridViews().entrySet()){
            String username = mapElement.getKey();
            GridView gridView = mapElement.getValue();
            gridsview.put(username, gridView.getGridView());
        }
        for(Map.Entry<String, TilesView> mapElement : view.getTilesViews().entrySet()){
            String username = mapElement.getKey();
            TilesView tileView = mapElement.getValue();
            tilesview.put(username, tileView.getPlayerTiles());
        }
        for(Map.Entry<String, Integer> mapElement : view.getPersonalGoalViews().entrySet()){
            String username = mapElement.getKey();
            Integer pgoalView = mapElement.getValue();
            personalGoalview.put(username,pgoalView);
        }

        //Sending to socket clients
        RefreshMsg message = new RefreshMsg(board, pointStack, gridsview, tilesview,personalGoalview,commonGoalview);
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to RMI clients

        rmiServer.getClientsRMI().forEach((username,client)-> {
            try {
                client.notifyInitialGameView(board,pointStack,gridsview,tilesview,personalGoalview,commonGoalview);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Refreshes the game view for a specific player in the provided VirtualView.
     * Retrieves the game state from the VirtualView and organizes it into various data structures,
     * including the game board, the point stack, the grid views, the tile views, the personal goal views,
     * and the common goal view.
     * Constructs a RefreshMsg object with the organized game view data.
     * Send the new view to rmi and socket clients.
     *
     * @param playername the username of the player to refresh the game view for
     * @param view the VirtualView containing the game state
     */
    public void refresh(String playername, VirtualView view){

        String[][] board = view.getBoardView().getBoard();
        List<Integer> pointStack = view.getPointStackView().getPointList();
        HashMap<String, String[][]> gridsview = new HashMap<>();
        HashMap<String, String[]> tilesview = new HashMap<>();
        HashMap<String, Integer> personalGoalview=new HashMap<>();
        List<Integer> commonGoalview= view.getCommonGoalsView();

        for(Map.Entry<String, GridView> mapElement : view.getGridViews().entrySet()){
            String username = mapElement.getKey();
            GridView gridView = mapElement.getValue();
            gridsview.put(username, gridView.getGridView());
        }
        for(Map.Entry<String, TilesView> mapElement : view.getTilesViews().entrySet()){
            String username = mapElement.getKey();
            TilesView tileView = mapElement.getValue();
            tilesview.put(username, tileView.getPlayerTiles());
        }
        for(Map.Entry<String, Integer> mapElement : view.getPersonalGoalViews().entrySet()){
            String username = mapElement.getKey();
            Integer pgoalView = mapElement.getValue();
            personalGoalview.put(username,pgoalView);
        }
        RefreshMsg message = new RefreshMsg(board, pointStack, gridsview, tilesview,personalGoalview,commonGoalview);
        boolean connectionType = controller.getUser(playername).getConnectionType();
        //If client is socket
        if(connectionType){
            socketServer.getSocketClients().get(playername).send(message);
        }
        //If client is RMI
        else {
            try {
                rmiServer.getClientsRMI().get(playername).notifyInitialGameView(board, pointStack, gridsview, tilesview, personalGoalview, commonGoalview);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }
    //Sends necessary stuff for update after a successful pick move
    /**
     * Sends an update message containing the game board and player's tiles to all connected clients after a successful pick move.
     * Constructs an UpdatePickMsg object with the player's name, tiles, and board.
     * Sends the message to all connected socket clients and rmiClients.
     *
     * @param boardView  the BoardView containing the game board
     * @param tilesView  the TilesView containing the player's tiles
     */
    public void send(BoardView boardView, TilesView tilesView){
        String[][] board = boardView.getBoard();
        String[] tiles = tilesView.getPlayerTiles();
        String playername = tilesView.getUsername();
        if(board==null){
            System.out.println("BOARD");
        } else if (tiles==null) {
            System.out.println("TILES");
        }
        //Sending to socket clients
        UpdatePickMsg message = new UpdatePickMsg(playername, tiles, board);
        System.out.println("\nServer has created UpdatePickMsg to send (Server send method 2nd overload)");
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to rmi clients
        rmiServer.getClientsRMI().forEach((username, client)-> {
            try {
                client.notifyPick(board,tiles,playername);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //Sends necessary stuff after a successful topUp move
    /**
     * Sends an update message containing the grid view and player's tiles to all connected clients after a successful top up move.
     * Constructs an UpdateTopUPMsg object with the player's name, tiles, and grid.
     * Sends the message to all connected socket clients and rmiClients.
     *
     * @param gridView   the GridView containing the grid view
     * @param tilesView  the TilesView containing the player's tiles
     */
    public void send(GridView gridView, TilesView tilesView){

        String[][] grid = gridView.getGridView();
        String[] tiles = tilesView.getPlayerTiles();
        String playername = tilesView.getUsername();
        if(grid==null){
            System.out.println("GRID");
        } else if (tiles==null) {
            System.out.println("TILES");
        }
        //Sending to socket clients
        UpdateTopUPMsg message = new UpdateTopUPMsg(playername, tiles, grid);
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to RMI clients
        rmiServer.getClientsRMI().forEach((username,client)-> {
            try {
                client.notifyTopUp(grid,tiles,playername);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //Sends necessary stuff to display final scoreBoard after game has ended
    /**
     * Sends an update message containing the scoreboard to all connected clients to display final scoreBoard after game has ended.
     * Constructs a ScoreBoardMsg object with the scoreboard data.
     * Sends the message to all connected socket clients and rmiClients.
     *
     * @param scoreBoard the ScoreBoardView containing the scoreboard data
     */
    public void send(ScoreBoardView scoreBoard){

        HashMap<String, Integer> score = scoreBoard.getScoreBoard();
        //Sending to socket clients
        ScoreBoardMsg message = new ScoreBoardMsg(score);
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to RMI clients
        rmiServer.getClientsRMI().forEach((username, client)-> {
            try {
                client.notifyScoreBoard(score);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //Rewriting server methods

    //Method to send text information to a single user
    /**
     * Sends an informational message to a specific user.
     * Constructs a PopUpMsg object with the provided information.
     * Sets the identifier for the message.
     * Retrieves the username and connection type from the provided User object.
     * Sends the message to the corresponding socket client or rmi client.
     *
     * @param info the information to be sent
     * @param user reference to the User
     * @param identifier the identifier for the message
     */
    public void sendInfo(String info,User user, int identifier){
        PopUpMsg message = new PopUpMsg(info);
        message.setIdentifier(identifier);
        String username = user.getUsername();
        if(user.getConnectionType()){
            socketServer.getSocketClients().get(username).send(message);
        }
        else{
            RMIClientInterface client = rmiServer.getClientsRMI().get(username);
            try{
                client.notifyPopUpView(info, identifier);
            }catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //Method to send text information to all players
    /**
     * Sends an informational message to all connected clients.
     * Constructs a PopUpMsg object with the provided information.
     * Sets the identifier for the message.
     * Sends the message to all connected socket clients and rmi clients.
     *
     * @param info the information to be sent
     * @param identifier the identifier for the message
     */
    public void sendInfo(String info, int identifier){

        PopUpMsg message = new PopUpMsg(info);
        message.setIdentifier(identifier);
        socketServer.getSocketClients().forEach((username,client) -> client.send(message));
        rmiServer.getClientsRMI().forEach((username,client)-> {
            try {
                client.notifyPopUpView(info, identifier);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    /**
     * Sends a chat message to all connected clients.
     * Constructs a ChatMessage object with the provided username and text.
     * Sends the message to all connected socket clients and rmi clients.
     *
     * @param username the username of the sender
     * @param text the text of the chat message
     */
    public void sendChat(String username, String text){

        ChatMessage message = new ChatMessage(username, text);
        socketServer.getSocketClients().forEach((playername,client) -> client.send(message));
        rmiServer.getClientsRMI().forEach((playername,client)-> {
            try {
                client.notifyChat(username, text);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Sends a chat message to a specific connected clients.
     * Constructs a ChatMessage object with the provided username and text.
     *
     * @param username the username of the sender
     * @param text the text of the chat message
     * @param receiver the username of the receiver
     */
    public void sendChat(String username, String text, String receiver) {
        if (controller.getUser(receiver).getConnectionType()) {
            ChatMessage message = new ChatMessage(username, text, receiver);
            socketServer.getSocketClients().get(receiver).send(message);
        } else {
            RMIClientInterface client = rmiServer.getClientsRMI().get(receiver);
            try {
                client.notifyChat(username, text, receiver);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Removes a user from the server based on the username and connection type.
     * If the connection type is socket, disconnects the corresponding socket client and removes it from the socket server's client list.
     * If the connection type is RMI, removes the corresponding RMI client from the RMI server's client list.
     *
     * @param username the username of the user to be removed
     * @param connectionType the connection type of the user (true for socket, false for RMI)
     */
    public void removeUser(String username, boolean connectionType){
        if(connectionType){
            socketServer.getSocketClients().get(username).disconnect();
            socketServer.getSocketClients().remove(username);
        }
        else {
            rmiServer.getClientsRMI().remove(username);
        }
    }
    //todo javadoc
    /**
     * Sends a pre-turn message to a specific player indicating their turn refresh.
     * Constructs a PreTurnMsg object with the playername and move information.
     * Sends the message to the corresponding socket client or rmi client.
     *
     * @param playername the username of the player
     * @param move indicates whether the player can make a move in the current turn
     */
    public void turn_Refresh(String playername, boolean move){

        boolean connectionType = controller.getUser(playername).getConnectionType();
        PreTurnMsg message = new PreTurnMsg(playername, move);
        //If client is socket
        if(connectionType){
            socketServer.getSocketClients().get(playername).send(message);
        }
        //If client is RMI
        else {
            try {
                rmiServer.getClientsRMI().get(playername).notifyTurn(playername, move);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
