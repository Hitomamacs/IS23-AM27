package org.project.Controller.Server;

import org.project.RMIClientApp;
import org.project.RMIClientInterface;
import org.project.Model.Coordinates;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface of server
 */

public interface RMIServerInterface extends Remote {
    /**
     * remote method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, boolean connectionType, RMIClientApp client) throws RemoteException;

    /**
     * remote method for logging the first player through the nickname.
     * @param nickname player's name
     * @param connectionType =0 if connection is RMI, =1 if connection is Socket
     * @param numPlayers Number of players in the match
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, boolean connectionType,RMIClientApp client ,int numPlayers) throws RemoteException;


    /**
     *remote method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendQuit(String nickname) throws RemoteException;

    /**
     * remote method that allows the client to take tiles from the board
     * @param nickname player's name
     * @param coordinates coordinates of the tiles to be taken
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendPickRequest(String nickname, List<Coordinates> coordinates) throws RemoteException;

    /**
     * remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param nickname player's name
     * @param column Player's Choice Column
     * @param tileIndex
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendTopUpRequest(String nickname, int column, int tileIndex) throws RemoteException;

    /**
     * remote method that send a chat message to all players
     * @param client message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendMessageRequest(RMIClientApp client, String message) throws RemoteException;
}
