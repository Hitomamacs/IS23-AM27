package org.project.ClientPack;

import com.google.gson.Gson;
import org.project.Controller.Messages.*;
import org.project.Model.Coordinates;

import java.util.*;

/**
 * abstract class for socket client
 */

public abstract class AbstractClientApp implements Runnable {

    /**
     * reference client view
     */
    protected ClientView clientView = new ClientView();

    /**
     * gson
     */
    protected final Gson gson = new Gson();

    protected final Scanner scanner = new Scanner(System.in);

    private int firstTime = -1;
    private int tileIndex = -1;
    private String username;
    public static final int MAX_TILES = 3;
    public int get_tile_index() {
        return tileIndex;
    }

    /**
     * Create a PickMessage JSON string.
     *
     * @param username The username.
     * @param numTiles The number of tiles.
     * @param coordinates The coordinates.
     * @return The JSON string representing the PickMessage.
     */
    protected String createPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
        this.username = username;
        clientView.setNum_tiles(numTiles);
        numTiles = Math.min(numTiles, MAX_TILES); // Ensure the number of tiles doesn't exceed 3

        PickMessage message = new PickMessage(username, coordinates);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    /**
     * Create a TopUpMessage JSON string.
     *
     * @param username The username.
     * @param firstTime The first time.
     * @param tileIndex The tile index.
     * @return The JSON string representing the TopUpMessage.
     */
    protected String createTopUpMessage(String username, int firstTime, int tileIndex) {
        this.username = username;
        this.firstTime = firstTime;
        this.tileIndex = tileIndex;

        TopUpMessage message = new TopUpMessage(username, firstTime, tileIndex);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    /**
     * Create a QuitMessage JSON string.
     *
     * @param username The username.
     * @return The JSON string representing the QuitMessage.
     */
    protected String createQuitMessage(String username) {
        QuitMessage message = new QuitMessage(username);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    /**
     * Create a CreateGameMessage JSON string.
     *
     * @param username The username.
     * @param connection_type The connection type.
     * @param numPlayers The number of players.
     * @return The JSON string representing the CreateGameMessage.
     */
    protected String createCreateGameMessage(String username, boolean connection_type, int numPlayers) {
        CreateGame_Message message = new CreateGame_Message(username, connection_type, numPlayers);
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    /**
     * Create a JoinMessage JSON string.
     *
     * @param username The username.
     * @param connection_type The connection type.
     * @return The JSON string representing the JoinMessage.
     */
    protected String createJoinMessage(String username, boolean connection_type) {
        JoinMessage message = new JoinMessage(username, connection_type);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }

    /**
     * Create a ChatMessage JSON string.
     *
     * @param username The username.
     * @param text The chat message text.
     * @return The JSON string representing the ChatMessage.
     */
    protected String createChatMessage(String username, String text){
        ChatMessage message = new ChatMessage(username, text);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        return jsonStr;
    }
    protected String createChatMessage(String username, String text, String receiver){
        ChatMessage message = new ChatMessage(username, text, receiver);
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
