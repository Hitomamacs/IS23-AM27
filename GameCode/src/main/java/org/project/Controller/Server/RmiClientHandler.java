package org.project.Controller.Server;

import org.project.ClientPack.RMIClient;
import org.project.ClientPack.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * class used to see if the rmi client is connected
 */
public class RmiClientHandler extends Thread{

    /**
     * reference to the main server
     */
    private Server server;
    /**
     * reference to rmi client
     */
    private RMIClientInterface client;
    /**
     * reference to rmi server
     */
    private RMIServerApp rmiServerApp;
    /**
     * player's username
     */
    private String username;
    /**
     * if the client is connected this boolean is true
     */
    boolean connected;

    /**
     * constructor
     * @param server reference to main server
     * @param rmiServerApp reference to rmi server
     * @param client reference to the client
     * @param username player's name
     */
    public RmiClientHandler(Server server, RMIServerApp rmiServerApp,RMIClientInterface client, String username) {
        this.server = server;
        this.rmiServerApp=rmiServerApp;
        this.client=client;
        this.username=username;
        connected=true;
    }

    /**
     * method executed by a separate thread which keeps calling
     * a client method to see if it is connected.
     * if it detects the remote exception it means that the
     * client is crushed so it aborts the connection.
     */

    @Override
    public void run() {
        while(connected){
            try{
                client.isConnected();
            }catch (RemoteException remoteException){
                disconnect();
            }
        }
    }

    /**
     *method called if the remote exception is caught.
     *set the client boolean to false.
     */
    public void disconnect(){
        connected=false;
        if(rmiServerApp.getClientsRMI().containsKey(username))
        {
            rmiServerApp.getClientsRMI().remove(username);
        }
        server.set_player_disconnected(username);
    }

    /**
     * getter server
     * @return server
     */
    public Server getServer() {
        return server;
    }

    /**
     * setter server
     * @param server server
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * getter client
     * @return client
     */
    public RMIClientInterface getClient() {
        return client;
    }

    /**
     * setter client
     * @param client client
     */
    public void setClient(RMIClientInterface client) {
        this.client = client;
    }

    /**
     * getter if client is connected
     * @return connected
     */
    public boolean getConnected() {
        return connected;
    }

    /**
     * setter if client is connected
     * @param connected connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}