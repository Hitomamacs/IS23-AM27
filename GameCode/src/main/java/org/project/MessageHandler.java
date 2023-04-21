package org.project;

import com.google.gson.Gson;

public class MessageHandler {
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

    }
    public void handleTopUp(Message msg){

    }
    public void handleQuit(Message msg){

    }
    public void handleLogin(Message msg){

    }
}
