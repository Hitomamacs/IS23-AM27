package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.FileNotFoundException;
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

    public ClientView getClientView() {
        return clientView;
    }

    /**
     * reference to the server object
     */
    private RMIServerInterface rmiServer;
    /**
     * port for the communication
     */
    private int port;
    /**
     * nickname chosen by the player
     */
    String nickname;
    /**
     * reference to the client view
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
    public void startClient (ClientFactory clientFactory, UserInterfaceFactory userInterfaceFactory) {
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
     * method invoked by the server to send the initial view
     * @param board represents the game board
     * @param pointStack represents scores for common goals
     * @param gridsView represents each player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param pGoalView in this list there are the personal goals fished
     * @param cGoalView in this list there are the common goals fished
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView, HashMap<String,Integer> pGoalView, List<Integer> cGoalView) throws RemoteException{
        int i,j;

        clientView.setBoard(board);
        clientView.setPointStack(pointStack);
        clientView.setGridsview(gridsView);
        clientView.setTilesview(tilesView);
        clientView.setCommonGoalView(cGoalView);
        clientView.setPersonalGoalViews(pGoalView);


        clientView.printStartImage();


        System.out.println("The game is started");

        //stampa board iniziale
        System.out.println("The game is printing the board");

        clientView.printBoard();

        //stampa common goal
        clientView.printCommonGoal();

        //stampa personal goal
        //clientView.printPersonalGoalCard();


    }

    /**
     * method invoked by the server after a successful pick
     * @param board new game board
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
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
        clientView.printBoard();

        System.out.println("Printing " + playername + " tiles");
        clientView.printTiles(playername);

        String[][] grid = clientView.getGridsview().get(playername);
        System.out.println("This is "+playername+" grid");
        clientView.printGrid(playername);

    }

    /**
     * method invoked by the server after a successful topUp
     * @param grid new player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyTopUp(String[][] grid,String[] tilesView,String playername) throws RemoteException{
        int i,j;

        //aggiorna grid view e stampa grid
        for(i=0;i<6;i++){
            for(j=0;j<5;j++){
                clientView.getGridsview().get(playername)[i][j]=grid[i][j];
            }
        }
        System.out.println("This is "+playername+ " grid now");
        clientView.printGrid(playername);


        for(i=0;i< tilesView.length; i++ ){
            clientView.getTilesview().get(playername)[i]=tilesView[i];
        }

    }

    /**
     * method invoked by the server to notify the client of each player's final score
     * @param score final scores
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyScoreBoard (HashMap<String, Integer> score) throws RemoteException{
        clientView.getScoreBoard().putAll(score);
    }

    /**
     * method invoked to print errors or other warnings to the client
     * @param text text
     * @throws RemoteException if something goes wrong with the connection
     */
    public void notifyPopUpView (String text) throws RemoteException{
        clientView.setErrorMessage(text);
        System.out.println(text);
    }


    /**
     * method that returns the player's nickname
     * @return player's nickname
     */
    public String getNickname() {
        return nickname;
    }
}
