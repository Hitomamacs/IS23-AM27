package org.project;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.project.Controller.Messages.*;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class SocketClientApp extends AbstractClientApp {
    private  ClientView clientView = new ClientView();

    private Timer keepAlive;

    private static final int KEEP_ALIVE_INTERVAL = 5 * 1000;  // in milliseconds


    private final Scanner scanner = new Scanner(System.in);



    private static final int MAX_TILES = 3;

    private long lastKeepAliveReceivedTime;
    private Timer keepAliveTimer;
    private BufferedReader in;

    private PrintWriter out;

    private final Gson gson = new Gson();






    public void startClient() {
        try {
            Socket echoSocket = new Socket(Settings.SERVER_NAME, Settings.SOCKET_PORT);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            new Thread(this).start();
            startKeepAlive();

            processInput();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + Settings.SERVER_NAME);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + Settings.SERVER_NAME);
        }
    }

    private void startKeepAlive() {
        Timer keepAlive = new Timer();
        keepAlive.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.println("KEEP_ALIVE");
            }
        }, 0, 1000);
    }

    protected void sendMessage(String message) {
        out.println(message);
    }

    public void SendPickMessage() {
        sendMessage(createPickMessage());
    }


    public void SendTopUpMessage() {

        // Get the user's tiles
        String[] userTiles = clientView.getTilesview().get(getUsername());
        long numTiles = Arrays.stream(userTiles).filter(tile -> !tile.equals("N")).count();
        for (int i = 0; i < numTiles; i++) {
            sendMessage(createTopUpMessage());
        }
        resetFirstTime();
    }


    public void SendQuitMessage() {
        sendMessage(createQuitMessage());
    }
    public void SendCreateGameMessage() {
        sendMessage(createCreateGameMessage(true));
    }

    public void SendJoinMessage() {
        sendMessage(createJoinMessage(true));
    }

    @Override
    protected void processInput() throws IOException {
        while (true) {
            System.out.println("Enter Message Type: ");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "join":
                    SendJoinMessage();
                    break;
                case "create_game":
                    SendCreateGameMessage();
                    break;
                case "quit":
                    SendQuitMessage();
                    break;
                case "pick":
                    SendPickMessage();
                    break;
                case "topup":
                    SendTopUpMessage();
                    break;
                default:
                    System.out.println("Invalid message type");
                    continue;
            }
        }
    }
    public void run() {
        Gson gson = new Gson();
        String line;
        startKeepAliveCheck();
        while (true) {
            try {
                line = in.readLine();
                if (line!= null && line.equals("KEEP_ALIVE")) {
                    lastKeepAliveReceivedTime = System.currentTimeMillis();
                    continue;
                }

                processReceivedMessage(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void startKeepAliveCheck() {
        keepAliveTimer = new Timer();
        keepAliveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastKeepAliveReceivedTime > KEEP_ALIVE_INTERVAL) {
                    disconnect();  // replace this with your disconnect function
                }
            }
        }, KEEP_ALIVE_INTERVAL, KEEP_ALIVE_INTERVAL);
    }

    private void disconnect() {
        // Implement  disconnect function here!!!!
        System.out.println("Disconnected from server");
    }
    public synchronized  void processReceivedMessage(String line) {
        if (line != null) {
            JsonElement jelement = JsonParser.parseString(line).getAsJsonObject();
            JsonObject jsObject = jelement.getAsJsonObject();
            JsonElement id = jsObject.get("ID");
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

                    default:
                        break;
            }
        }
    }
    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.updateGridsView(message.getPlayerName(), message.getGrid());
        clientView.printGrid(message.getPlayerName());
    }

    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        clientView.printBoard();
        clientView.printTiles(message.getPlayerName());
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        System.out.println(message.getText());
    }

    public void handleScoreUpdate(ScoreBoardMsg message){
        clientView.setScoreBoard(message.getScoreBoard());
    }

    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());
        clientView.printBoard();
        System.out.println("Printing Just Board (Refresh message), Start Game");
    }



}
