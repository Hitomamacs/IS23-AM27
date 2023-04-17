package org.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of server
 */

public interface RMIServerInterface extends Remote {
    /**
     * remote method for logging in the player through the nickname.
     * The method checks that the nickname is different for each logged in player.
     * @param nickname player's name
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendLogin(String nickname, RMIClientInterface client) throws RemoteException;


    /**
     *remote method called when a player wants to drop out. A message of the event that occurred is sent to all.
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendQuit(String nickname,RMIClientInterface client) throws RemoteException;

    /**
     * remote method that allows the client to take tiles from the board
     * @param client  reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendPickRequest(String nickname,RMIClientInterface client) throws RemoteException;

    /**
     *remote method that given a column as input, puts the drawn tiles in that column of the player's grid
     * @param client reference to the client that wants to execute the method
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendTopUpRequest(String nickname,RMIClientInterface client) throws RemoteException;

    /**
     * remote method that send a chat message to all players
     * @param client message sender
     * @param message message you want to send
     * @throws RemoteException if something goes wrong with the connection
     */
    public void sendMessageRequest(RMIClientInterface client, String message) throws RemoteException;
}
