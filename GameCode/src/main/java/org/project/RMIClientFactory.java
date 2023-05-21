package org.project;

import java.rmi.RemoteException;

public class RMIClientFactory implements ClientFactory {

    @Override
    public ConnectionInterface createClient() throws RemoteException {
        return new RMIClient();

    }
}