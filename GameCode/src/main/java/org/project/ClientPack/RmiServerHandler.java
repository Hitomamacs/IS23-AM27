package org.project.ClientPack;

import org.project.Controller.Server.RMIServerInterface;

import java.rmi.RemoteException;

public class RmiServerHandler extends Thread{
    private RMIClient client;
    private RMIServerInterface rmiServer;
    private boolean connected;

    public RmiServerHandler(RMIServerInterface server, RMIClient client){
        this.client=client;
        this.rmiServer=server;
        connected=true;
    }

    @Override
    public void run() {
        while(connected){
            try{
                rmiServer.isConnected();
            }catch(RemoteException remoteException){
                connected=false;
                System.out.println("server disconnesso");
                client.closeConnection();
            }

        }
    }

}
