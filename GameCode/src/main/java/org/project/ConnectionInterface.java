package org.project;

import org.project.Controller.Messages.Message;
import org.project.Model.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public interface ConnectionInterface {

    ClientView getClientView();

    boolean get_connection_type();
    void SendJoinMessage(String username, boolean connection_type);

    void SendCreateGameMessage(String username, boolean connection_type, int numPlayers) throws RemoteException;

    void SendQuitMessage(String username);

    void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates);

    void SendTopUpMessage(String username, int firstTime, int tileIndex);

    String receiveMessage() throws IOException;

    String receiveMessage(Message msg) throws IOException;

    void setUserInterface(UserInterface client);


}
