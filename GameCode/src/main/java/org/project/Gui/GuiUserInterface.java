package org.project.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.ClientView;
import org.project.ConnectionInterface;
import org.project.UserInterface;

import java.rmi.RemoteException;

public class GuiUserInterface extends Application implements UserInterface {

    private ClientView clientView;
    private String nickname;
    private String input;
    private int numPlayers;

   /* public GuiUserInterface(ClientView clientView) {

        this.clientView = clientView;
    }*/


    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Parent root = loader.load();

            WelcomeController welcomeController = loader.getController();
            welcomeController.setGuiUserInterface(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch();
    }

    @Override
    public ClientView getClientView() {
        return clientView;
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public void displayMessage(String invalidMessageType) {

    }

    @Override
    public void processReceivedMessage(String serverMessage) {

    }

    @Override
    public void SendJoinMessage(ConnectionInterface client) {
        client.SendJoinMessage(nickname, true); //TODO for RMI
    }

    @Override
    public void SendCreateGameMessage(ConnectionInterface client) {

        try {
            client.SendCreateGameMessage(nickname, client.get_connection_type(), numPlayers); //TODO for RMI
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void SendQuitMessage(ConnectionInterface client) {

    }

    @Override
    public void SendPickMessage(ConnectionInterface client) {

    }

    @Override
    public void SendTopUpMessage(ConnectionInterface client) {

    }

    @Override
    public void printBoard() {

    }

    @Override
    public void printTiles(String playername) {

    }

    @Override
    public void printGrids(String playername) {

    }

    @Override
    public void updateClientViewTiles(String playerName, String[] tiles) {
        int i;
        for(i=0;i< tiles.length; i++ ){
            clientView.getTilesview().get(playerName)[i]=tiles[i];
        }
    }

    @Override
    public void updateGridsView(String playerName, String[][] grid) {
        int i;
        for(i=0;i< grid.length; i++ ){
            clientView.getGridsview().get(playerName)[i]=grid[i];
        }
    }

    @Override
    public void updateClientView(ClientView clientView) {
        this.clientView=clientView;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
