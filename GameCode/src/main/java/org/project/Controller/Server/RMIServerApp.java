package org.project.Controller.Server;

import org.project.RMIClientApp;
import org.project.Model.Coordinates;
import org.project.RMIClientInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RMIServerApp implements RMIServerInterface {

    /**
     * hash map che contiene tutti i giocatori RMI.
     * La chiave è l'username
     * il secondo parametro è il riferimento al client
     */
    private final HashMap<String, RMIClientInterface> clientsRMI;

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
     */
    public void startRMIServer(int port) {
        RMIServerInterface stub= null;
        try {
            stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Registry registry= null;
        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            registry.bind("Server", stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RMI server bound and ready");
    }

    /**
     * metodo che serve per comunicare con il client
     * @return il client
     */
    private RMIClientInterface getRmiClient () {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //Looking up the registry for the remote object
        RMIClientInterface client;
        try {
            client = (RMIClientInterface) registry.lookup("ClientRMI");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    //METHODS INVOCATED BY CLIENTS--SONO I METODI DELL'RMI INTERFACE

    /**
     * remote method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendLogin(String nickname, boolean connectionType) throws RemoteException{
        boolean check;
        check=server.login(nickname, connectionType);
        if(check==true){
            clientsRMI.put(nickname,getRmiClient());
            return true;
        }

        return false;
    }

    /**
     * remote method for logging the first player through the nickname.
     *
     * @param nickname       player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers     Number of players in the match
     * @return
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendLogin(String nickname, boolean connectionType, int numPlayers) throws RemoteException{
        boolean check;
        check=server.login(nickname,connectionType,numPlayers);
        if(check==true){
            clientsRMI.put(nickname, getRmiClient());
            return true;
        }
        return false;
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
     * @param nickname message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
     */

    public void sendMessageRequest(String nickname, String message) throws RemoteException{
        server.sendMessage(nickname, message);
    }


    public HashMap<String, RMIClientInterface> getClientsRMI() {
        return clientsRMI;
    }

}
