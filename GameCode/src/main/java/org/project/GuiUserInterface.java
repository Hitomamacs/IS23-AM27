package org.project;

import javafx.application.Application;
import javafx.stage.Stage;

public class GuiUserInterface extends Application implements UserInterface{
    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public ClientView getClientView() {
        return null;
    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public void displayMessage(String invalidMessageType) {

    }

    @Override
    public void processReceivedMessage(String serverMessage) {

    }

    @Override
    public void SendJoinMessage(ConnectionInterface client) {

    }

    @Override
    public void SendCreateGameMessage(ConnectionInterface client) {

    }

    @Override
    public void SendQuitMessage(ConnectionInterface client) {

    }

    @Override
    public void SendPickMessage(ConnectionInterface client) {

    }

    @Override
    public void SendTopUpMessage(ConnectionInterface client) {

    }

    @Override
    public void printBoard() {

    }

    @Override
    public void printTiles(String playername) {

    }

    @Override
    public void printGrids(String playername) {

    }

    @Override
    public void updateClientViewTiles(String playerName, String[] tiles) {

    }

    @Override
    public void updateGridsView(String playerName, String[][] grid) {

    }

    @Override
    public void updateClientView(ClientView clientView) {

    }
}
