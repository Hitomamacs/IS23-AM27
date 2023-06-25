package org.project.ClientPack;

import java.rmi.RemoteException;

public interface ClientFactory {
public ConnectionInterface createClient() throws RemoteException;
}
