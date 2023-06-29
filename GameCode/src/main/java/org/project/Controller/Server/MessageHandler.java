package org.project.Controller.Server;

import com.google.gson.*;
import org.project.Controller.Control.InvalidLoginException;
import org.project.Controller.Messages.*;

/**
 * Handles incoming messages from clients and delegates them to the appropriate server methods.
 */
public class MessageHandler {
    /**
     * reference to main server
     */
    private Server server;
    /**
     * reference to client
     */
    private SocketClientHandler client;
    /**
     * reference to socket server
     */
    private SocketServer socketServer;

    /**
     * constructor
     *
     * @param server the server instance.
     * @param socketServer the socket server instance.
     * @param client the client handler instance.
     */
    public MessageHandler(Server server, SocketServer socketServer, SocketClientHandler client){
        this.server = server;
        this.socketServer = socketServer;
        this.client = client;
    }

    /**
     * Handles the incoming JSON message.
     * depending on the ID of the message, a specific method is called which manages it
     * @param jsonStr The JSON message string.
     */
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
            case CHAT -> this.handleChat(gson.fromJson(jsonStr, ChatMessage.class));
        };
    }

    /**
     * Handles the PickMessage from the client and send it to the main server
     *
     * @param pickMsg The PickMessage object.
     */
    public void handlePick(PickMessage pickMsg){
        server.pick(pickMsg.getUsername(), pickMsg.getCoordinates());
    }

    /**
     * Handles the TopUpMessage from the client and send it to the main server
     *
     * @param topUpMsg The TopUpMessage object.
     */
    public void handleTopUp(TopUpMessage topUpMsg){
        server.topUp(topUpMsg.getUsername(), topUpMsg.getColumn(), topUpMsg.getTileIndex());
    }

    /**
     * Handles the QuitMessage from the client and send it to the main server
     *
     * @param quitMessage The QuitMessage object.
     */
    public void handleQuit(QuitMessage quitMessage){
        server.quit(quitMessage.getUsername());
        client.disconnect();

    }

    /**
     * Handles the CreateGame_Message from the client and send it to the main server
     * if the operation fails, an exception is thrown
     *
     * @param create_gameMsg The CreateGame_Message object.
     */
    public void handleCreateGame(CreateGame_Message create_gameMsg){
        String username = create_gameMsg.getUsername();
        PopUpMsg popUpMsg = new PopUpMsg();
        boolean connection = create_gameMsg.getConnectionType();
        int num_players = create_gameMsg.getNumPlayers();
        if(num_players >= 2 && num_players <= 4) {

            try {
                if (server.create_game(username, connection, num_players)) {
                    socketServer.getSocketClients().put(username, client);
                    popUpMsg.setText("Game has been created");
                    popUpMsg.setIdentifier(0);
                    send(popUpMsg);
                    client.setUsername(username);
                    return;
                }
            } catch (InvalidLoginException e) {
                popUpMsg.setText(e.getMessage());
                popUpMsg.setIdentifier(e.getIdentifier());
                send(popUpMsg);
            }
            popUpMsg.setText("Already an existing game");
            popUpMsg.setIdentifier(0);
            return;
        }
        popUpMsg.setText("Number of players is not valid, has to be between 2 and 4");
        popUpMsg.setIdentifier(0);
        send(popUpMsg);
    }

    /**
     * Handles the JoinMessage from the client and send it to the main server
     * if the operation fails, an exception is thrown
     * @param joinMsg The JoinMessage object.
     */
    public void handleJoin(JoinMessage joinMsg){
        String username = joinMsg.getUsername();
        PopUpMsg popUpMsg = new PopUpMsg();
        boolean connection = joinMsg.getConnectionType();
        try {
            if (server.join(username, connection)) {
                socketServer.getSocketClients().put(username, client);
                popUpMsg.setText("Successfully joined the game");
                popUpMsg.setIdentifier(1);
                send(popUpMsg);
                client.setUsername(username);
                server.getController().refreshRequest(username);
            }
        } catch (InvalidLoginException e) {
            popUpMsg.setText(e.getMessage());
            popUpMsg.setIdentifier(e.getIdentifier());
            send(popUpMsg);
        }
    }

    /**
     * Handles the ChatMessage from the client and send it to the main server
     * if the operation fails, an exception is thrown
     * @param chatMsg The ChatMessage object.
     */
    public void handleChat(ChatMessage chatMsg){
        if(chatMsg.getReceiver().equalsIgnoreCase("broadcast"))
            server.chat(chatMsg.getUsername(), chatMsg.getText());
        else
            server.chat(chatMsg.getUsername(), chatMsg.getText(), chatMsg.getReceiver());
    }

    public void send(Message message){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(message);
        client.getOut().println(jsonStr);
        System.out.println("\nMessage sent (Message Handler send method)");
    }

}
