package org.project.ClientPack;

import java.io.IOException;

/**
 * This class represents a general client that interfaces with a client factory and a user interface factory.
 * It implements the ClientInterface, serving as a bridge between the user interface and the connection interface.
 * It has one public method, startClient, which launches the client and handles input/output with the user interface.
 */

public class GeneralClient implements ClientInterface {

    /**
     * This method starts a client session. It creates an instance of ConnectionInterface and UserInterface.
     * If the user interface type is GUI, it starts a new thread and launches the GUI.
     * If the user interface type is CLI, it launches the CLI and presents the user with the main menu options.
     * It then creates a new thread for continuously reading and processing messages from the server.
     *
     * @param clientFactory        The factory used to create a new client instance.
     * @param userInterfaceFactory The factory used to create a new user interface instance.
     * @throws Exception if there's an error while launching the user interface or receiving a message from the server.
     */

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
