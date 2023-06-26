package org.project.ClientPack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.project.Controller.Messages.*;
import org.project.Model.Coordinates;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliUserInterface implements UserInterface {

    private ConnectionInterface client;

    private Screens screen = Screens.PLAYER_SCREEN;

    public CliUserInterface(ClientView clientView, ConnectionInterface client) {

        this.clientView = clientView;
        this.client = client;
        clientView.addPropertyChangeListener(getPopupListener());
        clientView.addPropertyChangeListener(getRefreshListener());
        clientView.addPropertyChangeListener(getPickListener());
        clientView.addPropertyChangeListener(getTopupListener());
        clientView.addPropertyChangeListener(getTopupGridListener());
    }
    public Screens getScreen(){
        return screen;
    }

    public PropertyChangeListener getPopupListener() {
        return popupListener;
    }

    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }
    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("refresh".equals(evt.getPropertyName())){
                    if(evt.getNewValue() != null){

                        if((boolean)evt.getNewValue()){
                            //clientView.printBoard();
                            //System.out.println();
                            //clientView.printGrid(nickname);
                            printPlayerStuff();
                        }
                        else{
                            printPlayerStuff();
                            //clientView.printGrid(nickname);
                            //System.out.println();
                            //clientView.printTiles(nickname);
                        }
                    }else {//clientView.printBoard();
                        printPlayerStuff(); }
            };
        }
    };

    PropertyChangeListener popupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("popup".equals(evt.getPropertyName())){
                displayMessage(clientView.getPopUpErrorMessage());

            }

        }
    };
    PropertyChangeListener topup_grid_listener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if("topup_grids".equals(evt.getPropertyName())){
                printOtherGrids();

            };
        }
    };

    public PropertyChangeListener getPickListener() {
        return pickListener;
    }
    PropertyChangeListener pickListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("pick".equals(evt.getPropertyName())){
                printPlayerStuff();
            }
        }
    };

    public PropertyChangeListener getTopupListener() {
        return topupListener;
    }

    public PropertyChangeListener getTopupGridListener(){ return topup_grid_listener;}

    PropertyChangeListener topupListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("topup".equals(evt.getPropertyName())){
                    //printGrids((String) evt.getNewValue());
                    printPlayerStuff();
                }

        }
    };

    public PropertyChangeListener getScorelistener() {
        return scorelistener;
    }

    PropertyChangeListener scorelistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("score".equals(evt.getPropertyName())){
                    clientView.printScore();
                }

        }
    };

    PropertyChangeListener turnlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("turn".equals(evt.getPropertyName())){
                    //TODO PRE TURN DOING NOTHING
                }

        }
    };

    public void launcher(){
        new Thread(() -> {
            while (true) {

                String userInput = getInput();
                // Depending on the user input, send a different type of message
                switch (userInput) {
                    case "join":
                        SendJoinMessage(client);
                        break;
                    case "create_game":
                        SendCreateGameMessage(client);
                        break;
                    case "quit":
                        SendQuitMessage(client);
                        break;
                    case "pick":
                        SendPickMessage(client);
                        break;
                    case "topup":
                        SendTopUpMessage(client);
                        break;
                    case "show_p":
                        screen = Screens.GOAL_SCREEN;
                        ShowPObj(getNickname());
                        break;
                    case "show_c":
                        screen = Screens.GOAL_SCREEN;
                        ShowCObj(getNickname());
                        break;
                    case "show_chat":
                        screen = Screens.CHAT_SCREEN;
                        break;
                    case "show_grids":
                        screen = Screens.GRIDS_SCREEN;
                        printOtherGrids();
                        break;
                    case "show_board":
                        screen = Screens.PLAYER_SCREEN;
                        printPlayerStuff();
                        break;
                    case "help":
                        printInstructions();
                        break;
                    default:
                        displayMessage("Invalid message type");
                        break;
                }

            }
        }).start();
    }
    
    private String UI = "CLI";

    private ClientView clientView;

    private void printInstructions(){
        System.out.println("pick: Starts picking phase");
        System.out.println("topup: Starts topup phase");
        System.out.println("quit: Quits game and close application");
        System.out.println("chat: Enters chat screen and waits for messages");
        System.out.println("chat_exit: Leaves chat screen");
        System.out.println("show_c: Shows common goals");
        System.out.println("show_c: Shows personal goal");
        System.out.println("show_board: Shows the board, your grid and your tiles");
        System.out.println("show_grids: Shows other players grid");
        System.out.println("show_chat: Shows the chat");
    }

    private Cli_Images cliImages = new Cli_Images();

    private String nickname;

    private final Scanner scanner = new Scanner(System.in);

    public void displayMessage(String message) {
        System.out.println(message);
    }



    @Override
    public ClientView getClientView() {
        return clientView;
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void CliUserInterface(ClientView clientView) {
        this.clientView = clientView;
    }

    public void updateClientViewTiles(String playerName, String[] tiles){
        int i;
        for(i=0;i< tiles.length; i++ ){
            clientView.getTilesview().get(playerName)[i]=tiles[i];
        }
    }

    @Override
    public void updateClientView(ClientView clientView){
        this.clientView=clientView;

    }

    @Override
    public void updateGridsView(String playerName, String[][] grid) {
        int i;
        for(i=0;i< grid.length; i++ ){
            clientView.getGridsview().get(playerName)[i]=grid[i];
        }
    }


    public void updaateGridsView(String playerName, String[][] grid){
        int i;
        for(i=0;i< grid.length; i++ ){
            clientView.getGridsview().get(playerName)[i]=grid[i];
        }
    }




    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        clientView.updateGridsView(message.getPlayerName(), message.getGrid());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("topup", message.getPlayerName());
        }
        if(screen.equals(Screens.GRIDS_SCREEN)){
            clientView.firePropertyChange("topup_grids", message.getPlayerName());
        }

    }

    @Override
    public void printGrids(String playerName){
        clientView.printGrid(playerName);
    }
    @Override
    public void printTiles(String playerName){
        clientView.printTiles(playerName);
    }
    @Override
    public void printBoard(){
        clientView.printBoard();
    }

    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("pick", message.getPlayerName());
        }
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("popup", null);
        }

    }
    public void handleTurnUpdate(PreTurnMsg message){
        String username = message.getUsername();
        if(message.getMove_Type()){
            boolean type = true;
            //clientView.printBoard();
            //System.out.println();
            //clientView.printGrid(username);
            if(screen.equals(Screens.PLAYER_SCREEN)) {
                clientView.firePropertyChange("refresh", type);
            }
        }
        else{
            boolean type = false;
            //clientView.printGrid(username);
            //System.out.println();
            //clientView.printTiles(username);
            if(screen.equals(Screens.PLAYER_SCREEN)) {
                clientView.firePropertyChange("refresh", type);
            }
        }
    }

    public void handleScoreUpdate(ScoreBoardMsg message){

        clientView.setScoreBoard(message.getScoreBoard());
        if(screen.equals(Screens.SCORE_SCREEN)) {
            clientView.firePropertyChange("score", null);
        }

    }

    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());
        clientView.setCommonGoalView(message.getCommonGoalsView());
        clientView.setPersonalGoalViews(message.getPersonalGoalViews());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("refresh", null);
        }
    }

    public synchronized  void processReceivedMessage(String line) {
        if (line != null && !line.equals("KEEP_ALIVE")) {
            JsonElement jelement = JsonParser.parseString(line).getAsJsonObject();
            JsonObject jsObject = jelement.getAsJsonObject();
            JsonElement id = jsObject.get("ID");
            Gson gson = new Gson();
            MessageID ID = gson.fromJson(id, MessageID.class);
            switch (ID) {
                case TOPUP_UPDATE:
                    handleTopUpUpdate(gson.fromJson(line, UpdateTopUPMsg.class));
                    break;
                case PICK_UPDATE:
                    handlePickUpdate(gson.fromJson(line, UpdatePickMsg.class));
                    break;
                case POP_UP:
                    handlePopUp(gson.fromJson(line, PopUpMsg.class));
                    break;
                case SCORE_UPDATE:
                    handleScoreUpdate(gson.fromJson(line, ScoreBoardMsg.class));
                    break;
                case REFRESH_UPDATE:
                    handleRefreshUpdate(gson.fromJson(line, RefreshMsg.class));
                    break;
                case TURN_UPDATE:
                    handleTurnUpdate(gson.fromJson(line, PreTurnMsg.class));
                default:
                    break;
            }
        }
    }

    @Override
    public void ShowPObj(String playerName) {
        clientView.printPersonalGoalCard(playerName);
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getUI() {
        return UI;
    }

    @Override
    public void ShowCObj(String playerName) {
        clientView.printCommonGoal();

    }

    @Override
    public void SendJoinMessage(ConnectionInterface client) {
        String username = getUsername();
        this.nickname=username;
        client.SendJoinMessage(username, true); //TODO for RMI
    }

    @Override
    public void SendCreateGameMessage(ConnectionInterface client) {
        String username = getUsername();
        this.nickname=username;
        int numPlayers = getNumPlayers();

        try {
            client.SendCreateGameMessage(username, client.get_connection_type(), numPlayers); //TODO for RMI
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendQuitMessage(ConnectionInterface client) {
        client.SendQuitMessage(this.nickname); //TODO for RMI

    }

    @Override
    public void SendPickMessage(ConnectionInterface client) {

        int numTiles = getNumTiles();
        List<Coordinates> coordinates = getCoordinates(numTiles);
        client.SendPickMessage(this.nickname, numTiles, coordinates); //TODO for RMI

    }

    @Override
    public void SendTopUpMessage(ConnectionInterface client) {
        int firstTime = getFirstTime();
        int tileIndex = getTileIndex();
        client.SendTopUpMessage(this.nickname, firstTime, tileIndex); //TODO for RMI


    }

    // User username
    public String getUsername() {
        System.out.println("Enter username: ");
        return scanner.nextLine();
    }

    // Number of tiles for PickMessage
    public int getNumTiles() {
        System.out.println("Enter the number of tiles you want to pick (up to " + 3 + "):");
        int numTiles = Integer.parseInt(scanner.nextLine());
        // Ensuring valid input
        if(numTiles < 0 || numTiles > 3) {
            System.out.println("Invalid input. Please try again.");
            return getNumTiles();  // Retry for correct input
        }
        return numTiles;
    }

    public int getFirstTime(){
        System.out.println("Enter Selected column: ");
        int firstTime = Integer.parseInt(scanner.nextLine());
        return firstTime;
    }

    // Coordinates for PickMessage
    public List<Coordinates> getCoordinates(int numTiles) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < numTiles; i++) {
            System.out.println("Enter x coordinate for tile " + (i + 1) + ":");
            int x = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter y coordinate for tile " + (i + 1) + ":");
            int y = Integer.parseInt(scanner.nextLine());
            coordinates.add(new Coordinates(x, y));
        }
        return coordinates;
    }

    // Column for TopUpMessage
    public int getColumn() {
        System.out.println("Enter the column where you want to place tiles: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Tile index for TopUpMessage
    public int getTileIndex() {
        System.out.println("Enter the index of the tile you want to place: ");
        int tileIndex = Integer.parseInt(scanner.nextLine());
        // Ensuring valid input
        if(tileIndex < 0 || tileIndex >= 5) {
            System.out.println("Invalid tile index. Please try again.");
            return getTileIndex(); // Retry for correct input
        }
        return tileIndex;
    }

    // Number of players for CreateGameMessage
    public int getNumPlayers() {
        System.out.println("Enter number of players: ");
        int numPlayers = Integer.parseInt(scanner.nextLine());
        // You may want to add a validation here as well
        return numPlayers;
    }
    public void printPlayerStuff(){
        clientView.printPlayerStuff(nickname);
    }
    public void printOtherGrids(){
        clientView.printOtherGrids(nickname);
    }
}
