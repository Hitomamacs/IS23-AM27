package org.project.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.ClientView;
import org.project.ConnectionInterface;
import org.project.UserInterface;

public class GuiUserInterface extends Application implements UserInterface {

    private ClientView clientView;
    public GuiUserInterface(ClientView clientView) {

        this.clientView = clientView;
    }

    private String nickname;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
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
        return null;
    }

    @Override
    public void displayMessage(String invalidMessageType) {

    }

    @Override
    public void processReceivedMessage(String serverMessage) {

    }

    @Override
    public void SendJoinMessage(ConnectionInterface client) {

    }

    @Override
    public void SendCreateGameMessage(ConnectionInterface client) {

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
}
