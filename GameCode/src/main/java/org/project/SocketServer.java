package org.project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private final Server server;
    int port;

    private HashMap<String, SocketClientHandler> socketClients;

    public SocketServer(Server server, int port){
        this.server = server;
        this.port = port;
        socketClients = new HashMap<>();
    }
    public HashMap<String, SocketClientHandler> getSocketClients(){
        return socketClients;
    }
    public void startSocketServer(){
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Port not available
            return;
        }
        System.out.println("Server ready");
        //Not while true but while variable in server indicating number of players still needing to
        //connect is bigger than zero
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                SocketClientHandler clientHandler = new SocketClientHandler(socket, server, this);
                executor.submit(clientHandler);
            } catch(IOException e) {
                break; // If server socket is closed
            }
        }
        executor.shutdown();
    }

}
