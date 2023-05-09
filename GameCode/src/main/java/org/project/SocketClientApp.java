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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocketClientApp implements ClientInterface, Runnable {
    private  ClientView clientView = new ClientView();
    private BufferedReader in;

    private String username;
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
            this.in = new BufferedReader(new InputStreamReader((echoSocket.getInputStream())));
            new Thread(this).start();
            while (true) {
                System.out.println("Enter Message Type: ");
                String userInput;
                userInput = stdIn.readLine();
                switch (userInput) {
                    case "create_game" -> {
                        boolean connectionType;
                        int numPlayers;
                        System.out.println("Enter username: ");
                        username = stdIn.readLine();

                        System.out.println("Enter number of players: ");
                        numPlayers = sc.nextInt();
                        LoginMessage message = new LoginMessage(username, true, numPlayers);
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                        break;
                    }
                    case "login" -> {
                        System.out.println("Enter username: ");
                        username = stdIn.readLine();
                        LoginMessage message = new LoginMessage(username, true);
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                        break;
                    }
                    case "quit" -> {
                        QuitMessage message = new QuitMessage(username);
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                        break;
                    }
                    case "pick" -> {
                        Gson gson = new Gson();
                        int x, y, num_tiles;
                        List<Coordinates> Coordinates = new ArrayList<>();
                        System.out.println("Enter number of tiles: ");
                        num_tiles = sc.nextInt();
                        for(int i = 0; i <= num_tiles - 1; i++) {
                            System.out.println("Enter x coordinate: ");
                            x = sc.nextInt();
                            System.out.println("Enter y coordinate: ");
                            y = sc.nextInt();
                            Coordinates.add(new Coordinates(x, y));
                        }
                        PickMessage message = new PickMessage(username, Coordinates);
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                    }

                    case "topup" -> {
                        Gson gson = new Gson();
                        int column, tileIndex;
                        System.out.println("Enter column: ");
                        column = sc.nextInt();
                        System.out.println("Enter tile index: ");
                        tileIndex = sc.nextInt();
                        TopUpMessage message = new TopUpMessage(username, column, tileIndex);
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                    }


                }

            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

    }
    public void run() {
        Gson gson = new Gson();
        String line = null;
        while (true) {
            try {
                line = in.readLine() ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



                JsonElement jelement = JsonParser.parseString(line).getAsJsonObject();
                JsonObject jsObject = jelement.getAsJsonObject();
                JsonElement id = jsObject.get("messageID");
                MessageID ID = gson.fromJson(id, MessageID.class);
                switch (ID) {
                    case TOPUP_UPDATE -> this.handleTopUpUpdate(gson.fromJson(line, UpdateTopUPMsg.class));
                    case PICK_UPDATE -> this.handlePickUpdate(gson.fromJson(line, UpdatePickMsg.class));
                    case POP_UP -> this.handlePopUp(gson.fromJson(line, PopUpMsg.class));
                    case SCORE_UPDATE -> this.handleScoreUpdate(gson.fromJson(line, ScoreBoardMsg.class));
                    case REFRESH_UPDATE -> this.handleRefreshUpdate(gson.fromJson(line, RefreshMsg.class));
                }
                ;

            }

    }

    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.getGridsview().put(message.getPlayerName(), message.getGrid());
        String[][] grid = clientView.getGridsview().get(message.getPlayerName());
        for (int i = 0; i<6; i++ ){
            for (int j = 0; j<5; j++){
                if(message.getGrid()[i][j] != null){
                    System.out.print(grid[i][j]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.getTilesview().put(message.getPlayerName(), message.getTiles());
        System.out.println("\nPrinting updated board");
        for(int i = 0; i<message.getBoard().length; i++){
            for(int j = 0; j<message.getBoard()[0].length; j++){
                if(message.getBoard()[i][j] != null){
                    System.out.print(message.getBoard()[i][j]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println("\nPrinting " + message.getPlayerName() + " updated tiles");
        String[] tiles = clientView.getTilesview().get(message.getPlayerName());
        for (String tile : tiles) {
            System.out.println(tile + " ");
        }
        System.out.println();
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        System.out.println(message.getText());
    }

    public void handleScoreUpdate(ScoreBoardMsg message){
        clientView.setScoreBoard(message.getScoreBoard());
        ;
    }

    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());
        for(int i = 0; i<message.getBoard().length; i++){
            for(int j = 0; j<message.getBoard()[0].length; j++){
                if(message.getBoard()[i][j] != null){
                    System.out.print(message.getBoard()[i][j]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("Printing Just Board (Refresh message), Start Game");
        ;
    }
}
