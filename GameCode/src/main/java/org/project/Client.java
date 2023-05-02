package org.project;

import org.project.Controller.Server.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * client of the game
 */

public class Client implements IClient {

    /**
     * porta socket
     */
    static int socketPort= Settings.SOCKET_PORT;
    /**
     * porta RMI
     */
    static int  rmiPort= Settings.RMI_PORT;

    /**
     * client scelto (RMI O SOCKET)
     */
    static private ClientInterface client;
    /**
     * nickname scelto
     */
    private String nickname;
    /**
     * riferimento alla view del client
     */
    private ClientView clientView;
    /**
     * booleano che mi dice se la partita è iniziata
     */
    private boolean gameStarted;


    /**
     * constructor
     */
    public Client() {
        nickname="client";
        clientView= new ClientView();
        gameStarted=false;
    }

    /**
     * MAIN
     */
    public static void main(String args[]){

        String connessione;
        final Scanner stdin= new Scanner(System.in);

        //Controllo se le porte mi vengono passate in ingresso
        //TODO: anche server name può essere passato?
        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[0]));
        }

        System.out.println("Che tipo di connessione vuoi usare (rmi/socket)?");
        connessione= stdin.nextLine();
        switch(connessione){
            case "rmi":
                try{
                    //client= new RMIClientApp(rmiPort,this);
                    client.startClient();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "socket":
                 try{
                    client= new SocketClientApp();
                    client.startClient();
                 }catch(Exception e ){
                    e.printStackTrace();
                 }
                break;
        }

    }
    //METODI PER AVVIARE RMI O SOCKET

    //METODI INVOCATI SUL MAIN CLIENT
    /**
     * method that notify the player a new message
     *@param nickname nickname of the author of the message
     *@param message message text
     */
    public void PrintMessageChat(String nickname, String message){

    }

    /**
     * metodo che imposta la view iniziale
     * @param board rappresenta la board di gioco
     * @param pointStack rappresenta i punteggi per gli obiettivi comuni
     * @param gridsView rappresenta la libreria di ogni giocatore
     * @param tilesView in questa lista sono presenti le tessere scelte
     */
    public void UpdateInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView){

        int i,j;

        for(i=0;i<9;i++){
            for(j=0;j<9;j++){
                clientView.getBoard()[i][j]=board[i][j];
            }
        }

        for(i=0;i< pointStack.size(); i++ ){
            clientView.getPointStack().add(pointStack.get(i));
        }

        clientView.getGridsview().putAll(gridsView);

        clientView.getTilesview().putAll(tilesView);
    }

    /**
     * metodo che aggiorna la view dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @param playername username del giocatore
     */
    public void UpdatePick(String[][] board,String[] tilesView, String playername){

        int i,j;

        for(i=0;i<9;i++){
            for(j=0;j<9;j++){
                clientView.getBoard()[i][j]=board[i][j];
            }
        }

        for(i=0;i< tilesView.length; i++ ){
            clientView.getTilesview().get(playername)[i]=tilesView[i];
        }
    }

    /**
     * metodo che aggiorna la view dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tilesView array delle tessere prese
     * @param playername username del giocatore
     */
    public void UpdateTopUp(String[][] grid,String[] tilesView,String playername){
        int i,j;

        for(i=0;i<6;i++){
            for(j=0;j<5;j++){
                clientView.getGridsview().get(playername)[i][j]=grid[i][j];
            }
        }

        for(i=0;i< tilesView.length; i++ ){
            clientView.getTilesview().get(playername)[i]=tilesView[i];
        }
    }

    /**
     * metodo che comunica il punteggio finale di ogni giocatore
     * @param score punteggi finali
     */
    public void UpdateScoreBoard (HashMap<String, Integer> score){
        clientView.getScoreBoard().putAll(score);
    }

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     */
    public void UpdatePopUpView (String text){
        clientView.setErrorMessage(text);
    }

    // METODI  CHE CHIAMANO IL SERVER
    //TODO: avrò i metodi di login, send chat message e metodi per fare le mosse, questi metodi chiameranno i metodi presenti in RMI/SOCKET client che chismeranno i metodi del server


    //GETTER

    /**
     * metodo getter che ritorna la socket port scelta
     */
    public static int getSocketPort() {
        return socketPort;
    }

    /**
     *metodo che ritorna il client
     */
    public static ClientInterface getClient() {
        return client;
    }

    /**
     * metodo che ritorna la porta rmi scelta
     */
    public static int getRmiPort() {
        return rmiPort;
    }

    /**
     *metodo che ritorna il nickname finale
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * metodo che ritorna la client view
     */
    public ClientView getClientView() {
        return clientView;
    }

    /**
     *metodo che ritorna il flag che mi dice se una partita è già iniziata
     */
    public boolean isGameStarted() {
        return gameStarted;
    }



}

