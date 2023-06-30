package org.project.ClientPack;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;

public interface UserInterface {

    ClientView getClientView();
    String getInput();
    String getNickname();
    String getUI();

    public void launcher();
    void processReceivedMessage(String serverMessage);
    void ShowPObj(String playerName);
    void displayMessage(String invalidMessageType);
    void ShowCObj(String playerName);
    void SendJoinMessage(ConnectionInterface client);
    void SendCreateGameMessage(ConnectionInterface client);
    void SendQuitMessage(ConnectionInterface client);
    void SendPickMessage(ConnectionInterface client);
    void SendTopUpMessage(ConnectionInterface client);
    void SendChat(ConnectionInterface client);
    void serverDown();
    void printBoard();
    void printTiles(String playername);
    void printGrids(String playername);
    void updateClientViewTiles(String playerName, String[] tiles);
    void updateGridsView(String playerName, String[][] grid);
    void updateClientView(ClientView clientView);

    void fillChatMap();
}
