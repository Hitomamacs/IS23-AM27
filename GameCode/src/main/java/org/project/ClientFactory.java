package org.project;

import org.project.ClientInterface;

import java.rmi.RemoteException;

public interface ClientFactory {
public ConnectionInterface createClient() throws RemoteException;
}
