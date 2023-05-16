package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//AGGIUNGI CLIENT INTERFACE
public class RMIClientApp extends UnicastRemoteObject implements RMIClientInterface, ClientInterface{

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
     * riferimento alla view del client
     */
    private ClientView clientView= new ClientView();


    /**
     * constructor
     * @throws RemoteException
     */
    public RMIClientApp(int port) throws RemoteException{
        this.port=port;
       // this.mainClient=client;
    }


    //METODI ESTESI DALLA CLIENT INTERFACE
    /**
     * method that opens a connection with the RMI SERVER
     */
    public void startClient () {
        boolean nome;
        boolean successo;
        final Scanner stdin= new Scanner(System.in);
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

        System.out.println("stai facendo il login");
        try {
            System.out.println("Inserisci nome");
            nickname=stdin.nextLine();
            nome=rmiServer.sendCreateGame(nickname,false,2, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(nome){
            System.out.println("LOGGATO");
        }else {
            try {
                nome=rmiServer.sendJoin(nickname,false, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(nome) {
                System.out.println("LOGGATO");
            }else{
                System.out.println("ERRORE");
            }
        }


        while(true){
            System.out.println("cosa vuoi");
            String input;
            input=stdin.nextLine();
            switch(input){
                case "pick":
                    int x,y;
                    List<Coordinates> coordinates= new ArrayList<>();
                    System.out.println("Enter x coordinate: ");
                    x=stdin.nextInt();
                    System.out.println("Enter y coordinate: ");
                    y=stdin.nextInt();
                    coordinates.add(new Coordinates(x,y));
                    try {
                        successo=rmiServer.sendPickRequest(nickname, coordinates);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    if(successo){
                        System.out.println("PICK SUCCESSO");
                    }else {
                        System.out.println("PICK ERRORE");
                    }
                    break;
                case "topup":
                    int column, tileIndex;
                    System.out.println("Inserisci colonna :  ");
                    column=stdin.nextInt();
                    System.out.println("Inserisci tile index : ");
                    tileIndex=stdin.nextInt();
                    try {
                        successo=rmiServer.sendTopUpRequest(nickname,column,tileIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    if(successo){
                        System.out.println("TOP UP SUCCESSO");
                    }else {
                        System.out.println("TOP UP ERRORE");
                    }
                    break;
                case "quit":
                    try {
                        successo=rmiServer.sendQuit(nickname);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    if(successo){
                        System.out.println("QUIT SUCCESSO");
                    }else {
                        System.out.println("QUIT ERRORE");
                    }
            }
        }


    }

   /* public void sendLoginRequest(String nickname){

    }
    public void sendMessage(String message){

    }
    public void sendPickRequest(){

    }
    public void sendTopUpRequest(){

    }*/

    //METODI DELL'INTERFACCIA RMI CLIENT INTERFACE
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
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView, HashMap<String,Integer> pGoalView, List<Integer> cGoalView) throws RemoteException{
        int i,j;

        clientView.setBoard(board);
        clientView.setPointStack(pointStack);
        clientView.setGridsview(gridsView);
        clientView.setTilesview(tilesView);

        System.out.println("the game is started");

        //stampa board iniziale
        for(i=0;i<9;i++){
            for(j=0;j<9;j++){
                System.out.print(clientView.getBoard()[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    /**
     * metodo invocato dal server dopo una pick avvenuta con successo
     * @param board nuova board di gioco
     * @param tilesView lista delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPick(String[][] board,String[] tilesView, String playername) throws RemoteException{
        int i,j;

        //aggiorno board
        for(i=0;i<9;i++){
            for(j=0;j<9;j++){
                clientView.getBoard()[i][j]=board[i][j];
            }
        }
        //aggiorno tiles view
        for(i=0;i< tilesView.length; i++ ){
            clientView.getTilesview().get(playername)[i]=tilesView[i];
        }

        System.out.println("\nPrinting updated board");
        for(i=0;i<9;i++){
            for(j=0;j<9;j++){
                System.out.print(clientView.getBoard()[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("Printing " + playername + " tiles");
        for (String tile : tilesView) {
            System.out.println(tile + " ");
        }

        String[][] grid = clientView.getGridsview().get(playername);
        System.out.println("This is "+playername+" grid");
        for ( i = 0; i<6; i++ ){
            for ( j = 0; j<5; j++){
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    /**
     * metodo invocato dal server dopo una topUp avvenuta con successo
     * @param grid nuova libreria del giocatore
     * @param tilesView array delle tessere prese
     * @param playername username del giocatore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tilesView,String playername) throws RemoteException{
        int i,j;

        //aggiorna grid view e stampa grid
        System.out.println("This is "+playername+ " grid now");
        for(i=0;i<6;i++){
            for(j=0;j<5;j++){
                clientView.getGridsview().get(playername)[i][j]=grid[i][j];
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        for(i=0;i< tilesView.length; i++ ){
            clientView.getTilesview().get(playername)[i]=tilesView[i];
        }

    }

    /**
     * metodo invocato dal server per notificare al client il punteggio finale di ogni giocatore
     * @param score punteggi finali
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score) throws RemoteException{
        clientView.getScoreBoard().putAll(score);
    }

    /**
     * metodo invocato per stampare errori o altre cose sul client
     * @param text testo dell'errore
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text) throws RemoteException{
        clientView.setErrorMessage(text);
        System.out.println(text);
    }


    public String getNickname() {
        return nickname;
    }
}
