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
    public boolean quit(String username){
        return false;
    }

    public void sendMessage (String message){
        //chiamo metodo sul client che mi stampa il messaggio
    }
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
