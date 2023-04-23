package org.project;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class SocketClientHandler implements Runnable {

    private Server server;
    private Socket socket;
    private SocketServer socketServer;
    private MessageHandler messageHandler;
    private boolean connected;
    public SocketClientHandler(Socket socket, Server server, SocketServer socketServer) {
        this.server = server;
        this.socket = socket;
        this.socketServer = socketServer;
        messageHandler = new MessageHandler(server, socketServer,this);
        connected = true;
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            //We then continue to read from input unless a quit message has been sent
            while (connected) {
                String line = in.nextLine();
                messageHandler.handle(line);
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void disconnect(){
        connected = false;
    }

    public void send(Message message){


    }
}

