package org.project.Controller.Server;

import org.project.Controller.Control.InvalidLoginException;
import org.project.Model.Coordinates;
import org.project.ClientPack.RMIClientInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

/**
 * RMI SERVER
 */

public class RMIServerApp implements RMIServerInterface {

    /**
     * hash map containing all RMI players.
     * The key is the username
     * the second parameter is the reference to the client
     */
    private HashMap<String, RMIClientInterface> clientsRMI;

    /**
     * reference to main server
     */
    private final Server server;
    private HashMap<String,RmiClientHandler> rmiClientsHandler;

    /**
     * this is the constructor.
     * @throws RemoteException if something goes wrong with the connection
     */
    public RMIServerApp(Server server) throws RemoteException{
        this.clientsRMI = new HashMap<>();
        this.server = server;
        rmiClientsHandler=new HashMap<>();
    }

    //TODO javadoc
    public void flushRMIClients() throws RemoteException {
        //TODO haven't really understood how to close the connections
        clientsRMI = new HashMap<>();
    }

    /**
     * method called by the main server to start the RMI server
     * @param port port the server is listening on
     */
    public void startRMIServer(int port) {
        RMIServerInterface stub= null;
        try {
            System.setProperty("java.rmi.server.hostname", Settings.SERVER_NAME);
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


    //METHODS INVOCATED BY CLIENTS--SONO I METODI DELL'RMI INTERFACE

    /**
     * remote method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendJoin(String nickname, boolean connectionType, RMIClientInterface client) throws RemoteException{
        boolean check = false;
        try {
            check=server.join(nickname, connectionType);
        } catch (InvalidLoginException e) {
            client.notifyPopUpView(e.getMessage(), e.getIdentifier());
        }
        if(check==true){
            clientsRMI.put(nickname,client);
            client.notifyPopUpView("Successfully joined game", 1);
            server.getController().refreshRequest(nickname);

            RmiClientHandler rch=new RmiClientHandler(server,this,client,nickname);
            rmiClientsHandler.put(nickname, rch);
            rmiClientsHandler.get(nickname).start();

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
    public boolean sendCreateGame(String nickname, boolean connectionType, int numPlayers, RMIClientInterface client) throws RemoteException{
        boolean check = false;
        try {
            check = server.create_game(nickname,connectionType,numPlayers);
        } catch (InvalidLoginException e) {
            client.notifyPopUpView(e.getMessage(), e.getIdentifier());
        }
        if(check==true){
            clientsRMI.put(nickname, client);
            client.notifyPopUpView("Successfully created game", 0);

            RmiClientHandler rch=new RmiClientHandler(server,this,client,nickname);
            rmiClientsHandler.put(nickname, rch);
            rmiClientsHandler.get(nickname).start();

            return true;
        }else{
            client.notifyPopUpView("Already an existing game", 0);
        }
        return false;
    }

    /**
     * remote method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param nickname player's name
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendQuit(String nickname) throws RemoteException{
        boolean check;
        check=server.quit(nickname);
        if(check){
            clientsRMI.remove(nickname);
            rmiClientsHandler.get(nickname).setConnected(false);
            rmiClientsHandler.remove(nickname);
            return true;
        }
        return false;
    }

    /**
     * remote method that allows the client to take tiles from the board
     * @param nickname player's name
     * @param coordinates coordinates of the tiles to be taken
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendPickRequest(String nickname, List<Coordinates> coordinates) throws RemoteException{
        boolean check;
        check=server.pick(nickname,coordinates);
        if(check){
            return true;
        }
        return false;
    }
    public boolean sendChat(String username, String text){
        boolean check;
        check = server.chat(username, text);
        if(check){
            return true;
        }
        return false;
    }
    public boolean sendChat(String username, String text, String receiver){
        boolean check;
        check = server.chat(username, text, receiver);
        if(check){
            return true;
        }
        return false;
    }

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param nickname player's name
     * @param column Player's Choice Column
     * @param tileIndex
     * @throws RemoteException if something goes wrong with the connection
     */
    public boolean sendTopUpRequest(String nickname, int column, int tileIndex) throws RemoteException{
        boolean check;
        check=server.topUp(nickname, column, tileIndex);
        if(check){
            return true;
        }
        return false;
    }


    /**
     * method called constantly by the client rmi to understand if the server is crushed
     * @throws RemoteException if something goes wrong with the connection
     */
    @Override
    public void isConnected() throws RemoteException {

    }

    /**
     * getter rmi clients
     * @return
     */
    public HashMap<String, RMIClientInterface> getClientsRMI() {
        return clientsRMI;
    }

}
