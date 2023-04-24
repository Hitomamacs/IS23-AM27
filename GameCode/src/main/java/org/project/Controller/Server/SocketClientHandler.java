package org.project.Controller.Server;


import org.project.Controller.Messages.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class SocketClientHandler implements Runnable {

    private Server server;
    private Socket socket;
    private SocketServer socketServer;

    private Scanner in;

    private PrintWriter out;
    private MessageHandler messageHandler;
    private boolean connected;
    public SocketClientHandler(Socket socket, Server server, SocketServer socketServer) {
        try {
            this.server = server;
            this.socket = socket;
            this.socketServer = socketServer;
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream());
            messageHandler = new MessageHandler(server, socketServer, this);
            connected = true;
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }
    public void run() {
        while (connected) {
            String line = in.nextLine();
            messageHandler.handle(line);
        }
    }
    public void disconnect(){
        connected = false;
        closeEverything(socket, in, out);
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

