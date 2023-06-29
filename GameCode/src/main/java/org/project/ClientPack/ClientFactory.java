package org.project.ClientPack;

import java.rmi.RemoteException;

public interface ClientFactory {

    /**
     *  The method creates a client connection by returning an object that implements the ConnectionInterface.
     * @return An object that implements the ConnectionInterface representing the client connection.
     * @throws RemoteException If there is a communication failure during the creation of the client.
     */
    public ConnectionInterface createClient() throws RemoteException;
}
