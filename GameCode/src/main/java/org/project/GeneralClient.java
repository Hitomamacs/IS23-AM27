package org.project;

import java.io.IOException;
import java.rmi.RemoteException;

public class GeneralClient implements ClientInterface{

    public void startClient(ClientFactory clientFactory, UserInterfaceFactory userInterfaceFactory) throws Exception {
        ConnectionInterface client = clientFactory.createClient();
        UserInterface userInterface = userInterfaceFactory.createUserInterface(client.getClientView(), client);
        client.setUserInterface(userInterface);
        if(userInterface.getUI() == "GUI"){
           new Thread(() -> {
               try {
                   userInterface.launcher();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }).start();

        }
        if(userInterface.getUI() == "CLI"){
            userInterface.launcher();

        }



        new Thread(() -> {
            while (true) {
                try {
                    String serverMessage = client.receiveMessage();
                    userInterface.processReceivedMessage(serverMessage);
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
