package org.project.Controller.Control;

public class User {

    private String username;
    private boolean connectionType;
    private boolean isConnected;

    public User(String username, boolean connectionType){
        this.username = username;
        this.connectionType = connectionType;
        this.isConnected=true;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setConnectionType(boolean connectionType){
        this.connectionType = connectionType;
    }
    public String getUsername() {
        return username;
    }
    public boolean getConnectionType(){
        return connectionType;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}


