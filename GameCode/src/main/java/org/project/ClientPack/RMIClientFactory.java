package org.project.ClientPack;

import org.project.ClientPack.ClientFactory;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.RMIClient;

import java.rmi.RemoteException;

/**
 * A factory class for creating RMIClient objects that implement the ConnectionInterface.
 */
public class RMIClientFactory implements ClientFactory {

    /**
     * Creates a new RMIClient object that implements the ConnectionInterface.
     * @return new instance of the RMIClient class representing the client connection.
     * @throws RemoteException If there is a communication failure during the creation of the client.
     */
    @Override
    public ConnectionInterface createClient() throws RemoteException {
        return new RMIClient();

    }
}