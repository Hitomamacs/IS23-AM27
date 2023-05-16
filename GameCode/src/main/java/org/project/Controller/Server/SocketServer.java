package org.project.Controller.Server;

import org.project.Controller.Server.Server;
import org.project.Controller.Server.SocketClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable {

    private final Server server;
    int port;

    public HashMap<String, Timer> getKeepAliveTimers() {
        return keepAliveTimers;
    }

    private HashMap<String, Timer> keepAliveTimers = new HashMap<>();


    private HashMap<String, SocketClientHandler> socketClients;

    public SocketServer(Server server, int port){
        this.server = server;
        this.port = port;
        socketClients = new HashMap<>();
    }
    public HashMap<String, SocketClientHandler> getSocketClients(){
        return socketClients;
    }

    @Override
    public void run() {
        startSocketServer();
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
                server.setConnectedPlayers(server.getConnectedPlayers() + 1);
            } catch(IOException e) {
                break; // If server socket is closed
            }
        }
        executor.shutdown();
    }

}
