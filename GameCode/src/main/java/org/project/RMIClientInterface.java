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
     * metodo invocato dal server per mandare la view iniziale
     * @param board rappresenta la board di gioco
     * @param pointStack rappresenta i punteggi per gli obiettivi comuni
     * @param gridsView rappresenta la libreria di ogni giocatore
     * @param tilesView in questa lista sono presenti le tessere scelte
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView) throws RemoteException;

    /**
     * metodo invocato dal server dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPick(String[][] board,String[] tilesView,String playername)throws RemoteException;

    /**
     * metodo invocato dal server dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tilesView array delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tilesView,String playername) throws RemoteException;

    /**
     * metodo invocato dal server per notificare al client il punteggio finale di ogni giocatore
     * @param score punteggi finali
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score) throws RemoteException;

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text) throws RemoteException;

}
