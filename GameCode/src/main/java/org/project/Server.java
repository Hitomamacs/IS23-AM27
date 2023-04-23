package org.project;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private Game game;
    private GameOrchestrator orchestrator;

    /**
     * variabile per tener conto di quante persone ho aggiunto
     */
    int count_players=0;

    /**
     * RMI server
     */
    private static RMIServerApp rmiServer;
    private static SocketServer socketServer;
    /**
     * SOCKET server
     */
    //aggiungo un socket

    /**
     * constructor
     */
    //TODO:devo passare game e game orchestrator
    public Server() throws RemoteException {
        socketServer= new SocketServer(this, Settings.SOCKET_PORT);
        rmiServer= new RMIServerApp(this);
    }

    /**
     * MAIN
     */
    public static void main (String[] args) throws RemoteException {

        int rmiPort=Settings.RMI_PORT;
        int socketPort = Settings.SOCKET_PORT;

        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[1]));
        }

        try{
            Server server=new Server();
            rmiServer.startRMIServer(rmiPort);
            socketServer.startSocketServer();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //METODI CHE IL CLIENT PUO CHIAMARE SUL SERVER

    /**
     * method that allows the client to take tiles from the board
     * @param username player's name
     * @param coordinates coordinates of the tiles to be taken
     */
    public boolean pick(String username, List<Coordinates> coordinates){
        //Check if it's actually the players turn (we will make sure client can't send moves if it isn't
        //his turn so this check is redundant)
        if(orchestrator.getCurrentPlayer().getNickname().equals(username)){
            orchestrator.setPickedCoordinates(coordinates);
            orchestrator.executeState();
            //now if the coordinates were valid then the pieces have been picked and put in players pickedTiles
            if(!orchestrator.getCurrentPlayer().pickedTilesIsEmpty()){
                return true;
            }
        } //else it either wasn't players turn or the coordinates weren't valid and are still waiting for
        //valid input
        return false;
    }

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param username player's name
     * @param column Player's Choice Column
     * @param tileIndex
     */
    public boolean topUp(String username, int column, int tileIndex){

        int num_tiles = 0;
        if(orchestrator.getCurrentPlayer().getNickname().equals(username)){
            num_tiles = orchestrator.getCurrentPlayer().pickedTilesNum();
            orchestrator.getCurrentPlayer().setSelectedColumn(column);
            orchestrator.getCurrentPlayer().setTileIndex(tileIndex);
            orchestrator.executeState();
            if(orchestrator.getCurrentPlayer().pickedTilesNum() == num_tiles - 1){
                return true;
            }
        }
        return false;
    }
    /**
     * method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     */
    public boolean login(String username, boolean connectionType){
        //TODO checks once persistence has been implemented
        if(!game.getUsers().isEmpty()){
            for(int i = 0; i < game.getUsers().size(); i++){
                if(game.getUsers().get(i).getUsername().equals(username))
                    return false;//Probably better if I throw exceptions instead to distinguish
            }                    //the reasons the method was unsuccessful
            game.getUsers().add(new User(username, connectionType));

            return true;
        }
        return false;
    }
    /**
     * method for logging the FIRST player through the nickname.
     * @param username player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     */
    public boolean login(String username, boolean connectionType, int numPlayers){
        if(game.getUsers().isEmpty()) {
            game.getUsers().add(new User(username, connectionType));
            game.setNumPlayers(numPlayers);
            return true;
        }
        //Means a game has already been created
        //Should probably do another check to see if numPlayers is acceptable
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
        for(RMIClientApp rmiCl: rmiServer.getClientsRMI().keySet()){
            try{rmiCl.printMsg(username,message);}
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

    //Next are the methods for sending information to the clients, two smaller methods sendRMI and sendSocket
    //and a more general method used for broadcasting messages to all the clients (which iterates on the two
    //client hash maps and then calls the previously mentioned methods), when the message has to be sent to a
    //single client we directly use the smaller methods

    //Sent at the beginning of the game and updates the client view (Could be the one sent if we decide
    //to implement a refresh view method called from the client)
    public void sendSocket(SocketClientHandler socketClient,  VirtualView view){

        String[][] board = view.getBoardView().getBoard();
        List<Integer> pointStack = view.getPointStackView().getPointList();
        HashMap<String, String[][]> gridsview = new HashMap<>();
        HashMap<String, String[]> tilesview = new HashMap<>();
        for(GridView gView : view.getGridViews()){
            gridsview.put(gView.getUsername(), gView.grid);
        }
        for(TilesView tView : view.getTilesViews()){
            tilesview.put(tView.getUsername(), tView.getPlayerTiles());
        }
        RefreshMsg message = new RefreshMsg(board, pointStack, gridsview, tilesview);
        socketClient.send(message);
    }
    //Sends necessary stuff for update after a successful pick move
    public void sendSocket(SocketClientHandler socketClient, BoardView boardView, TilesView tilesView){

        String[][] board = boardView.getBoard();
        String[] tiles = tilesView.getPlayerTiles();
        String playername = tilesView.getUsername();

        UpdatePickMsg message = new UpdatePickMsg(playername, tiles, board);
        socketClient.send(message);
    }
    //Sends necessary stuff after a successful topUp move
    public void sendSocket(SocketClientHandler socketClient, GridView gridView, TilesView tilesView){

        String[][] grid = gridView.getGridView();
        String[] tiles = tilesView.getPlayerTiles();
        String playername = tilesView.getUsername();

        UpdateTopUPMsg message = new UpdateTopUPMsg(playername, tiles, grid);
        socketClient.send(message);
    }
    //Sends necessary stuff to display a popUp message (after failed moves, final round alert, player has
    //disconnected
    public void sendSocket(SocketClientHandler socketClient, PopUpView popUpView){

        String text = popUpView.getErrorMessage();

        PopUpMsg message = new PopUpMsg(text);
        socketClient.send(message);
    }
    //Sends necessary stuff to display final scoreBoard after game has ended
    public void sendSocket(SocketClientHandler socketClient, ScoreBoardView scoreBoard){

        HashMap<String, Integer> score = scoreBoard.getScoreBoard();

        ScoreBoardMsg message = new ScoreBoardMsg(score);
        socketClient.send(message);
    }
}
