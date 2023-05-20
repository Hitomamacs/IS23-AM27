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

public abstract class AbstractClientApp implements Runnable {
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



    public abstract void  SendPickMessage();

    public abstract void SendTopUpMessage();

    public abstract void SendQuitMessage();

    public abstract void SendJoinMessage();

    protected abstract void sendMessage(String message);

    public abstract void SendCreateGameMessage();




    protected String createPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
        this.username = username;
        clientView.setNum_tiles(numTiles);
        numTiles = Math.min(numTiles, MAX_TILES); // Ensure the number of tiles doesn't exceed 3

        PickMessage message = new PickMessage(username, coordinates);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createTopUpMessage(String username, int firstTime, int tileIndex) {
        this.username = username;
        this.firstTime = firstTime;
        this.tileIndex = tileIndex;

        TopUpMessage message = new TopUpMessage(username, firstTime, tileIndex);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createQuitMessage(String username) {
        QuitMessage message = new QuitMessage(username);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createCreateGameMessage(String username, boolean connection_type, int numPlayers) {
        CreateGame_Message message = new CreateGame_Message(username, connection_type, numPlayers);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    protected String createJoinMessage(String username, boolean connection_type) {
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
