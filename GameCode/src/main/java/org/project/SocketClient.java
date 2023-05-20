package org.project;

import com.google.gson.Gson;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class SocketClient extends AbstractClientApp implements ConnectionInterface{

    private  ClientView clientView = new ClientView();

    private Timer keepAlive;

    private static final int KEEP_ALIVE_INTERVAL = 5 * 1000;  // in milliseconds


    private final Scanner scanner = new Scanner(System.in);



    private static final int MAX_TILES = 3;

    private long lastKeepAliveReceivedTime;
    private Timer keepAliveTimer;
    private BufferedReader in;

    private PrintWriter out;

    private final Gson gson = new Gson();

    public SocketClient() {
        // qui connetti il client al server
        try {
            Socket echoSocket = new Socket(Settings.SERVER_NAME, Settings.SOCKET_PORT);
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
        System.out.println("Disconnected from server");
    }

    private void startKeepAlive() {
        Timer keepAlive = new Timer();
        keepAlive.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.println("KEEP_ALIVE");
            }
        }, 0, 1000);
    }





    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        String line = in.readLine();
        if (line.equals("KEEP_ALIVE")) {
            lastKeepAliveReceivedTime = System.currentTimeMillis();
        }
        else
            return line;
        return line;
    }

    public void SendPickMessage(String username, int numTiles, List<Coordinates> coordinates) {
        sendMessage(createPickMessage(username, numTiles, coordinates));
    }


    public void SendTopUpMessage(String username, int firstTime, int tileIndex) {

        // Get the user's tiles
        String[] userTiles = clientView.getTilesview().get(getUsername());
        long numTiles = Arrays.stream(userTiles).filter(tile -> !tile.equals("N")).count();
        for (int i = 0; i < numTiles; i++) {
            sendMessage(createTopUpMessage(username, firstTime, tileIndex));

        }
    }


    public void SendQuitMessage(String username) {
        sendMessage(createQuitMessage(username));
    }
    public void SendCreateGameMessage(String username, boolean connection_type, int numPlayers) {
        sendMessage(createCreateGameMessage(username, connection_type, numPlayers));
    }

    public void SendJoinMessage(String username, boolean connection_type) {
        sendMessage(createJoinMessage(username, connection_type));
    }




}

