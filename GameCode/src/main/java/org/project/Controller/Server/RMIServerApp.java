package org.project.Controller.Server;

import org.project.RMIClientApp;
import org.project.RMIClientInterface;
import org.project.Model.Coordinates;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RMIServerApp extends UnicastRemoteObject implements RMIServerInterface {

    /**
     * hash map che contiene tutti i giocatori RMI.
     * La chiave è il riferimento al client
     * String è l'username scelto
     */
    private final HashMap<String, RMIClientApp> clientsRMI;

    private final Server server;

    /**
     * this is the constructor. Initialize the hash map.
     * @throws RemoteException if something goes wrong with the connection
     */
    public RMIServerApp(Server server) throws RemoteException{
        this.clientsRMI = new HashMap<>();
        this.server = server;
    }

    /**
     * method called by the main server to start the RMI server
     * @param port port the server is listening on
     * @throws Exception
     */
    public void startRMIServer(int port) throws Exception{
        Registry registry= LocateRegistry.createRegistry(port);
        registry.bind("Server", this);
        System.out.println("RMI server bound and ready");
    }

    //METHODS INVOCATED BY CLIENTS

    /**
     * remote method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, boolean connectionType, RMIClientApp client) throws RemoteException{
        boolean check;
        check=server.login(nickname, connectionType);
        if(check==true){
            clientsRMI.put(nickname,client);
        }
    }

    /**
     * remote method for logging the first player through the nickname.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, boolean connectionType, RMIClientApp client,int numPlayers) throws RemoteException{
        boolean check;
        check=server.login(nickname,connectionType,numPlayers);
        if(check==true){
            clientsRMI.put(nickname, client);
        }
    }


    /**
     * remote method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param nickname player's name
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendQuit(String nickname) throws RemoteException{
        server.quit(nickname);
    }

    /**
     * remote method that allows the client to take tiles from the board
     * @param nickname player's name
     * @param coordinates coordinates of the tiles to be taken
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendPickRequest(String nickname, List<Coordinates> coordinates) throws RemoteException{
        server.pick(nickname, coordinates);
    }

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param nickname player's name
     * @param column Player's Choice Column
     * @param tileIndex
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendTopUpRequest(String nickname, int column, int tileIndex) throws RemoteException{
        server.topUp(nickname, column, tileIndex);
    }


    /**
     * send a chat message to all players
     * @param client message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendMessageRequest(RMIClientApp client, String message) throws RemoteException{
        server.sendMessage(client.getNickname(), message);
    }


    public HashMap<String, RMIClientApp> getClientsRMI() {
        return clientsRMI;
    }
}
