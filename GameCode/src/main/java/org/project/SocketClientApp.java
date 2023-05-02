package org.project;

import com.google.gson.Gson;
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

public class SocketClientApp implements ClientInterface{
    public void startClient() throws Exception{
        String hostName = "127.0.0.1";
        int portNumber = 5678;
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
