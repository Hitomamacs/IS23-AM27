package org.project;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * interfaccia per istanziare il MAIN client
 */
public interface IClient {

    /**
     * method that notify the player a new message
     *@param nickname nickname of the author of the message
     *@param message message text
     */
    public void PrintMessageChat(String nickname, String message);

    /**
     * metodo che imposta la view iniziale
     * @param board rappresenta la board di gioco
     * @param pointStack rappresenta i punteggi per gli obiettivi comuni
     * @param gridsView rappresenta la libreria di ogni giocatore
     * @param tilesView in questa lista sono presenti le tessere scelte
     */
    public void UpdateInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView);

    /**
     * metodo che aggiorna la view dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @param playername username del giocatore
     */
    public void UpdatePick(String[][] board,String[] tilesView, String playername);

    /**
     * metodo che aggiorna la view dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tilesView array delle tessere prese
     * @param playername username del giocatore
     */
    public void UpdateTopUp(String[][] grid, String[] tilesView, String playername);

    /**
     * metodo che comunica il punteggio finale di ogni giocatore
     * @param score punteggi finali
     */
    public void UpdateScoreBoard (HashMap<String, Integer> score);

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     */
    public void UpdatePopUpView (String text);
}
