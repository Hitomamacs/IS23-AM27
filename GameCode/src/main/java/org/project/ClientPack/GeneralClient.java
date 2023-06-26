package org.project.ClientPack;

import java.io.IOException;

public class GeneralClient implements ClientInterface {

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
            Cli_Images start_image = new Cli_Images();
            start_image.printFirstImage();
            
            System.out.println("Enter create_game to create a new game");
            System.out.println("Enter join to join an already existing game");
            System.out.println("Enter help to view all commands");


        }
        new Thread(() -> {
            while (client.get_connection_type()) {
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
