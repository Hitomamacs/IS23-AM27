package org.project;

import com.google.gson.Gson;
import org.project.Controller.Messages.LoginMessage;
import org.project.Controller.Messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientApp implements ClientInterface{
    public void startClient() throws Exception{
        String hostName = "127.0.0.1";
        int portNumber = 5678;
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            LoginMessage message = new LoginMessage("Spike", true, 4);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(message);
            out.println(jsonStr);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

    }
    public void sendLoginRequest(String nickname){

    }
    public void sendMessage(String message){

    }
    public void sendPickRequest(){

    }
    public void sendTopUpRequest(){

    }
}
