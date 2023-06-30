package org.project.ClientPack;

import org.project.Controller.Messages.Message;
import org.project.Model.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that expose communication methods both for RMI and socket clients
 */
public interface ConnectionInterface {

    ClientView getClientView();

    boolean get_connection_type();

    /**
     * Sends a join message
     * @param username player's name
     * @param connection_type socket/rmi
     */
    void SendJoinMessage(String username, boolean connection_type);

    /**
     * Sends a create game message
     * @param username player's name
     * @param connection_type socket/rmi
     * @param numPlayers number of the players that want to take part in the game
     * @throws RemoteException
     */
    void SendCreateGameMessage(String username, boolean connection_type, int numPlayers) throws RemoteException;

    /**
     * Sends a quit message
     * @param username player's name who wants to quit
     */
    void SendQuitMessage(String username);

    /**
     * Sends a pick message
     * @param username player's name
     * @param numTiles number of the tiles that the player has picked
     * @param coordinates coordinates of the tiles that the player has picked
     */
    void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates);

    /**
     * Sends a chat message
     * @param username player's name
     * @param text text of the message
     */
    void SendChatMessage(String username, String text);

    void SendChatMessage(String username, String text, String receiver);

    /**
     * Sends a top-up message
     * @param username player's name
     * @param firstTime column number
     * @param tileIndex number of the tile in the array picked tiles
     */
    void SendTopUpMessage(String username, int firstTime, int tileIndex);

    /**
     * @return the message
     * @throws IOException
     */
    String receiveMessage() throws IOException;

    String receiveMessage(Message msg) throws IOException;

    void setUserInterface(UserInterface client);
}
