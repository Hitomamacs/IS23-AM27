package org.project;

import java.io.IOException;
import java.rmi.RemoteException;

public class GeneralClient implements ClientInterface{



    public void startClient(ClientFactory clientFactory, UserInterfaceFactory userInterfaceFactory) throws Exception {
        ConnectionInterface client = clientFactory.createClient();
        UserInterface userInterface = userInterfaceFactory.createUserInterface();

        new Thread(() -> {
            while (true) {
                String userInput = userInterface.getInput();
                // In base all'input dell'utente, invia un tipo di messaggio diverso
                switch (userInput) {
                    case "join":
                        client.SendJoinMessage();
                        break;
                    case "create_game":
                        try {
                            client.SendCreateGameMessage();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "quit":
                        client.SendQuitMessage();
                        break;
                    case "pick":
                        client.SendPickMessage();
                        break;
                    case "topup":
                        client.SendTopUpMessage();
                        break;
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

                    // In base al messaggio del server, mostra un tipo di messaggio diverso
                    // (qui dovresti interpretare il messaggio del server e chiamare il metodo appropriato)
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }





}
