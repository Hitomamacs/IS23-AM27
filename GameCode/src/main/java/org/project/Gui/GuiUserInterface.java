package org.project.Gui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.ClientView;
import org.project.ConnectionInterface;
import org.project.Controller.Messages.*;
import org.project.UserInterface;

import java.io.IOException;
import java.rmi.RemoteException;

public class GuiUserInterface  implements UserInterface {

    private ClientView clientView;
    private String nickname;
    private String input;
    private int numPlayers;

    private ConnectionInterface client;
    private GuiController guiController;

    public ConnectionInterface getClient() {
        return client;
    }
    public GuiUserInterface(ClientView clientView, ConnectionInterface connectionInterface) {

        this.clientView = clientView;
        this.client = connectionInterface;


    }

    private String UI = "GUI";

    @Override
    public String getUI() {
        return UI;
    }

    @Override
    public void ShowCObj(String playerName) {

    }

    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Parent root = loader.load();

            WelcomeController welcomeController = loader.getController();
            welcomeController.setGuiUserInterface(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            guiController=new GuiController(primaryStage);
            welcomeController.setGuiController(guiController);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    */


    public void launcher(){
        GuiFx.setguiUserInterface(this);
        GuiFx.setClientView(clientView);
        Application.launch(GuiFx.class);
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
        if (serverMessage != null && !serverMessage.equals("KEEP_ALIVE")) {
            JsonElement jelement = JsonParser.parseString(serverMessage).getAsJsonObject();
            JsonObject jsObject = jelement.getAsJsonObject();
            JsonElement id = jsObject.get("ID");
            Gson gson = new Gson();
            MessageID ID = gson.fromJson(id, MessageID.class);
            switch (ID) {
                case TOPUP_UPDATE:
                    handleTopUpUpdate(gson.fromJson(serverMessage, UpdateTopUPMsg.class));
                    break;
                case PICK_UPDATE:
                    handlePickUpdate(gson.fromJson(serverMessage, UpdatePickMsg.class));
                    break;
                case POP_UP:
                    handlePopUp(gson.fromJson(serverMessage, PopUpMsg.class));
                    break;
                case SCORE_UPDATE:
                    handleScoreUpdate(gson.fromJson(serverMessage, ScoreBoardMsg.class));
                    break;
                case REFRESH_UPDATE:
                    handleRefreshUpdate(gson.fromJson(serverMessage, RefreshMsg.class));
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void ShowPObj(String playerName) {
        
    }

    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.updateGridsView(message.getPlayerName(), message.getGrid());
        clientView.firePropertyChange("topup", message.getPlayerName());
    }
    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        clientView.firePropertyChange("pick", message.getPlayerName());
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        clientView.setPopUpIdentifier(message.getIdentifier());
        clientView.firePropertyChange("popupCreate", clientView);
    }

    public void handleScoreUpdate(ScoreBoardMsg message){
        clientView.setScoreBoard(message.getScoreBoard());
    }

    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());
        clientView.firePropertyChange("refresh", clientView);
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
        client.SendQuitMessage(this.nickname);

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
