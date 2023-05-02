package org.project.Controller.Server;

import com.google.gson.*;
import netscape.javascript.JSObject;
import org.project.Controller.Messages.*;
import org.project.Controller.Server.Server;
import org.project.Controller.Server.SocketClientHandler;
import org.project.Controller.Server.SocketServer;

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
        JsonElement id = jsObject.get("messageID");
        MessageID ID = gson.fromJson(id, MessageID.class);
        switch (ID){
            case PICK -> this.handlePick(gson.fromJson(jsonStr, PickMessage.class));
            case QUIT -> this.handleQuit(gson.fromJson(jsonStr, QuitMessage.class));
            case LOGIN -> this.handleLogin(gson.fromJson(jsonStr, LoginMessage.class));
            case TOPUP -> this.handleTopUp(gson.fromJson(jsonStr, TopUpMessage.class));
        };
    }
    public void handlePick(PickMessage pickMsg){
        server.pick(pickMsg.getUsername(), pickMsg.getCoordinates());
    }
    public void handleTopUp(TopUpMessage topUpMsg){
        server.topUp(topUpMsg.getUsername(), topUpMsg.getColumn(), topUpMsg.getTileIndex());
    }
    public void handleQuit(QuitMessage quitMessage){
        server.quit(quitMessage.getUsername());
        client.disconnect();
    }
    public void handleLogin(LoginMessage loginMsg){

        String username = loginMsg.getUsername();
        boolean connection = loginMsg.getConnectionType();
        int num_players = loginMsg.getNumPlayers();
        if(!(num_players == 0)){//TODO absolutely change
            if(server.login(username, connection, num_players)){
                socketServer.getSocketClients().put(username, client);
                int a = 1;
            }
        }
        else if(server.login(username, connection)){
            socketServer.getSocketClients().put(username, client);
        }
    }
    public void send(Message message){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        client.getOut().println(jsonStr);
    }

}
