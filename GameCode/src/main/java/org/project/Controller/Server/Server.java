package org.project.Controller.Server;

import org.project.Controller.Control.Game;
import org.project.Controller.Control.GameOrchestrator;
import org.project.Controller.Control.User;
import org.project.Controller.Messages.*;
import org.project.Controller.States.PickState;
import org.project.Controller.States.TopUpState;
import org.project.Controller.States.VerifyGrillableState;
import org.project.Controller.View.*;
import org.project.Model.Color;
import org.project.Model.Coordinates;
import org.project.Model.Player;
import org.project.RMIClientApp;
import org.project.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private Game game;
    private GameOrchestrator orchestrator;
    private int connectedPlayers;

    /**
     * variabile per tener conto di quante persone ho aggiunto
     */
    int count_players=0;

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

    /**
     * constructor
     */
    //TODO:devo passare game e game orchestrator
    public Server() throws RemoteException {
        socketServer= new SocketServer(this, Settings.SOCKET_PORT);
        rmiServer= new RMIServerApp(this);
        connectedPlayers=0;
    }

    public Game getGame() {
        return game;
    }

    /**
     * MAIN
     */
    public static void main(String[] args) throws RemoteException {

        int rmiPort=1234;
        int socketPort = Settings.SOCKET_PORT;

        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[1]));
        }

        try{
            Server server = new Server();
            server.game = new Game(server);
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

    //METODI DEL SERVER CHIAMATI DA RMI/SOCKET SERVER

    /**
     * method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     */

    /**
     * method for logging the FIRST player through the nickname.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     */


    /**
     * method that allows the client to take tiles from the board
     * @param username player's name
     * @param coordinates coordinates of the tiles to be taken
     */
    public boolean pick(String username, List<Coordinates> coordinates){
        if(game.getGameStarted()) {
            System.out.println("Server handling pick message (Server pick method)");
            getGame().getOrchestrator().pick(username, coordinates);
            if(!orchestrator.getCurrentPlayer().pickedTilesIsEmpty()){
                return true;
            }
            /*
            //Check if it's actually the players turn (we will make sure client can't send moves if it isn't
            //his turn so this check is redundant)
            if (orchestrator.getCurrentPlayer().getNickname().equals(username)) {
                //Have to check if the model is actually in VerifyGrillableState waiting for pick move
                if (orchestrator.getState() instanceof VerifyGrillableState) {
                    System.out.println("Pick message from correct player checking validity...  (Server pick method) ");
                    orchestrator.setPickedCoordinates(coordinates);
                    orchestrator.executeState();
                    int a = 3;
                    //now if the coordinates were valid then the pieces have been picked and put in players pickedTiles
                    if (!orchestrator.getCurrentPlayer().pickedTilesIsEmpty()) {
                        //If successful the view has been updated so need to send it to all
                        BoardView boardView = game.getView().getBoardView();
                        //TODO double check the next two lines and test properly
                        List<TilesView> tilesViews = game.getView().getTilesViews().stream().filter(t -> t.getUsername().equals(username)).toList();
                        send(boardView, tilesViews.get(0));
                        return true;
                    }//Otherwise the move wasn't successful and the error is written in the popUpView text field;
                    else sendError(username);
                } else {
                    System.out.println("Pick request ignored from current player as it is not the current state  (Server pick method)");
                    return false;
                  }
            }
            //else it either wasn't players turn or the coordinates weren't valid and are still waiting for
            //valid input
            else{System.out.println("Wrong player for pick  (Server pick method)");
            return false;}
        */
        }
        else System.out.println("Pick request ignored as game has not started yet  (Server pick method)");
        return false;
    }

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param username player's name
     * @param column Player's Choice Column
     * @param tileIndex
     */
    public boolean topUp(String username, int column, int tileIndex) {
        if (game.getGameStarted()) {
            /*
            System.out.println("Server handling topUp message (Server topUp method)");
            int num_tiles = 0;
            if (orchestrator.getCurrentPlayer().getNickname().equals(username)) {
                if (orchestrator.getState() instanceof TopUpState) {
                    System.out.println("Correct player for topUp  (Server topUp method)");
                    num_tiles = orchestrator.getCurrentPlayer().pickedTilesNum();
                    orchestrator.getCurrentPlayer().setSelectedColumn(column);
                    orchestrator.getCurrentPlayer().setTileIndex(tileIndex);
                    orchestrator.executeState();
                    if (orchestrator.getCurrentPlayer().pickedTilesNum() == num_tiles - 1) {
                        List<GridView> gridViews = game.getView().getGridViews().stream().filter(g -> g.getUsername().equals(username)).toList();
                        GridView gridView = gridViews.get(0);
                        List<TilesView> tilesViews = game.getView().getTilesViews().stream().filter(t -> t.getUsername().equals(username)).toList();
                        TilesView tilesView = tilesViews.get(0);
                        System.out.println("Valid topUp sending update (Server topUp method)");
                        send(gridView, tilesView);
                        return true;
                    } else sendError(username);
                } else {
                    System.out.println("Ignoring topUp request from current player as it is not the current state  (Server topUp method)");
                    return false;
                }
            } else {
                System.out.println("Wrong player for topUp (Server topUp method)");
                return false;
            }
        */
        }
        System.out.println("TopUp request ignored as game has not started yet  (Server topUp method)");
        return false;

    }

    void set_player_disconnected(String username){
        List<Player> players = game.getPlayers();
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getNickname().equals(username)){
                players.get(i).setIsConnected(false);
                connectedPlayers--;
                count_players--;
                break;
            }
        }
    }

    /**
     * method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     */
    public boolean login(String username, boolean connectionType){
        System.out.println("\nReceived login request from " + username + " to join game  (Server login method)");
        //TODO checks once persistence has been implemented
        if(!game.getUsers().isEmpty()){
            for(int i = 0; i < game.getUsers().size(); i++){
                if(game.getUsers().get(i).getUsername().equals(username)){
                    System.out.println("\n" + username + " is already in use in the game  (Server login method)");
                    return false;}//Probably better if I throw exceptions instead to distinguish
            }                    //the reasons the method was unsuccessful
            game.getUsers().add(new User(username, connectionType));
            System.out.println("\n" + username + " added to game  (Server login method)");

            return true;
        }
        System.out.println("\n" + "A game needs to be created first  (Server login method)");
        return false;
    }
    /**
     * method for logging the FIRST player through the nickname.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     */
    public boolean login(String username, boolean connectionType, int numPlayers){
        System.out.println("\nServer received request to create game with " + numPlayers + " players   (Server login method)");
        if(game.getUsers().isEmpty()) {
            game.getUsers().add(new User(username, connectionType));
            game.setNumPlayers(numPlayers);
            System.out.println("\nServer has created new game  (Server login method)");
            return true;
        }
        //Means a game has already been created
        //Should probably do another check to see if numPlayers is acceptable
        System.out.println("\nAlready an existing game  (Server login method)");
        return false;
    }
    /**
     * remote method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param username player's name
     */
    public boolean quit(String username){
        return false;
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
    public int getCount_players(){
        return count_players;
    }
    public void setCount_players(int count){
        this.count_players = count;
    }

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
        //Sending to socket clients
        RefreshMsg message = new RefreshMsg(board, pointStack, gridsview, tilesview);
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to RMI clients
        rmiServer.getClientsRMI().forEach((username,client)-> {
            try {
                client.notifyInitialGameView(board,pointStack,gridsview,tilesview);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }
    //Sends necessary stuff for update after a successful pick move
    public void send(BoardView boardView, TilesView tilesView){
        String[][] board = boardView.getBoard();
        String[] tiles = tilesView.getPlayerTiles();
        String playername = tilesView.getUsername();
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
    public void send(BoardView boardView){
        String[][] board = boardView.getBoard();
        //Sending to socket clients
        RefillUpdateMsg message = new RefillUpdateMsg(boardView.getBoard());
        socketServer.getSocketClients().forEach((username, client) -> client.send(message));
        //Sending to rmi clients
        /*rmiServer.getClientsRMI().forEach((username, client)-> {
            try {
                client.notifyPick(board,tiles,playername);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        })
        */
    }
    //Now for Pop Up messages these are broadcast in cases such as player disconnection, final round
    //warning etc. but are sent to the client individually in case of error, so we need two separate
    //methods

    //Broadcast PopUp send, the method just takes one PopUpView as parameter, but in this case the text
    //displayed will be the same for all PopUpViews so, it's alright
    public void send(PopUpView popUpView){

        String text = popUpView.getErrorMessage();
        //Sending to socket clients
        PopUpMsg message = new PopUpMsg(text);
        socketServer.getSocketClients().forEach((username,client) -> client.send(message));
        //Sending to RMI clients
        rmiServer.getClientsRMI().forEach((username,client)-> {
            try {
                client.notifyPopUpView(text);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //The other case is an error message which is sent directly to the interested player, the next method
    //takes the username checks his connection and sends the popUp
    public void sendError(String username) {
        PopUpView view = game.getView().getPopUpViews().get(username);
        //remember game.getUserfromName returns null if no user with such name (clearly should not happen)
        User user = game.getUserfromName(username);

        if(user.getConnectionType()){
            String text = view.getErrorMessage();
            PopUpMsg message = new PopUpMsg(text);
            socketServer.getSocketClients().get(username).send(message);
        }
        else{
            String text = view.getErrorMessage();
            try{
                rmiServer.getClientsRMI().get(username).notifyPopUpView(text);
            }catch(RemoteException e){
                throw new RuntimeException(e);
            }

        }

    }
    
}
