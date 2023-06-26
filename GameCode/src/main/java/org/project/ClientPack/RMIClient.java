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

public class RMIClient extends UnicastRemoteObject implements ConnectionInterface, RMIClientInterface {

    /**
     * reference to the server object
     */

    private int num_tiles;
    private RMIServerInterface rmiServer;
    /**
     * port for the communication
     */
    private int port;
    /**
     * nickname chosen by the player
     */
    String nickname;

    UserInterface userInterface;

    final Scanner stdin= new Scanner(System.in);
    /**
     * reference to the client view
     */
    private ClientView clientView= new ClientView();

    public RMIClient() throws RemoteException {
        this.port= Settings.RMI_PORT;

        startClient();
    }



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
    @Override
    public ClientView getClientView() {
        return clientView;
    }

    @Override
    public boolean get_connection_type() {
        return false;
    }

    @Override
    public void SendJoinMessage(String username, boolean connection_type) {
        //System.out.println("Inserisci nome");

        try {
            rmiServer.sendJoin(username,false, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendCreateGameMessage(String username, boolean connection_type, int numPlayers){
        try {
            rmiServer.sendCreateGame(username,connection_type,numPlayers, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendQuitMessage(String username) {
        try {
            rmiServer.sendQuit(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
        try {
            rmiServer.sendPickRequest(username, coordinates);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendTopUpMessage(String username, int firstTime, int tileIndex) {
        try {
            rmiServer.sendTopUpRequest(username,firstTime,tileIndex);
        } catch (RemoteException e) {
            throw new RuntimeException(e);

    }


}

    @Override
    public String receiveMessage() throws IOException {
        return null;
    }

    @Override
    public String receiveMessage(Message msg) throws IOException {
        return null;
    }

    @Override
    public void setUserInterface(UserInterface client) {
        this.userInterface=client;
    }

    @Override
    public void printMsgChat(String nickname, String message) throws RemoteException {
        //TODO chat


    }

    @Override
    public void notifyInitialGameView(String[][] board, List<Integer> pointStack, HashMap<String, String[][]> gridsView, HashMap<String, String[]> tilesView, HashMap<String, Integer> pGoalView, List<Integer> cGoalView) throws RemoteException {
        RefreshMsg message = new RefreshMsg(board, pointStack, gridsView, tilesView, pGoalView, cGoalView);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);

        /*clientView.setBoard(board);
        clientView.setPointStack(pointStack);
        clientView.setGridsview(gridsView);
        clientView.setTilesview(tilesView);
        clientView.setCommonGoalView(cGoalView);
        clientView.setPersonalGoalViews(pGoalView);
        userInterface.updateClientView(clientView);
        clientView.firePropertyChange("refresh", null);
        //clientView.printCommonGoal();
        */
    }

    @Override
    public void notifyPick(String[][] board, String[] tilesView, String playername) throws RemoteException {

        UpdatePickMsg message = new UpdatePickMsg(playername, tilesView, board);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);


        /*int i,j;

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
        */
    }

    @Override
    public void notifyTopUp(String[][] grid, String[] tilesView, String playername) throws RemoteException {

        UpdateTopUPMsg message = new UpdateTopUPMsg(playername,tilesView, grid);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);
        /*int i,j;

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
        */
    }

    @Override
    public void notifyScoreBoard(HashMap<String, Integer> score) throws RemoteException {
        ScoreBoardMsg message = new ScoreBoardMsg(score);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);

        /*clientView.setScoreBoard(score);
        userInterface.updateClientView(clientView);
        clientView.firePropertyChange("score",clientView);*/
    }

    @Override
    public void notifyPopUpView(String text, int identifier) throws RemoteException {
        PopUpMsg message = new PopUpMsg(text);
        message.setIdentifier(identifier);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);

        /*clientView.setErrorMessage(text);
        clientView.setPopUpIdentifier(identifier);
        userInterface.updateClientView(clientView);
        userInterface.displayMessage(text);
        clientView.firePropertyChange("popupCreate", clientView);*/

    }
    @Override
    public void notifyTurn(String username, boolean move) throws RemoteException {
        PreTurnMsg message = new PreTurnMsg(username, move);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        userInterface.processReceivedMessage(jsonStr);
        //clientView.firePropertyChange("refresh", move);
    }

    @Override
    public void isConnected() throws RemoteException {

    }

    public void closeConnection(){
        try{
            UnicastRemoteObject.unexportObject(this,true);
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
