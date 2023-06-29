package org.project.ClientPack;

import org.project.ClientPack.ClientFactory;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.RMIClient;

import java.rmi.RemoteException;

public class RMIClientFactory implements ClientFactory {
    @Override
    public ConnectionInterface createClient() throws RemoteException {
        return new RMIClient();

    }
}