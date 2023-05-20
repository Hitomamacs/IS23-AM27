package org.project;

import org.project.Model.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public interface ConnectionInterface {
    void SendJoinMessage(String username, boolean connection_type);

    void SendCreateGameMessage(String username, boolean connection_type, int numPlayers) throws RemoteException;

    void SendQuitMessage(String username);

    void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates);

    void SendTopUpMessage(String username, int firstTime, int tileIndex);

    String receiveMessage() throws IOException;
}
