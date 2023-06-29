package org.project.ClientPack;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;

/**
 * interface implemented by GUI and CLI
 */
public interface UserInterface {

    ClientView getClientView();
    String getInput();
    String getNickname();
    String getUI();

    /**
     * Starts a new thread to listen for user input and perform corresponding actions.
     */
    public void launcher();

    /**
     * Processes the received message from the server.
     *
     * @param serverMessage the received message as a string
     */
    void processReceivedMessage(String serverMessage);

    /**
     * Displays the personal goal card for the specified player.
     *
     * @param playerName the name of the player
     */
    void ShowPObj(String playerName);

    /**
     * Displays a message to the user.
     *
     * @param invalidMessageType the message to be displayed
     */
    void displayMessage(String invalidMessageType);
    /**
     * Displays the common goal for all players.
     *
     * @param playerName the name of the player
     */
    void ShowCObj(String playerName);

    /**
     * Sends a join message to the server using the provided client connection (rmi/socket).
     * The method prompts the user to enter a username and sends the join message with the username.
     *
     * @param client The client connection interface used to send the join message.
     */
    void SendJoinMessage(ConnectionInterface client);

    /**
     * Sends a create game message to the server using the provided client connection (rmi/socket).
     * The method prompts the user to enter a username and the number of players for the game.
     * It sends the create game message with the username and number of players.
     * After sending the message, the nickname field is updated with the chosen username.
     *
     * @param client the client connection interface used to send the create game message.
     * @throws RuntimeException if an error occurs during the remote method invocation.
     */
    void SendCreateGameMessage(ConnectionInterface client);

    /**
     * Sends a quit message to the server using the provided client connection (rmi/socket).
     * The method sends the quit message with the current nickname to indicate the player's intention to quit the game.
     *
     * @param client The client connection interface used to send the quit message.
     */
    void SendQuitMessage(ConnectionInterface client);

    /**
     * Sends a pick message to the server using the provided client connection (rmi/socket).
     * The method sends the pick message with the current nickname, the number of tiles to pick, and the list of coordinates
     * indicating the positions of the tiles to be picked.
     *
     * @param client The client connection interface used to send the pick message.
     */
    void SendPickMessage(ConnectionInterface client);

    /**
     * Sends a top-up message to the server using the provided client connection (rmi/socket).
     * The method sends the top-up message with the current nickname, the flag indicating if it's the first top-up,
     * and the index of the tile to be topped up.
     *
     * @param client The client connection interface used to send the top-up message.
     */
    void SendTopUpMessage(ConnectionInterface client);

    /**
     * Sends chat messages to other players.
     *
     * @param client the connection interface used for sending messages
     */
    void SendChat(ConnectionInterface client);

    /**
     * Handles the case when the server is unreachable.
     * Displays an error message, waits for 10 seconds, and then exits the application.
     */
    void serverDown();
    void printBoard();
    void printTiles(String playername);

    /**
     * Prints the grid of a specific player.
     *
     * @param playername the name of the player whose grid is to be printed
     */
    void printGrids(String playername);
    void updateClientViewTiles(String playerName, String[] tiles);
    void updateGridsView(String playerName, String[][] grid);

    /**
     * Updates the client view.
     *
     * @param clientView the new client view to be set
     */
    void updateClientView(ClientView clientView);

    void fillChatMap();
}
