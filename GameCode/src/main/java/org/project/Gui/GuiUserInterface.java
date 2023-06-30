package org.project.Gui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;
import org.project.Controller.Messages.*;
import org.project.ClientPack.UserInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The GuiUserInterface class is responsible for handling the client's graphical user interface (GUI).
 * Implements the UserInterface interface which defines methods for interacting with the client and
 * receiving notifications from the server.
 */

public class GuiUserInterface implements UserInterface {

    private ClientView clientView;
    private String nickname;
    private String input;
    private int numPlayers;
    private boolean firstAction;
    private boolean serverDownFlag;
    private GuiFx guiCentralController;
    private ConnectionInterface client;

    private String current_Chat;

    private boolean firstTimeChat = true;

    public ConnectionInterface getClient() {
        return client;
    }

    /**
     * Constructor
     * @param clientView reference to the ClientView
     * @param connectionInterface reference to the ConnectionInterface
     */
    public GuiUserInterface(ClientView clientView, ConnectionInterface connectionInterface) {
        this.clientView = clientView;
        this.client = connectionInterface;
        firstAction=false;
    }
    public void setGuiCentralController(GuiFx guifx){
        this.guiCentralController = guifx;
    }
    private String UI = "GUI";

    @Override
    public String getUI() {
        return UI;
    }
    @Override
    public void ShowCObj(String playerName) {

    }

    /**
     * The method initializes the settings needed to launch the graphical user interface
     * and then launches the JavaFX application to display the GUI window.
     */
    public void launcher(){
        GuiFx.setGuiUserInterface(this);
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

    /**
     * The method checks the message received from the server, determines the type of message
     * and handles it, updating the GUI and notifying observers if necessary.
     * @param serverMessage message received from the server
     */
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
                case TURN_UPDATE:
                    handleTurnUpdate(gson.fromJson(serverMessage, PreTurnMsg.class));
                    break;
                case CHAT:
                    handleChat(gson.fromJson(serverMessage, ChatMessage.class));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void ShowPObj(String playerName) {
        
    }

    /**
     * The method takes care of updating the grid view for the corresponding player
     * and notifying observers of the change in tile replenishment.
     * @param message card replenishment update message received from server
     */
    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.updateGridsView(message.getPlayerName(), message.getGrid());
        clientView.firePropertyChange("topup", message.getPlayerName());
    }

    /**
     * The method takes care of adding the chat message to the chat message list
     * and notifying viewers of the new chat message
     * @param message chat message
     */
    public void handleChat(ChatMessage message){
        String receiver = message.getReceiver();
        String sender = message.getUsername();
        if(message.getReceiver().equalsIgnoreCase("broadcast")){
            clientView.getChat().add(message);
        }
        else {
            clientView.getPrivateChats().get(sender).add(message);
        }
        clientView.firePropertyChange("chat", message);
    }

    /**
     * The method takes care of updating the player's tile and board view after a pick
     * and notifying observers of the update
     * @param message update of a player's tiles after a pick
     */
    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        clientView.firePropertyChange("pick", message.getPlayerName());
    }

    /**
     * The method takes care of setting the error message and the identifier of the pop-up in the ClientView class
     * and notifying the observers of the existence of a new pop-up
     * @param message pop-up message
     */
    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        clientView.setPopUpIdentifier(message.getIdentifier());
        clientView.firePropertyChange("popupCreate", clientView);
    }

    /**
     * The method notifies observers that a turn change update has been received
     * @param message change turn
     */
    public void handleTurnUpdate(PreTurnMsg message){
        clientView.firePropertyChange("refresh", clientView);
    }

    /**
     * The method allows to view the updated scores of the players
     * @param message up-to-date player score information
     */
    public void handleScoreUpdate(ScoreBoardMsg message){
        clientView.setScoreBoard(message.getScoreBoard());
        clientView.firePropertyChange("score",clientView);
    }

    /**
     * The method allows to view the updated game data
     * @param message contains updated information about the game board, player tiles, player grids,
     *                score stack and common/personal goal cards
     */
    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());

        clientView.setCommonGoalView(message.getCommonGoalsView());
        clientView.setPersonalGoalViews(message.getPersonalGoalViews());
        if(firstTimeChat){
            fillChatMap();
            firstTimeChat = false;
        }
        clientView.firePropertyChange("refresh", clientView);
    }

    /**
     * The method allows the GuiUserInterface to send a join request to the client selected by the player (rmi/socket),
     * later that client will take care of sending the request to the server
     * @param client selected by the player
     */
    @Override
    public void SendJoinMessage(ConnectionInterface client) {
        client.SendJoinMessage(nickname, true);
    }

    /**
     * The method allows the GuiUserInterface to send a create game request to the client selected by the player (rmi/socket),
     * later that client will take care of sending the request to the server
     * @param client selected by the player
     */
    @Override
    public void SendCreateGameMessage(ConnectionInterface client) {
        try {
            client.SendCreateGameMessage(nickname, client.get_connection_type(), numPlayers);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method allows the GuiUserInterface to send a quit request to the client selected by the player (rmi/socket),
     * later that client will take care of sending the request to the server
     * @param client selected by the player
     */
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
    public void SendChat(ConnectionInterface client) {

    }

    /**
     * The method is called when the server is unreachable or down.
     * The method is used to handle this situation and take appropriate actions in the client.
     */
    @Override
    public void serverDown() {
        if(!serverDownFlag){
            serverDownFlag = true;
            guiCentralController.showBanner();

        }
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

    /**
     * The method allows to update the client view with new tiles from a specific player
     * @param playerName name of the player
     * @param tiles of the player
     */
    @Override
    public void updateClientViewTiles(String playerName, String[] tiles) {
        int i;
        for(i=0;i< tiles.length; i++ ){
            clientView.getTilesview().get(playerName)[i]=tiles[i];
        }
    }

    /**
     * The method allows to correctly view the updated grid
     * @param playerName name of the player
     * @param grid player's grid
     */
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

    public void fillChatMap(){
        for(Map.Entry<String, String[][]> entry : clientView.getGridsview().entrySet()){
            String playername = entry.getKey();
            if(!playername.equals(nickname))
                clientView.getPrivateChats().put(entry.getKey(), new ArrayList<>());
        }
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

    public boolean isFirstAction() {
        return firstAction;
    }

    public void setFirstAction(boolean firstAction) {
        this.firstAction = firstAction;
    }
}