package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RMIServerApp extends UnicastRemoteObject implements RMIServerInterface {

    private HashMap<String, RMIClientInterface> clientsRMI;

    private final Server server;

    /**
     * this is the constructor. Initialize the hash map.
     * @throws RemoteException if something goes wrong with the connection
     */
    public RMIServerApp() throws RemoteException{
        //chiamo costruttore server totale
        this.clientsRMI = new HashMap<>();
        this.server= new Server();
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
     * method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, RMIClientInterface client) throws RemoteException{
       server.login(nickname, new RMIClientApp ());
    }

    /**
     *method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendQuit(String nickname,RMIClientInterface client) throws RemoteException{
        server.quit(nickname,new RMIClientApp());
    }

    /**
     * method that allows the client to take tiles from the board
     * @param client  reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendPickRequest(String nickname,RMIClientInterface client) throws RemoteException{
        server.pick(nickname, new RMIClientApp());
    }

    /**
     *method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendTopUpRequest(String nickname,RMIClientInterface client) throws RemoteException{
        server.topUp(nickname, new  RMIClientApp());
    }

    /**
     * send a chat message to all players
     * @param client message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendMessageRequest(RMIClientInterface client, String message) throws RemoteException{
        server.sendMessage(message);
    }

}
