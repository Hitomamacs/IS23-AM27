package org.project.ClientPack;

import com.google.gson.Gson;
import org.project.Controller.Messages.*;
import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * RMI client
 */

public class RMIClient extends UnicastRemoteObject implements ConnectionInterface, RMIClientInterface {
    /**
     * reference to rmi server
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
     * reference to user interface
     */
    UserInterface userInterface;

    /**
     * reference to the client view
     */
    private ClientView clientView= new ClientView();

    /**
     * constructor
     * @throws RemoteException if something goes wrong with connection
     */
    public RMIClient() throws RemoteException {
        this.port= Settings.RMI_PORT;

        startClient();
    }


    /**
     * method called to initiate the RMI client. This method establishes the connection with the RMI server.
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

        RmiServerHandler rmiServerHandler=new RmiServerHandler(rmiServer,this);
        Thread serverHandlerThread = new Thread(rmiServerHandler);
        serverHandlerThread.start();

        //System.out.println("Connessione stabilita con successo");
    }


    /**
     * getter client view
     * @return client view
     */
    @Override
    public ClientView getClientView() {
        return clientView;
    }

    /**
     * getter connection type
     * @return connection type
     */
    @Override
    public boolean get_connection_type() {
        return false;
    }

    /**
     * method called by the general client in order to be able to send the request to join the game to the rmi server
     * @param username player's nickname
     * @param connection_type connection type
     */
        @Override
        public void SendJoinMessage(String username, boolean connection_type) {
            //System.out.println("Inserisci nome");

            try {
                rmiServer.sendJoin(username,false, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

    /**
     * method called by the general client in order to be able to send the request to create a game to the rmi server
     * @param username player's name
     * @param connection_type connection type
     * @param numPlayers number of players in the game
     */
        @Override
        public void SendCreateGameMessage(String username, boolean connection_type, int numPlayers){
            try {
                rmiServer.sendCreateGame(username,connection_type,numPlayers, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

    /**
     * method called by the general client to be able to send the disconnection request to the rmi server
     * @param username player's name
     */
        @Override
        public void SendQuitMessage(String username) {
            try {
                rmiServer.sendQuit(username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

    /**
     * method called by the general client to be able to send the rmi server the request to take some tiles from the board
     * @param username player's name
     * @param numTiles number of tiles he wants to take
     * @param coordinates coordinates of tiles he wants to take
     */
    @Override
        public void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
            try {
                rmiServer.sendPickRequest(username, coordinates);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

    /**
     * method called by the general client to be able to send the rmi server the request to insert the tiles in its own grid
     * @param username player's name
     * @param firstTime column choosen
     * @param tileIndex index of tile choosen
     */
        @Override
        public void SendTopUpMessage(String username, int firstTime, int tileIndex) {
            try {
                rmiServer.sendTopUpRequest(username,firstTime,tileIndex);
            } catch (RemoteException e) {
                throw new RuntimeException(e);

        }


}
    //TODO javadoc
    @Override
    public String receiveMessage() throws IOException {
        return null;
    }

    //TODO javadoc
    @Override
    public String receiveMessage(Message msg) throws IOException {
        return null;
    }

    /**
     * setter of userinterface
     * @param client type of client
     */
    @Override
    public void setUserInterface(UserInterface client) {
        this.userInterface=client;
    }

    /**
     * method called by the general client to be able to send the rmi server the text message
     * @param nickname nickname of the author of the message
     * @param message message text
     * @throws RemoteException
     */
    @Override
    public void printMsgChat(String nickname, String message) throws RemoteException {
        //TODO chat


    }

        //METHODS CALLED BY SERVER

    /**
     * method called by the server to send all initialized game parameters to the client
     * @param board represents the game board
     * @param pointStack represents scores for common goals
     * @param gridsView represents each player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param pGoalView in this list there are the personal goals fished
     * @param cGoalView in this list there are the common goals fished
     * @throws RemoteException
     */
         @Override
        public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView, HashMap<String, Integer> pGoalView, List<Integer> cGoalView) throws RemoteException {
            clientView.setBoard(board);
            clientView.setPointStack(pointStack);
            clientView.setGridsview(gridsView);
            clientView.setTilesview(tilesView);
            clientView.setCommonGoalView(cGoalView);
            clientView.setPersonalGoalViews(pGoalView);
            userInterface.updateClientView(clientView);
            clientView.firePropertyChange("refresh", null);
            //clientView.printCommonGoal();

        }

    /**
     * method invoked by the server after a successful pick
     * @param board new game board
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
     * @throws RemoteException
     */

        @Override
        public void notifyPick(String[][] board, String[] tilesView, String playername) throws RemoteException {
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

            userInterface.updateClientView(clientView);
            clientView.firePropertyChange("pick", playername);
            //System.out.println("\nPrinting updated board");
            //userInterface.printBoard();
            //userInterface.printTiles(playername);

           /* String[][] grid = clientView.getGridsview().get(playername);
            System.out.println("This is "+playername+" grid");
            clientView.printGrid(playername);*/


        }

    /**
     * method invoked by the server after a successful topUp
     * @param grid new player's grid
     * @param tilesView the chosen tiles are present in this list
     * @param playername player's username
     * @throws RemoteException
     */

        @Override
        public void notifyTopUp(String[][] grid, String[] tilesView, String playername) throws RemoteException {
            int i,j;

            //aggiorna grid view e stampa grid
            for(i=0;i<6;i++){
                for(j=0;j<5;j++){
                    clientView.getGridsview().get(playername)[i][j]=grid[i][j];
                }
            }
            for(i=0;i< tilesView.length; i++ ){
                clientView.getTilesview().get(playername)[i]=tilesView[i];
            }
            userInterface.updateClientView(clientView);
            userInterface.printGrids(playername);
            clientView.firePropertyChange("topup", playername);

        }

    /**
     * method invoked by the server to notify the client of each player's final score
     * @param score final scores
     * @throws RemoteException
     */

        @Override
        public void notifyScoreBoard(HashMap<String, Integer> score) throws RemoteException {
            clientView.setScoreBoard(score);
            userInterface.updateClientView(clientView);
            clientView.firePropertyChange("score",clientView);
        }

    /**
     * method invoked to print errors or other warnings to the client
     * @param text text
     * @param identifier
     * @throws RemoteException
     */

        @Override
        public void notifyPopUpView(String text, int identifier) throws RemoteException {
            clientView.setErrorMessage(text);
            clientView.setPopUpIdentifier(identifier);
            userInterface.updateClientView(clientView);
            userInterface.displayMessage(text);
            clientView.firePropertyChange("popupCreate", clientView);

        }

    //  TODO javadoc
    /**
     *
     * @param username
     * @param move
     * @throws RemoteException
     */
    @Override
    public void notifyTurn(String username, boolean move) throws RemoteException {
        PreTurnMsg message = new PreTurnMsg(username, move);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);
        //clientView.firePropertyChange("refresh", move);
    }

    /**
     * method called by the server to accept that the client is always connected.
     * When the exception returns it understands that the client has disconnected and therefore
     * removes the player from the list of online players
     * @throws RemoteException when something goes wrong with the connection
     */

    @Override
    public void isConnected() throws RemoteException {

    }

    /**
     * method called by ServerRmiHandler when server crush.
     * This method close the connection and the client.
     */
    public void closeConnection(){
        try{
            UnicastRemoteObject.unexportObject(this,true);
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
