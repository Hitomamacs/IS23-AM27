package org.project;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.project.Controller.Messages.*;

import java.util.Scanner;

public class CliUserInterface implements UserInterface{

    private  ClientView clientView = new ClientView();

    private final Scanner scanner = new Scanner(System.in);

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getInput() {
        return scanner.nextLine();
    }


    public void handleTopUpUpdate(UpdateTopUPMsg message){
        clientView.updateGridsView(message.getPlayerName(), message.getGrid());
        clientView.printGrid(message.getPlayerName());
    }

    public void handlePickUpdate(UpdatePickMsg message){
        clientView.setBoard(message.getBoard());
        clientView.updateTilesView(message.getPlayerName(), message.getTiles());
        clientView.printBoard();
        clientView.printTiles(message.getPlayerName());
    }

    public void handlePopUp(PopUpMsg message){
        clientView.setErrorMessage(message.getText());
        System.out.println(message.getText());
    }

    public void handleScoreUpdate(ScoreBoardMsg message){
        clientView.setScoreBoard(message.getScoreBoard());
    }

    public void handleRefreshUpdate(RefreshMsg message){
        clientView.setBoard(message.getBoard());
        clientView.setTilesview(message.getTilesview());
        clientView.setGridsview(message.getGridsview());
        clientView.setPointStack(message.getPointStack());
        clientView.printBoard();
        System.out.println("Printing Just Board (Refresh message), Start Game");
    }

    public synchronized  void processReceivedMessage(String line) {
        if (line != null && !line.equals("KEEP_ALIVE")) {
            JsonElement jelement = JsonParser.parseString(line).getAsJsonObject();
            JsonObject jsObject = jelement.getAsJsonObject();
            JsonElement id = jsObject.get("ID");
            Gson gson = new Gson();
            MessageID ID = gson.fromJson(id, MessageID.class);
            switch (ID) {
                case TOPUP_UPDATE:
                    handleTopUpUpdate(gson.fromJson(line, UpdateTopUPMsg.class));
                    break;
                case PICK_UPDATE:
                    handlePickUpdate(gson.fromJson(line, UpdatePickMsg.class));
                    break;
                case POP_UP:
                    handlePopUp(gson.fromJson(line, PopUpMsg.class));
                    break;
                case SCORE_UPDATE:
                    handleScoreUpdate(gson.fromJson(line, ScoreBoardMsg.class));
                    break;
                case REFRESH_UPDATE:
                    handleRefreshUpdate(gson.fromJson(line, RefreshMsg.class));
                    break;

                default:
                    break;
            }
        }
    }
}
