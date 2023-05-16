package org.project;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.project.Controller.Messages.*;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.IOException;
import java.util.*;

public abstract class AbstractClientApp implements ClientInterface, Runnable {
    protected ClientView clientView = new ClientView();
    protected final Gson gson = new Gson();
    protected final Scanner scanner = new Scanner(System.in);

    private int firstTime = -1;

    public String getUsername() {
        return username;
    }

    public int get_tile_index() {
        return tileIndex;
    }

    private int tileIndex = -1;

    private String username;
    public static final int MAX_TILES = 3;

    public abstract void startClient();

    public abstract void  SendPickMessage();

    public abstract void SendTopUpMessage();

    public abstract void SendQuitMessage();

    public abstract void SendJoinMessage();

    protected abstract void sendMessage(String message);

    public abstract void SendCreateGameMessage();


    protected abstract void processReceivedMessage(String line);

    protected String createPickMessage() {
            List<Coordinates> coordinates = new ArrayList<>();
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            this.username = username;
            System.out.println("Enter the number of tiles you want to pick (up to " + MAX_TILES + "):");
            int numTiles = scanner.nextInt();
            clientView.setNum_tiles(numTiles);
            numTiles = Math.min(numTiles, MAX_TILES); // Ensure the number of tiles doesn't exceed 3

            for (int i = 0; i < numTiles; i++) {
                System.out.println("Enter x coordinate for tile " + (i + 1) + ":");
                int x = scanner.nextInt();
                System.out.println("Enter y coordinate for tile " + (i + 1) + ":");
                int y = scanner.nextInt();
                coordinates.add(new Coordinates(x, y));
            }

            PickMessage message = new PickMessage(username, coordinates);;
            String jsonStr = gson.toJson(message);
            return jsonStr;

        }


    protected String createTopUpMessage() {

            Scanner sc = new Scanner(System.in);
            if (this.firstTime == -1) {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

            }
            if (this.firstTime == -1) {
                System.out.println("Enter the column where you want to place tiles: ");
                this.firstTime = sc.nextInt();
            }

                System.out.println("Enter the index of the tile you want to place: ");
                 this.tileIndex = sc.nextInt();
                if(this.tileIndex < 0 || this.tileIndex >= 5) {
                    System.out.println("Invalid tile index. Please try again.");
                }

                // Check if the tile index is valid


                TopUpMessage message = new TopUpMessage(this.username, this.firstTime, this.tileIndex);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(message);



                // Remove the placed tile from the user's tiles
                return jsonStr;

            }



    protected String createQuitMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        QuitMessage message = new QuitMessage(username);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createCreateGameMessage(boolean connection_type){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter number of players: ");
        int numPlayers = sc.nextInt();
        CreateGame_Message message = new CreateGame_Message(username, connection_type, numPlayers);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createJoinMessage(boolean connection_type){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        JoinMessage message = new JoinMessage(username, connection_type);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }


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
        // Same implementation as before
    }

    public void run() {
        // Same implementation as before
    }

    public void handleTopUpUpdate(UpdateTopUPMsg message) {
        // Same implementation as before
    }

    public void handlePickUpdate(UpdatePickMsg message) {
        // Same implementation as before
    }

    public void handlePopUp(PopUpMsg message) {
        // Same implementation as before
    }

    public void handleScoreUpdate(ScoreBoardMsg message) {
        // Same implementation as before
    }

    public void handleRefreshUpdate(RefreshMsg message) {
        // Same implementation as before
    }
}
