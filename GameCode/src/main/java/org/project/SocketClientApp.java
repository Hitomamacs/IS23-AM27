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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientApp implements ClientInterface, Runnable{

    private String hostName;
    private int portNumber;

    private Scanner in;

   private  Socket echoSocket;

    private ClientView clientView;


    public SocketClientApp() throws IOException {
        String hostName = "127.0.0.1";
        clientView = new ClientView();
        int portNumber = 5678;
        echoSocket = new Socket(hostName, portNumber);
        try {
            this.in = new Scanner(echoSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


    public void run() {
        Gson gson = new Gson();
        while (true) {
            String line = in.nextLine();
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
        ;
    }

    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.getTilesview().put(message.getPlayerName(), message.getTiles());
        for(int i = 0; i<message.getBoard().length; i++){
            for(int j = 0; j<message.getBoard()[0].length; j++){
                if(message.getBoard()[i][j] != null){
                    System.out.println(message.getBoard()[i][j]);
                    System.out.println(" ");
                }
            }
            System.out.println("\n");
        }
        ;
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        ;
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
        ;
    }



    public void startClient() throws Exception{
        Scanner sc=new Scanner(System.in);
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            while (true) {
                System.out.println("Enter Message Type: ");
                String userInput;
                userInput = stdIn.readLine();
                switch (userInput) {
                    case "login" -> {
                        String username;
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
                    case "quit" -> {
                        String username;
                        System.out.println("Enter username: ");
                        username = stdIn.readLine();
                        QuitMessage message = new QuitMessage(username);
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                        break;
                    }
                    case "pick" -> {
                        Gson gson = new Gson();
                        String username;
                        System.out.println("Enter username: ");
                        username = stdIn.readLine();
                        int x, y;
                        List<Coordinates> Coordinates = new ArrayList<>();
                        System.out.println("Enter x coordinate: ");
                        x = sc.nextInt();
                        System.out.println("Enter y coordinate: ");
                        y = sc.nextInt();
                        Coordinates.add(new Coordinates(x, y));
                        PickMessage message = new PickMessage(username, Coordinates);
                        String jsonStr = gson.toJson(message);
                        out.println(jsonStr);
                    }

                    case "topup" -> {
                        Gson gson = new Gson();
                        String username;
                        System.out.println("Enter username: ");
                        username = stdIn.readLine();
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
    public void sendLoginRequest(String nickname){

    }
    public void sendMessage(String message){

    }
    public void sendPickRequest(){

    }
    public void sendTopUpRequest(){

    }
}
