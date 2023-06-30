package org.project.ClientPack;

import com.google.gson.Gson;
import org.project.Controller.Messages.Message;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.*;

public class SocketClient extends AbstractClientApp implements ConnectionInterface {

    private boolean connection_type = true;
    private ClientView clientView = new ClientView();

    private boolean first = true;

    UserInterface userInterface;
    private Timer keepAlive;
    private static final int KEEP_ALIVE_INTERVAL = 5 * 1000;  // in milliseconds

    private final Scanner scanner = new Scanner(System.in);

    private static final int MAX_TILES = 3;
    private long lastKeepAliveReceivedTime;
    private Timer keepAliveTimer;
    private BufferedReader in;
    private PrintWriter out;
    public Socket echoSocket;

    private final Gson gson = new Gson();

    public SocketClient() {
        // qui connetti il client al server
        try {
            echoSocket = new Socket(Settings.SERVER_NAME, Settings.SOCKET_PORT);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            startKeepAlive();
            startKeepAliveCheck();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + Settings.SERVER_NAME);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + Settings.SERVER_NAME);
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
        //System.out.println("Disconnected from server");
        userInterface.serverDown();
    }

    private void startKeepAlive() {
         keepAlive = new Timer();
        keepAlive.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.println("KEEP_ALIVE");
            }
        }, 0, 1000);
    }

    private void stopKeepAlive() {
        if (keepAlive != null) {
            keepAlive.cancel();
            keepAlive = null;
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage()  {

        String line = null;
        try {
            if(in!= null){
            line = in.readLine();}
            else
                throw new IOException();
        } catch (IOException e) {
            if(first){
            System.out.println("Server down, Start Server And then Client");
            first = false;

        }}
        if (line != null &&line.equals("KEEP_ALIVE")) {
            lastKeepAliveReceivedTime = System.currentTimeMillis();
        }
        else
            return line;
        return line;
    }

    /**
     * @return the message
     * @throws IOException
     */
    @Override
    public String receiveMessage(Message msg) throws IOException {
        return null;
    }

    @Override
    public void setUserInterface(UserInterface client) {
        this.userInterface = client;
    }

    /**
     * Sends a pick message
     * @param username player's name
     * @param numTiles number of the tiles that the player has picked
     * @param coordinates coordinates of the tiles that the player has picked
     */
    public void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
        sendMessage(createPickMessage(username, numTiles, coordinates));
    }

    /**
     * Sends a chat message
     * @param username player's name
     * @param text text of the message
     */
    public void SendChatMessage(String username, String text){
        sendMessage(createChatMessage(username, text));
    }

    @Override
    public void SendChatMessage(String username, String text, String receiver) {
        sendMessage(createChatMessage(username, text, receiver));
    }


    /**
     * Sends a top-up message
     * @param username player's name
     * @param firstTime column number
     * @param tileIndex number of the tile in the array picked tiles
     */
    public void SendTopUpMessage(String username, int firstTime, int tileIndex) {

        sendMessage(createTopUpMessage(username, firstTime, tileIndex));
    }

    private void close_connection() {
        try {
            in.close();
            out.close();
            echoSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Sends a quit message
     * @param username player's name who wants to quit
     */
    public void SendQuitMessage(String username) {
        stopKeepAlive();
        sendMessage(createQuitMessage(username));
        close_connection();
    }

    /**
     * Sends a create game message
     * @param username player's name
     * @param connection_type socket/rmi
     * @param numPlayers number of the players that want to take part in the game
     * @throws RemoteException
     */
    public void SendCreateGameMessage(String username, boolean connection_type, int numPlayers) {
        sendMessage(createCreateGameMessage(username, connection_type, numPlayers));
    }

    @Override
    public ClientView getClientView() {
        return this.clientView;
    }

    @Override
    public boolean get_connection_type() {
        return true;
    }

    /**
     * Sends a join message
     * @param username player's name
     * @param connection_type socket/rmi
     */
    public void SendJoinMessage(String username, boolean connection_type) {
        sendMessage(createJoinMessage(username, connection_type));
    }
}

