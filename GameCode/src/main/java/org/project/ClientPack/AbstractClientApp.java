package org.project.ClientPack;

import com.google.gson.Gson;
import org.project.Controller.Messages.*;
import org.project.Model.Coordinates;

import java.util.*;

public abstract class AbstractClientApp implements Runnable {
    protected ClientView clientView = new ClientView();
    protected final Gson gson = new Gson();
    protected final Scanner scanner = new Scanner(System.in);

    private int firstTime = -1;



    public int get_tile_index() {
        return tileIndex;
    }

    private int tileIndex = -1;

    private String username;
    public static final int MAX_TILES = 3;







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
    protected String createChatMessage(String username, String text){
        ChatMessage message = new ChatMessage(username, text);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
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
