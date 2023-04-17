package org.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class SocketClientHandler implements Runnable {
    private Socket socket;
    public SocketClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            //We then continue to read from input unless a quit message has been sent
            while (true) {
                String line = in.nextLine();
                //Parsing needed, if it is a quit message we close the socket
            }
            //in.close();
            //out.close();
            //socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

