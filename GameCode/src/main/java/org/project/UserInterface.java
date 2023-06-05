package org.project;

public interface UserInterface {

    ClientView getClientView();
    String getInput();

    void displayMessage(String invalidMessageType);

    void processReceivedMessage(String serverMessage);

    void ShowPObj(String playerName);

    String getNickname();

    String getUI();

    void ShowCObj(String playerName);

    void SendJoinMessage(ConnectionInterface client);
    void SendCreateGameMessage(ConnectionInterface client);
    void SendQuitMessage(ConnectionInterface client);
    void SendPickMessage(ConnectionInterface client);
    void SendTopUpMessage(ConnectionInterface client);

    void printBoard();

    void printTiles(String playername);

    void printGrids(String playername);


    void updateClientViewTiles(String playerName, String[] tiles);

    void updateGridsView(String playerName, String[][] grid);
    void updateClientView(ClientView clientView);
}
