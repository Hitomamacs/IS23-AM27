package org.project.Controller.Server;


import org.project.Controller.Messages.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class SocketClientHandler implements Runnable {

    private static final int KEEP_ALIVE_INTERVAL = 5;

    private Server server;
    private Socket socket;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username = null;


    private SocketServer socketServer;

    private Scanner in;

    private PrintWriter out;
    private MessageHandler messageHandler;
    private boolean connected;

    Timer keepAlive = null;

    Timer keepAliveTimer;






    public SocketClientHandler(Socket socket, Server server, SocketServer socketServer) {
        try {
            this.server = server;
            this.socket = socket;
            this.socketServer = socketServer;
            resetKeepAliveTimer();
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(), true);
            startKeepAlive();
            this.out = out;
            //out.println("Connected to server");
            messageHandler = new MessageHandler(server, socketServer, this);
            connected = true;
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void resetKeepAliveTimer() {
        synchronized(this) {
            if(keepAlive != null) {
                keepAlive.cancel();
            }
            keepAlive = new Timer();

            keepAlive.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("No keep-alive signal received for " + KEEP_ALIVE_INTERVAL + " seconds. Disconnecting...");
                    disconnect();  // Call your custom disconnect function here
                }
            }, KEEP_ALIVE_INTERVAL * 1000);
        }
    }

    private void startKeepAlive() {
        keepAliveTimer = new Timer();
        keepAliveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.println("KEEP_ALIVE");
            }
        }, 0,  1000);
    }

    public void stopKeepAlive() {
        if (keepAliveTimer != null) {
            keepAliveTimer.cancel();
            keepAliveTimer = null;
        }
    }


    public void run() {
        while (connected) {
            try {
                String line = in.nextLine();
                if ("KEEP_ALIVE".equals(line)) {
                    resetKeepAliveTimer();
                } else {
                    messageHandler.handle(line);
                }
            }catch (Exception e){
                System.out.println("Client disconnected");
                server.set_player_disconnected(username);
                connected = false;
                closeEverything(socket, in, out);
            }



        }
    }
    public void disconnect() {
        Timer timer = socketServer.getKeepAliveTimers().remove(getUsername());
        System.out.println("Keep alive timer cancelled");
        if (timer != null) {
            timer.cancel();
        }
        if (connected) {
            server.set_player_disconnected(username);
            connected = false;
            closeEverything(socket, in, out);
        }
    }

    public void send(Message message){
        messageHandler.send(message);

    }
    public void closeEverything(Socket socket, Scanner in, PrintWriter out){

        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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

