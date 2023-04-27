package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RMIClientApp extends UnicastRemoteObject implements RMIClientInterface {

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
     * method that opens a connection with the RMI server
     * @throws Exception
     */
   //METODI ESTESI DALLA CLIENTINTERFACE
    public void startClient() throws Exception{
        //String nick;
        //Getting the registry
        Registry registry;
        registry= LocateRegistry.getRegistry(Settings.SERVER_NAME,port);

        //Looking up the registry for the remote object
        rmiServer= (RMIServerInterface) registry.lookup("Server");
        System.out.println("Connessione stabilita");
        /*final Scanner stdin= new Scanner(System.in);
        System.out.println("Quale nickname vuoi utilizzare?");
        nick=stdin.nextLine();
        this.rmiServer.sendLogin(nick, false,this);*/
    }

    //METODI DELL'INTERFACCIA RMICLIENT

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
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView){
        mainClient.UpdateInitialGameView(board,pointStack,gridsView,tilesView);
    }

    /**
     * metodo invocato dal server dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPick(String[][] board,String[] tilesView){
        mainClient.UpdatePick(board, tilesView);
    }

    /**
     * metodo invocato dal server dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tiles array delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tiles,String playername){
        mainClient.UpdateTopUp(grid,tiles,playername);
    }

    /**
     * metodo invocato dal server per notificare al client il punteggio finale di ogni giocatore
     * @param score punteggi finali
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score){
        mainClient.UpdateScoreBoard(score);
    }

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text){
        mainClient.UpdatePopUpView(text);
    }

    //GETTER
    public String getNickname() {
        return nickname;
    }
}
