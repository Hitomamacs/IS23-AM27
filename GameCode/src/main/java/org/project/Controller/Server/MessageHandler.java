package org.project.Controller.Server;

import com.google.gson.Gson;
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
        Message msg = gson.fromJson(jsonStr, Message.class);
        switch (msg.getMessageID()){
            case PICK -> this.handlePick(msg);
            case QUIT -> this.handleQuit(msg);
            case LOGIN -> this.handleLogin(msg);
            case TOPUP -> this.handleTopUp(msg);
        };
    }
    public void handlePick(Message msg){
        PickMessage pickMsg = (PickMessage) msg;
        server.pick(pickMsg.getUsername(), pickMsg.getCoordinates());
    }
    public void handleTopUp(Message msg){
        TopUpMessage topUpMsg = (TopUpMessage) msg;
        server.topUp(topUpMsg.getUsername(), topUpMsg.getColumn(), topUpMsg.getTileIndex());
    }
    public void handleQuit(Message msg){
        QuitMessage quitMessage = (QuitMessage) msg;
        server.quit(quitMessage.getUsername());
        client.disconnect();
    }
    public void handleLogin(Message msg){
        LoginMessage loginMsg = (LoginMessage) msg;
        String username = loginMsg.getUsername();
        boolean connection = loginMsg.getConnectionType();
        int num_players = loginMsg.getNumPlayers();
        if(!(num_players == 0)){
            if(server.login(username, connection, num_players)){
                socketServer.getSocketClients().put(username, client);
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
