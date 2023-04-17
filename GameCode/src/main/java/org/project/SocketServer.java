package org.project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private final Server server;
    int port;

    public SocketServer(Server server, int port){
        this.server = server;
        this.port = port;
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
                executor.submit(new SocketClientHandler(socket));
            } catch(IOException e) {
                break; // If server socket is closed
            }
        }
        executor.shutdown();
    }

}
