package org.project.Controller.Server;

import org.project.Controller.Control.*;
import org.project.Controller.Messages.*;
import org.project.Controller.View.*;
import org.project.Model.Coordinates;
import org.project.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private Game game;
    private Controller controller;
    private final Object lock = new Object();
    private int connectedPlayers;

    /**
     * variabile per tener conto di quante persone ho aggiunto
     */


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
    //TODO:devo passare game e game orchestrator
    public Server(Controller controller) throws RemoteException {

        this.controller = controller;
        game = controller.getGame();
        socketServer= new SocketServer(this, Settings.SOCKET_PORT);
        rmiServer= new RMIServerApp(this);
        connectedPlayers=0;
    }
    public void newGame(Controller controller){
        game = controller.getGame();
        connectedPlayers = 0;
    }

    /**
     * MAIN
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
                        //TODO possibly make it wait on notify from socket server and rmi server
                    }
                }
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

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param username player's name
     * @param column Player's Choice Column
     * @param tileIndex
     */
    public boolean topUp(String username, int column, int tileIndex) {
        return controller.topUp(username, column, tileIndex);
    }

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


    /**
     * send a chat message to all players
     * @param username message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
    */
    public void sendMessage (String username,String message){
        System.out.println("server received : "+ message);

        //per i client RMI:
        for(RMIClientInterface rmiCl: rmiServer.getClientsRMI().values()){  //MI SA FOR SBAGLIATO
            try{rmiCl.printMsgChat(username,message);}
            catch(Exception e){
                e.printStackTrace();
            }
        }

        //per i client Socket:
    }

   //METODI GET E SET
   


    //METODI PER LA VIEW

    //Next are the methods for sending information to the clients, two smaller methods sendRMI and sendSocket
    //and a more general method used for broadcasting messages to all the clients (which iterates on the two
    //client hash maps and then calls the previously mentioned methods), when the message has to be sent to a
    //single client we directly use the smaller methods

    //Sent at the beginning of the game and updates the client view (Could be the one sent if we decide
    //to implement a refresh view method called from the client, in this case though it should be sent just
    //to the client who has requested it)
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
    public void removeUser(String username, boolean connectionType){
        if(connectionType){
            socketServer.getSocketClients().get(username).disconnect();
            socketServer.getSocketClients().remove(username);
        }
        else {
            rmiServer.getClientsRMI().remove(username);
        }
    }
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
