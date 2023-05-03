package org.project;

import java.rmi.RemoteException;

public class provaClient {

    public static void main(String[] args) {
        RMIClientApp client;
        try {
            client = new RMIClientApp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        client.start();
    }
}
