package org.project;

import java.io.IOException;
import java.rmi.RemoteException;

public class GeneralClient implements ClientInterface{

    public void startClient(ClientFactory clientFactory, UserInterfaceFactory userInterfaceFactory) throws Exception {
        ConnectionInterface client = clientFactory.createClient();
        UserInterface userInterface = userInterfaceFactory.createUserInterface(client.getClientView());
        client.setUserInterface(userInterface);
        if(userInterface.getUI() == "GUI"){
            userInterface.launcher();

        }

        new Thread(() -> {
            while (true) {

                    String userInput = userInterface.getInput();
                    // Depending on the user input, send a different type of message
                    switch (userInput) {
                        case "join":
                            userInterface.SendJoinMessage(client);
                            break;
                        case "create_game":
                            userInterface.SendCreateGameMessage(client);
                            break;
                        case "quit":
                            userInterface.SendQuitMessage(client);
                            break;
                        case "pick":
                            userInterface.SendPickMessage(client);
                            break;
                        case "topup":
                            userInterface.SendTopUpMessage(client);
                            break;
                        case "Show P Obj":
                            userInterface.ShowPObj(userInterface.getNickname());

                        case "Show C Obj":
                            userInterface.ShowCObj(userInterface.getNickname());
                        default:
                            userInterface.displayMessage("Invalid message type");
                    }

            }
        }).start();

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
