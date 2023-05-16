package org.project;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.project.Controller.Messages.*;
import org.project.Model.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class SocketClientApp implements ClientInterface, Runnable {
    private  ClientView clientView = new ClientView();
    private BufferedReader in;

    private PrintWriter out;

    private final Gson gson = new Gson();






    public void startClient() throws Exception{
        String hostName = "127.0.0.1";
        int portNumber = 5678;
        Scanner sc=new Scanner(System.in);
        try (
                Socket echoSocket = new Socket(hostName, portNumber);

                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);

                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            this.out = out;
            this.in = new BufferedReader(new InputStreamReader((echoSocket.getInputStream())));

            new Thread(this).start();
            processInput(stdIn);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

    }

    private void createPickMessage() {
        Scanner sc = new Scanner(System.in);
        List<Coordinates> coordinates = new ArrayList<>();
        System.out.println("Enter username: ");
        String username = sc.nextLine();

        System.out.println("Enter the number of tiles you want to pick (up to 3):");
        int numTiles = sc.nextInt();
        clientView.setNum_tiles(numTiles);
        numTiles = Math.min(numTiles, 3); // Ensure the number of tiles doesn't exceed 3

        for (int i = 0; i < numTiles; i++) {
            System.out.println("Enter x coordinate for tile " + (i + 1) + ":");
            int x = sc.nextInt();
            System.out.println("Enter y coordinate for tile " + (i + 1) + ":");
            int y = sc.nextInt();
            coordinates.add(new Coordinates(x, y));
        }

        PickMessage message = new PickMessage(username, coordinates);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        out.println(jsonStr);
    }


    private void createTopUpMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();

        System.out.println("Enter the column where you want to place tiles: ");
        int column = sc.nextInt();

        // Get the user's tiles
        String[] userTiles = clientView.getTilesview().get(username);
        long numTiles = Arrays.stream(userTiles).filter(tile -> !tile.equals("N")).count();

        for (int i = 0; i < numTiles; i++) {
            System.out.println("Enter the index of the tile you want to place: ");
            int tileIndex = sc.nextInt();

            // Check if the tile index is valid
            if(tileIndex < 0 || tileIndex >= userTiles.length) {
                System.out.println("Invalid tile index. Please try again.");
                i--; // Ask again for a valid tile index
                continue;
            }

            TopUpMessage message = new TopUpMessage(username, column, tileIndex);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(message);
            out.println(jsonStr);

            // Remove the placed tile from the user's tiles
            String[] updatedUserTiles = new String[userTiles.length - 1];
            for (int j = 0, k = 0; j < userTiles.length; j++) {
                if (j == tileIndex) continue;
                updatedUserTiles[k++] = userTiles[j];
            }
            userTiles = updatedUserTiles;
            clientView.getTilesview().put(username, userTiles);
        }
    }


    private void createQuitMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        QuitMessage message = new QuitMessage(username);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        out.println(jsonStr);
    }
    private void createCreateGameMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter number of players: ");
        int numPlayers = sc.nextInt();
        CreateGame_Message message = new CreateGame_Message(username, true, numPlayers);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        out.println(jsonStr);
    }

    private void createJoinMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        JoinMessage message = new JoinMessage(username, true);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        out.println(jsonStr);
    }

    private void processInput(BufferedReader stdIn) throws IOException {
        while (true) {
            System.out.println("Enter Message Type: ");
            String userInput = stdIn.readLine();
            Message message = null;

            switch (userInput) {
                case "join":
                    createJoinMessage();
                    break;
                case "create_game":
                    createCreateGameMessage();
                    break;
                case "quit":
                    createQuitMessage();
                    break;
                case "pick":
                    createPickMessage();
                    break;
                case "topup":
                    createTopUpMessage();
                    break;
                default:
                    System.out.println("Invalid message type");
                    continue;
            }
        }
    }
    public void run() {
        Gson gson = new Gson();
        String line = null;
        while (true) {
            try {
                line = in.readLine();
                processReceivedMessage(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
    private void processReceivedMessage(String line) {
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
