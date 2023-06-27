package org.project.ClientPack;

import org.project.Controller.Server.RMIServerInterface;

import java.rmi.RemoteException;

/**
 * class that continuously checks if the server is crashed
 */
public class RmiServerHandler extends Thread{
    /**
     * reference to rmi client
     */
    private RMIClient client;
    /**
     * reference to rmi server
     */
    private RMIServerInterface rmiServer;
    /**
     * if server is connected to the client this boolean is true
     */
    private boolean connected;

    /**
     * constructor
     * @param server reference to rmi server
     * @param client reference to rmi client
     */
    public RmiServerHandler(RMIServerInterface server, RMIClient client){
        this.client=client;
        this.rmiServer=server;
        connected=true;
    }

    /**
     * method executed by a separate thread.
     * In this method, you continue to call a server method.
     * if the remote exception is caught then the connection is closed
     */
    @Override
    public void run() {
        while(connected){
            try{
                rmiServer.isConnected();
            }catch(RemoteException remoteException){

            }

        }
    }

}
