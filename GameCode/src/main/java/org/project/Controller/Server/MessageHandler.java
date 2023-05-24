package org.project.Controller.Server;

import com.google.gson.*;
import org.project.Controller.Control.InvalidLoginException;
import org.project.Controller.Messages.*;

public class MessageHandler {

    private Server server;
    private SocketClientHandler client;
    private SocketServer socketServer;
    public MessageHandler(Server server, SocketServer socketServer, SocketClientHandler client){
        this.server = server;
        this.socketServer = socketServer;
        this.client = client;
    }
    public void handle(String jsonStr){
        Gson gson = new Gson();

        JsonElement jelement =  JsonParser.parseString(jsonStr).getAsJsonObject();
        JsonObject jsObject = jelement.getAsJsonObject();
        JsonElement id = jsObject.get("ID");
        MessageID ID = gson.fromJson(id, MessageID.class);
        switch (ID){
            case PICK -> this.handlePick(gson.fromJson(jsonStr, PickMessage.class));
            case QUIT -> this.handleQuit(gson.fromJson(jsonStr, QuitMessage.class));
            case CREATE_GAME -> this.handleCreateGame(gson.fromJson(jsonStr, CreateGame_Message.class));
            case TOPUP -> this.handleTopUp(gson.fromJson(jsonStr, TopUpMessage.class));
            case JOIN -> this.handleJoin(gson.fromJson(jsonStr, JoinMessage.class));
        };
    }
    public void handlePick(PickMessage pickMsg){
        server.pick(pickMsg.getUsername(), pickMsg.getCoordinates());
    }
    public void handleTopUp(TopUpMessage topUpMsg){
        server.topUp(topUpMsg.getUsername(), topUpMsg.getColumn(), topUpMsg.getTileIndex());
    }
    public void handleQuit(QuitMessage quitMessage){
        client.disconnect();
        server.quit(quitMessage.getUsername());

    }
    public void handleCreateGame(CreateGame_Message create_gameMsg){
        String username = create_gameMsg.getUsername();
        client.setUsername(username);
        PopUpMsg popUpMsg = new PopUpMsg();
        boolean connection = create_gameMsg.getConnectionType();
        int num_players = create_gameMsg.getNumPlayers();
        if(num_players >= 2 && num_players <= 4) {

            try {
                if (server.create_game(username, connection, num_players)) {
                    socketServer.getSocketClients().put(username, client);
                    popUpMsg.setText("Game has been created");
                    send(popUpMsg);
                    return;
                }
            } catch (InvalidLoginException e) {
                popUpMsg.setText(e.getMessage());
                send(popUpMsg);
            }
            popUpMsg.setText("Already an existing game");
            return;
        }
        popUpMsg.setText("Number of players is not valid, has to be between 2 and 4");
        send(popUpMsg);
    }
    public void handleJoin(JoinMessage joinMsg){
        String username = joinMsg.getUsername();
        client.setUsername(username);
        PopUpMsg popUpMsg = new PopUpMsg();
        boolean connection = joinMsg.getConnectionType();
        try {
            if (server.join(username, connection)) {
                socketServer.getSocketClients().put(username, client);
                popUpMsg.setText("Successfully joined the game");
                send(popUpMsg);
                server.getController().refreshRequest(username);
            }
        } catch (InvalidLoginException e) {
            popUpMsg.setText(e.getMessage());
            send(popUpMsg);
        }
    }
    public void send(Message message){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        client.getOut().println(jsonStr);
        System.out.println("\nMessage sent (Message Handler send method)");
    }

}
