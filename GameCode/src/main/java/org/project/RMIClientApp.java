package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

//AGGIUNGI CLIENT INTERFACE
public class RMIClientApp implements RMIClientInterface, ClientInterface{

    /**
     * reference to the server object
     */
    private RMIServerInterface rmiServer;
    /**
     * porta di utilizzo per la comunicazione
     */
    private int port;
    /**
     * nickname usato dal giocatore per connettersi
     */
    String nickname;
    /**
     * riferimento alla classe Client
     */
    private final IClient mainClient;

    /**
     * constructor
     * @throws RemoteException
     */
    public RMIClientApp(int port, IClient client) throws RemoteException{
        this.port=port;
        this.mainClient=client;
    }

    /**
     * metodo per preparsri alla connessione con un server
     */
    private void prepareForRmiConnection () {
        RMIClientInterface scheleton= null;
        try {
            scheleton = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        Registry registry= null;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            registry.bind("ClientRMI", scheleton);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

    //METODI ESTESI DALLA CLIENT INTERFACE
    /**
     * method that opens a connection with the RMI SERVER
     */
    public void startClient () {
        boolean nome;
        int port=Settings.RMI_PORT;
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(Settings.SERVER_NAME,port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //Looking up the registry for the remote object
        try {
            rmiServer= (RMIServerInterface) registry.lookup("Server");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connessione stabilita con successo");

        prepareForRmiConnection();

        try {
            nome=rmiServer.sendLogin("mary",false,4);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ok");
        if(nome){
            System.out.println("LOGGATO");
        }else{
            System.out.println("ERROIRE");
        }

    }

    public void sendLoginRequest(String nickname){

    }
    public void sendMessage(String message){

    }
    public void sendPickRequest(){

    }
    public void sendTopUpRequest(){

    }

    //METODI DELL'INTERFACCIA RMI CLIENT INTETRFACE
    /**
     * method that shows the player a new message on chat
     * @param nickname nickname of the author of the message
     * @param message message text
     * @throws RemoteException if something goes wrong with the connection
     */
    public void printMsgChat (String nickname, String message) throws RemoteException{
        System.out.println(message);
    }

    /**
     * metodo invocato dal server per inizializzare la view del client quando inizia una partita
     * @param board rappresenta la board di gioco
     * @param pointStack rappresenta i punteggi per gli obiettivi comuni
     * @param gridsView rappresenta la libreria di ogni giocatore
     * @param tilesView in questa lista sono presenti le tessere scelte
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView) throws RemoteException{
        //mainClient.UpdateInitialGameView(board,pointStack,gridsView,tilesView);
    }

    /**
     * metodo invocato dal server dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPick(String[][] board,String[] tilesView, String playername) throws RemoteException{
        //mainClient.UpdatePick(board, tilesView, playername);
    }

    /**
     * metodo invocato dal server dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tilesView array delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tilesView,String playername) throws RemoteException{
        //mainClient.UpdateTopUp(grid,tilesView,playername);
    }

    /**
     * metodo invocato dal server per notificare al client il punteggio finale di ogni giocatore
     * @param score punteggi finali
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score) throws RemoteException{
        //mainClient.UpdateScoreBoard(score);
    }

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text) throws RemoteException{
        //mainClient.UpdatePopUpView(text);
    }


    public String getNickname() {
        return nickname;
    }
}
