package org.project.Controller.Server;

import org.project.Controller.Messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a client handler for socket communication in the server.
 * This class handles the communication with a specific client connected to the server via a socket.
 */

public class SocketClientHandler implements Runnable {

    private static final int KEEP_ALIVE_INTERVAL = 10;
    private Server server;
    private Socket socket;
    private String username = null;
    private SocketServer socketServer;
    private Scanner in;
    private PrintWriter out;
    private MessageHandler messageHandler;
    private boolean connected;
    private Timer keepAlive;
    private Timer keepAliveTimer;

    /**
     * Creates a new instance of SocketClientHandler.
     *
     * @param socket the socket representing the client connection.
     * @param server reference to the server instance.
     * @param socketServer reference to the socket server.
     */
    public SocketClientHandler(Socket socket, Server server, SocketServer socketServer) {
        this.server = server;
        this.socket = socket;
        this.socketServer = socketServer;

        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.messageHandler = new MessageHandler(server, socketServer, this);
            startKeepAlive();
            resetKeepAliveTimer();
            connected = true;
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Runs the client handler thread,
     * this method listens for incoming messages from the client and delegates the handling of each message.
     * if the message is a KEEP ALIVE it resets keep alive timer
     */

    public void run() {
        while (connected) {
            try {
                String line = in.nextLine();
                if ("KEEP_ALIVE".equals(line)) {
                    resetKeepAliveTimer();
                } else {
                    messageHandler.handle(line);
                }
            } catch (Exception e){
               disconnect();
            }
        }
    }

    /**
     * Sends a message to the client.
     * @param message The message to send.
     */
    public void send(Message message){
        messageHandler.send(message);
    }

    /**
     * disconnects the client from the server.
     * this method sets the connected flag to false,
     * notifies the server about the player disconnection,
     * stops the keep-alive mechanism, and closes the socket and associated resources.
     */
    public void disconnect() {
        if(connected){
        connected = false;
        server.set_player_disconnected(username);
        stopKeepAlive();
        closeResources();
        }
    }

    /**
     * Starts the keep-alive mechanism
     * sending periodic keep-alive messages to the client to maintain the connection.
     */
    private void startKeepAlive() {
        this.keepAliveTimer = new Timer();
        keepAliveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.println("KEEP_ALIVE");
            }
        }, 0, 1000);
    }

    /**
     * Stops the keep-alive mechanism.
     * This method cancels the keep-alive timer if it is running.
     */
    public void stopKeepAlive() {
        if (this.keepAliveTimer != null) {
            this.keepAliveTimer.cancel();
            this.keepAliveTimer = null;
        }
    }

    /**
     * Resets the keep-alive timer: cancels the current keep-alive timer and starts a new one with the specified interval.
     */
    private void resetKeepAliveTimer() {
        synchronized(this) {
            if(this.keepAlive != null) {
                this.keepAlive.cancel();
            }
            this.keepAlive = new Timer();
            this.keepAlive.schedule(new TimerTask() {
                @Override
                public void run() {
                    disconnect();
                }
            }, KEEP_ALIVE_INTERVAL * 1000);
        }
    }

    /**
     * Closes the socket and associated resources.
     */
    private void closeResources() {
        try {
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Server getServer() {
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public SocketServer getSocketServer() {
        return socketServer;
    }

    public Scanner getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public boolean isConnected() {
        return connected;
    }
}
