package org.project;

public interface UserInterface {
    String getInput();

    void displayMessage(String invalidMessageType);

    void processReceivedMessage(String serverMessage);
}
