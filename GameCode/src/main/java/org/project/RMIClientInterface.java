package org.project;

import org.project.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface RMIClientInterface extends Remote {

    /**
     * method that shows the player a new message
     * @param nickname nickname of the author of the message
     * @param message message text
     * @throws RemoteException if something goes wrong with the connection
     */
    public void printMsgChat (String nickname, String message) throws RemoteException;

    /**
     * method invoked by the server to send the initial view
     * @param board represents the game board
     * @param pointStack represents scores for common goals
     * @param gridsView represents each player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param pGoalView in this list there are the personal goals fished
     * @param cGoalView in this list there are the common goals fished
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView, HashMap<String,Integer> pGoalView, List<Integer> cGoalView) throws RemoteException;

    /**
     * method invoked by the server after a successful pick
     * @param board new game board
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPick(String[][] board,String[] tilesView,String playername)throws RemoteException;

    /**
     * method invoked by the server after a successful topUp
     * @param grid new player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tilesView,String playername) throws RemoteException;

    /**
     * method invoked by the server to notify the client of each player's final score
     * @param score final scores
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score) throws RemoteException;

    /**
     * method invoked to print errors or other warnings to the client
     * @param text text
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text) throws RemoteException;

    //public String getNickname() throws RemoteException;
}
