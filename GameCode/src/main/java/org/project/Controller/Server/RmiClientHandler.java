package org.project.Controller.Server;

import org.project.ClientPack.RMIClient;
import org.project.ClientPack.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.HashMap;

public class RmiClientHandler extends Thread{

    private Server server;
    private RMIClientInterface client;
    private RMIServerApp rmiServerApp;
    private String username;
    boolean connected;

    public RmiClientHandler(Server server, RMIServerApp rmiServerApp,RMIClientInterface client, String username) {
        this.server = server;
        this.rmiServerApp=rmiServerApp;
        this.client=client;
        this.username=username;
        connected=true;
    }

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

    public void disconnect(){
        connected=false;
        rmiServerApp.getClientsRMI().remove(username);
        server.set_player_disconnected(username);
    }


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public RMIClientInterface getClient() {
        return client;
    }

    public void setClient(RMIClientInterface client) {
        this.client = client;
    }

    public RMIServerApp getRmiServerApp() {
        return rmiServerApp;
    }

    public void setRmiServerApp(RMIServerApp rmiServerApp) {
        this.rmiServerApp = rmiServerApp;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}