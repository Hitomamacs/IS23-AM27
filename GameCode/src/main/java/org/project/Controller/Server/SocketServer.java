package org.project.Controller.Server;

import org.project.Controller.Server.Server;
import org.project.Controller.Server.SocketClientHandler;
import org.project.Controller.View.GridView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Socket server
 */
public class SocketServer implements Runnable {


    /**
     * reference to the server
     */
    private final Server server;
    /**
     * port for connection
     */
    int port;

    private HashMap<String, Timer> keepAliveTimers = new HashMap<>();


    /**
     * hash map that contains all references ti socket clients
     * String is the username
     */
    private HashMap<String, SocketClientHandler> socketClients;

    /**
     * Creates a new instance of SocketServer.
     * @param server the server instance.
     * @param port the port number to listen on.
     */
    public SocketServer(Server server, int port){
        this.server = server;
        this.port = port;
        socketClients = new HashMap<>();
    }

    /**
     * This method close all connections and then create a new hashmap for a new game
     */
    public void flushSocketClients(){
        //Trying to close all connections and then create a new hashmap for a new game
       /* for(Map.Entry<String, SocketClientHandler> mapElement : socketClients.entrySet()){
            mapElement.getValue().disconnect();
        } */
        socketClients = new HashMap<>();
    }

    /**
     * start socket server
     */
    @Override
    public void run() {
        startSocketServer();
    }

    /**
     * Prepare the socket server for new connection
     */
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

    public HashMap<String, Timer> getKeepAliveTimers() {
        return keepAliveTimers;
    }

    public HashMap<String, SocketClientHandler> getSocketClients(){
        return socketClients;
    }
}
