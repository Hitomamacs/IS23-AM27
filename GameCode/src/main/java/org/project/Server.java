package org.project;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public class Server {

    Game game;
    GameOrchestrator orchestrator;
    /**
     * LOCK to avoid the concurrency between players during login phase
     */
    private static final Object Login_lock= new Object();

    /**
     * HashMap that contains all players
     * string is player's username
     */
    private HashMap<String, Player> players;

    /**
     * RMI server
     */
    private static RMIServerApp rmiServer;

    /**
     * SOCKET server
     */
    //aggiungo un socket

    /**
     * constructor
     */
    public Server() throws RemoteException {
        players= new HashMap<>();
        //socketServer= new SocketServer()
        rmiServer= new RMIServerApp();
    }

    /**
     * MAIN
     */
    public static void main (String[] args) throws RemoteException {
        int socketPort; //lo metto uguale al settings.socketport
        int rmiPort=Settings.RMI_PORT;

        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[1]));
        }

        try{
            Server server=new Server();
            rmiServer.startRMIServer(rmiPort);
            //socketServer.startRMIServer(socketPort);
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


}
