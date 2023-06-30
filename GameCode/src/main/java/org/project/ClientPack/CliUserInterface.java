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
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a command-line user interface for a client.
 * It implements the `UserInterface` interface and provides methods for handling user input, displaying messages,
 * and interacting with the client and server.
 *
 * The class uses several properties and listeners to handle various events and updates from the client and view.
 * It also includes methods for sending different types of messages to the server based on user input.
 */
public class CliUserInterface implements UserInterface {

    /**
     * boolean used to prevent a player from creating:game and join from the same client or a player from joining twice
     */
    private boolean firstAction;
    /**
     * reference to the client
     */
    private ConnectionInterface client;

    /**
     * screens
     */
    private Screens screen = Screens.PLAYER_SCREEN;

    /**
     * boolean used to close the connection when the server is crushed
     */
    private boolean serverDownFlag;

    private boolean firstTimeChat = true;


    /**
     * player's username
     */
    private String nickname;

    /**
     * scanner
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new `CliUserInterface` object with the specified `ClientView` and `ConnectionInterface` objects.
     * It sets up listeners for various events and updates from the `ClientView`.
     * @param clientView reference to client view
     * @param client reference to client
     */
    public CliUserInterface(ClientView clientView, ConnectionInterface client) {

        this.clientView = clientView;
        this.client = client;
        firstAction=false;
        clientView.addPropertyChangeListener(getPopupListener());
        clientView.addPropertyChangeListener(getRefreshListener());
        clientView.addPropertyChangeListener(getPickListener());
        clientView.addPropertyChangeListener(getTopupListener());
        clientView.addPropertyChangeListener(getTopupGridListener());
        clientView.addPropertyChangeListener(getChatListener());
        clientView.addPropertyChangeListener(getScorelistener());
    }
    public Screens getScreen(){
        return screen;
    }

    public PropertyChangeListener getPopupListener() {
        return popupListener;
    }

    public PropertyChangeListener getChatListener(){ return chatListener; }

    /**
     * PropertyChangeListener implementation for handling chat events.
     */
    PropertyChangeListener chatListener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if("chat".equals(evt.getPropertyName())){
                ChatMessage chatMessage = (ChatMessage) evt.getNewValue();
                String receiver = chatMessage.getReceiver();
                String sender = chatMessage.getUsername();
                if(clientView.getCurrentChat().equalsIgnoreCase("broadcast")){
                    if(receiver.equalsIgnoreCase("broadcast")) {
                        clientView.printChatLast(nickname);
                    }
                }
                else {
                    if(clientView.getCurrentChat().equalsIgnoreCase(sender)) {
                        if(receiver.equalsIgnoreCase(nickname)){
                            clientView.printPrivateChatLast(nickname, sender);
                        }
                    }
                }

            }

        }
    };

    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }

    /**
     * PropertyChangeListener implementation for handling refresh events.
     */
    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("refresh".equals(evt.getPropertyName())){
                    printPlayerStuff();
                    if(firstTimeChat){
                        fillChatMap();
                    }
                    firstTimeChat = false;
            };
        }
    };

    /**
     * PropertyChangeListener implementation for handling popup events.
     */
    PropertyChangeListener popupListener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if("popup".equals(evt.getPropertyName())){
                displayMessage(clientView.getPopUpErrorMessage());

            }

        }
    };

    /**
     * PropertyChangeListener implementation for handling topup_grids events.
     */
    PropertyChangeListener topup_grid_listener = new PropertyChangeListener() {

        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
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

    /**
     * PropertyChangeListener implementation for handling pick events.
     */
    PropertyChangeListener pickListener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
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

    /**
     * PropertyChangeListener implementation for handling topup events.
     */
    PropertyChangeListener topupListener = new PropertyChangeListener() {

        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
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

    /**
     * PropertyChangeListener implementation for handling score events.
     */
    PropertyChangeListener scorelistener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("score".equals(evt.getPropertyName())){
                    clientView.printScore();
                }
        }
    };

    /**
     * PropertyChangeListener implementation for handling turn events.
     */
    PropertyChangeListener turnlistener = new PropertyChangeListener() {
        /**
         * Called when a property change event is fired.
         *
         * @param evt The property change event.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

                if("turn".equals(evt.getPropertyName())){
                    //TODO PRE TURN DOING NOTHING
                }

        }
    };

    /**
     * Starts a new thread to listen for user input and perform corresponding actions.
     */
    public void launcher(){
        new Thread(() -> {
            while (true) {

                String userInput = getInput();
                // Depending on the user input, send a different type of message
                switch (userInput) {
                    case "join":
                        if(firstAction==false){
                            SendJoinMessage(client);
                            if(clientView.getPopUpErrorMessage() != null && (clientView.getPopUpErrorMessage().contains("Successfully created game") && !clientView.getPopUpErrorMessage().contains("Succesfully joined game"))){
                                firstAction=true;
                            }
                        }else{
                            displayMessage("Hai già fatto join/create_game");
                        }
                        break;
                    case "create_game":
                        if(firstAction==false){
                            SendCreateGameMessage(client);
                            if(clientView.getPopUpErrorMessage() != null && clientView.getPopUpErrorMessage().contains("Successfully created game") && !clientView.getPopUpErrorMessage().contains("Succesfully joined game")){
                                firstAction=true;
                            }
                        }else{
                            displayMessage("Hai già fatto join/create_game");
                        }
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
                        clientView.setCurrent_Chat("broadcast");
                        clientView.printChat(nickname);
                        SendChat(client);
                        break;
                    case "show_private_chat":
                        if(firstTimeChat){
                            fillChatMap();
                        }
                        firstTimeChat = false;
                        screen = Screens.CHAT_SCREEN;
                        handlePrivateChat(client);
                        break;
                    case "show_grids":
                        screen = Screens.GRIDS_SCREEN;
                        printOtherGrids();
                        break;
                    case "show_board":
                        screen = Screens.PLAYER_SCREEN;
                        printPlayerStuff();
                        System.out.println(clientView.getPopUpErrorMessage());
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

    /**
     * Prints out the instructions for different commands.
     */
    private void printInstructions(){
        System.out.println("pick: Starts picking phase");
        System.out.println("topup: Starts topup phase");
        System.out.println("quit: Quits game and close application");
        System.out.println("chat: Enters chat screen and waits for messages");
        System.out.println("show_c: Shows common goals");
        System.out.println("show_p: Shows personal goal");
        System.out.println("show_board: Shows the board, your grid and your tiles");
        System.out.println("show_grids: Shows other players grid");
        System.out.println("show_chat: Shows the chat");
        System.out.println("show_private_chat: Shows private chat");
    }

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed
     */
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

    /**
     * Updates the client view.
     *
     * @param clientView the new client view to be set
     */
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


    /**
     * Handles the update message for a top-up action.
     *
     * @param message the update message containing the player's name, tiles, and grid
     */
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

    /**
     * Prints the grid of a specific player.
     *
     * @param playerName the name of the player whose grid is to be printed
     */
    @Override
    public void printGrids(String playerName){
        clientView.printGrid(playerName);
    }

    /**
     * Prints the tiles of a specific player.
     *
     * @param playerName the name of the player whose tiles are to be printed
     */
    @Override
    public void printTiles(String playerName){
        clientView.printTiles(playerName);
    }

    /**
     * Prints the board.
     */
    @Override
    public void printBoard(){
        clientView.printBoard();
    }

    /**
     * Handles the update message for the pick phase. it sets board and updates tiles view.
     *
     * @param message the update message containing the board and player information
     */
    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("pick", message.getPlayerName());
        }
    }

    /**
     * Handles the pop-up message received from the server. It set errorMessage in the view.
     *
     * @param message the pop-up message containing the error text
     */
    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        if(screen.equals(Screens.PLAYER_SCREEN)) {
            clientView.firePropertyChange("popup", null);
        }

    }

    /**
     * Handles the turn update message received from the server.
     *
     * @param message the turn update message containing the username and move type
     */
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

    /**
     * Handles the score update message received from the server.
     *
     * @param message the score update message containing the updated score
     */
    public void handleScoreUpdate(ScoreBoardMsg message){

        clientView.setScoreBoard(message.getScoreBoard());
        if(screen.equals(Screens.SCORE_SCREEN)) {
            clientView.firePropertyChange("score", null);
        }

    }

    /**
     * Handles the chat message update received from the server.
     *
     * @param message the chat message to be added to the chat view
     */
    public void handleChatUpdate(ChatMessage message){
        String sender = message.getUsername();

        if(message.getReceiver().equalsIgnoreCase("broadcast")){
            clientView.getChat().add(message);
        }
        else{
            clientView.getPrivateChats().get(sender).add(message);
        }
        if(screen.equals(Screens.CHAT_SCREEN)){
            clientView.firePropertyChange("chat", message);
        }
    }

    /**
     * Handles the refresh update received from the server. It sets all player stuff.
     *
     * @param message the refresh message containing updated game data
     */
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

    /**
     * Processes the received message from the server.
     *
     * @param line the received message as a string
     */
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
                    screen = Screens.SCORE_SCREEN;
                    handleScoreUpdate(gson.fromJson(line, ScoreBoardMsg.class));
                    break;
                case REFRESH_UPDATE:
                    handleRefreshUpdate(gson.fromJson(line, RefreshMsg.class));
                    break;
                case TURN_UPDATE:
                    handleTurnUpdate(gson.fromJson(line, PreTurnMsg.class));
                    break;
                case CHAT:
                    handleChatUpdate(gson.fromJson(line, ChatMessage.class));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Displays the personal goal card for the specified player.
     *
     * @param playerName the name of the player
     */
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

    /**
     * Sends chat messages to other players.
     *
     * @param client the connection interface used for sending messages
     */
    @Override
    public void SendChat(ConnectionInterface client){

        String text = getText();
        while(!(text.equals("exit"))){
            client.SendChatMessage(nickname, text);
            text = getText();
        }
        screen = Screens.PLAYER_SCREEN;
        clientView.printPlayerStuff(nickname);
    }
    public void SendChatPrivate(ConnectionInterface client, String receiver){
        String text = getText();
        while(!(text.equals("exit"))){
            client.SendChatMessage(nickname, text, receiver);
            clientView.getPrivateChats().get(receiver).add(new ChatMessage(nickname, text, receiver));
            clientView.printPrivateChatLast(nickname, receiver);
            text = getText();
        }
        screen = Screens.PLAYER_SCREEN;
        clientView.printPlayerStuff(nickname);

    }
    public void handlePrivateChat(ConnectionInterface client){
        System.out.println("Who do you want to chat with?");
        for(Map.Entry<String, List<ChatMessage>> entry : clientView.getPrivateChats().entrySet()){
            System.out.println(entry.getKey() + "  ");
        }
        System.out.println();
        String name = getInput();
        while(!clientView.findChat(name)){
            System.out.println("Invalid user");
            name = getInput();
        }
        clientView.setCurrent_Chat(name);
        clientView.printPrivateChat(nickname, name);
        SendChatPrivate(client, name);
    }
    public void fillChatMap(){
        for(Map.Entry<String, String[][]> entry : clientView.getGridsview().entrySet()){
            String playername = entry.getKey();
            if(!playername.equals(nickname))
                clientView.getPrivateChats().put(entry.getKey(), new ArrayList<>());
        }
    }
    /**
     * Handles the case when the server is unreachable.
     * Displays an error message, waits for 10 seconds, and then exits the application.
     */
    @Override
    public void serverDown() {
        if(!(serverDownFlag)){
            System.out.println("Server is unreachable");
            serverDownFlag = true;
            System.out.println("Closing application in 10 seconds");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(-1);
        }
    }

    /**
     * Sends a join message to the server using the provided client connection (rmi/socket).
     * The method prompts the user to enter a username and sends the join message with the username.
     *
     * @param client The client connection interface used to send the join message.
     */
    @Override
    public void SendJoinMessage(ConnectionInterface client) {
        String username = getUsername();
        this.nickname=username;
        client.SendJoinMessage(username, true); //TODO for RMI
    }

    /**
     * Sends a create game message to the server using the provided client connection (rmi/socket).
     * The method prompts the user to enter a username and the number of players for the game.
     * It sends the create game message with the username and number of players.
     * After sending the message, the nickname field is updated with the chosen username.
     *
     * @param client the client connection interface used to send the create game message.
     * @throws RuntimeException if an error occurs during the remote method invocation.
     */
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

    /**
     * Sends a quit message to the server using the provided client connection (rmi/socket).
     * The method sends the quit message with the current nickname to indicate the player's intention to quit the game.
     *
     * @param client The client connection interface used to send the quit message.
     */
    @Override
    public void SendQuitMessage(ConnectionInterface client) {
        client.SendQuitMessage(this.nickname); //TODO for RMI

    }

    /**
     * Sends a pick message to the server using the provided client connection (rmi/socket).
     * The method sends the pick message with the current nickname, the number of tiles to pick, and the list of coordinates
     * indicating the positions of the tiles to be picked.
     *
     * @param client The client connection interface used to send the pick message.
     */
    @Override
    public void SendPickMessage(ConnectionInterface client) {

        int numTiles = getNumTiles();
        List<Coordinates> coordinates = getCoordinates(numTiles);
        client.SendPickMessage(this.nickname, numTiles, coordinates); //TODO for RMI

    }

    /**
     * Sends a top-up message to the server using the provided client connection (rmi/socket).
     * The method sends the top-up message with the current nickname, the flag indicating if it's the first top-up,
     * and the index of the tile to be topped up.
     *
     * @param client The client connection interface used to send the top-up message.
     */
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
    /**
     * Prompts the user to enter the number of tiles they want to pick.
     * The method validates the input and ensures that it is within the valid range of 0 to 3.
     * If an invalid input is provided, the user is prompted again until a valid input is entered.
     *
     * @return The number of tiles the user wants to pick.
     */
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

    /**
     * Prompts the user to enter the selected column for the top-up action.
     *
     * @return The selected column for the top-up action.
     */
    public int getFirstTime(){
        System.out.println("Enter Selected column: ");
        int firstTime = Integer.parseInt(scanner.nextLine());
        return firstTime;
    }

    // Coordinates for PickMessage
    /**
     * Prompts the user to enter the coordinates for each tile in the PickMessage.
     *
     * @param numTiles The number of tiles to pick.
     * @return The list of coordinates for each tile.
     */
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
    /**
     * Prompts the user to enter the column where they want to place tiles in the TopUpMessage.
     *
     * @return The column chosen by the user.
     */
    public int getColumn() {
        System.out.println("Enter the column where you want to place tiles: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Prompts the user to enter the index of the tile they want to place in the TopUpMessage.
     *
     * @return The index of the tile chosen by the user.
     */
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
    /**
     * Prompts the user to enter the number of players for the CreateGameMessage.
     *
     * @return The number of players chosen by the user.
     */
    public int getNumPlayers() {
        System.out.println("Enter number of players: ");
        int numPlayers = Integer.parseInt(scanner.nextLine());
        // You may want to add a validation here as well
        //num player must be a number between 2 and 4
        if(numPlayers < 2 || numPlayers > 4) {
            System.out.println("Invalid number of players. Please try again.");
            return getNumPlayers(); // Retry for correct input
        }

        return numPlayers;
    }

    public String getText(){
        return scanner.nextLine();
    }

    /**
     * Prints the player's information, including their grid and tiles.
     */
    public void printPlayerStuff(){
        clientView.printPlayerStuff(nickname);
    }

    /**
     * Prints the grids of other players in the game.
     */
    public void printOtherGrids(){
        clientView.printOtherGrids(nickname);
    }

}
