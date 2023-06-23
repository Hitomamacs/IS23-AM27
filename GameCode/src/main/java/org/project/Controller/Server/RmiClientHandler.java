package org.project.Controller.Server;

import org.project.RMIClient;
import org.project.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.HashMap;

public class RmiClientHandler extends Thread{

    private static final int KEEP_ALIVE_INTERVAL = 10;
    private Server server;
    private HashMap<String, RMIClientInterface> clientsRMI;
    private RMIServerApp rmiServerApp;

    public RmiClientHandler(Server server, RMIServerApp rmiServerApp) {
        this.server = server;
        this.rmiServerApp=rmiServerApp;
        clientsRMI=new HashMap<>();
    }

    @Override
    public void run() {
        while(true){

            if(clientsRMI !=null){
                for(String username : clientsRMI.keySet()){
                    try{
                        clientsRMI.get(username).isConnected();
                    }catch(RemoteException e){
                        disconnect(username);
                    }
                }
            }

        }
    }

    public void disconnect(String username){
        synchronized (rmiServerApp.getClientsRMI()){
            rmiServerApp.getClientsRMI().remove(username);
        }
        synchronized (clientsRMI){
            clientsRMI.remove(username);
        }
        server.set_player_disconnected(username);
    }

    public void removeClients(String username){
        clientsRMI.remove(username);
    }

    public void addClients(String username, RMIClientInterface rmiClient){
        clientsRMI.put(username,rmiClient);
    }

    public RMIServerApp getRmiServerApp() {
        return rmiServerApp;
    }

    public void setRmiServerApp(RMIServerApp rmiServerApp) {
        this.rmiServerApp = rmiServerApp;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public HashMap<String, RMIClientInterface> getClientsRMI() {
        return clientsRMI;
    }

    public void setClientsRMI(HashMap<String, RMIClientInterface> clientsRMI) {
        this.clientsRMI = clientsRMI;
    }
}