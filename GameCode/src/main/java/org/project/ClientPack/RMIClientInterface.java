package org.project.ClientPack;
import org.project.ClientPack.ClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * interface to be implemented by rmi client. here are the methods that the rmi server can call on the rmi client
 */

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
        public void notifyPopUpView (String text, int identifier) throws RemoteException;

        //todo javadoc
        public void notifyTurn(String username, boolean move) throws RemoteException;

        /**
         * method called by the server to accept that the client is always connected.
         * When the exception returns it understands that the client has disconnected and therefore
         * removes the player from the list of online players
         * @throws RemoteException when something goes wrong with the connection
         */
        public void isConnected() throws RemoteException;

        /**
         * method invoked by the server to notify the client of a new chat message
         * @param playername message author
         * @param text text of the message
         */
        public void notifyChat(String playername, String text) throws RemoteException;

    public void notifyChat(String playername, String text, String receiver) throws RemoteException;

    //public String getNickname() throws RemoteException;
}
