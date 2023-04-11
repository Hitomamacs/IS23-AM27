package org.example;

public class User {

    String username;
    boolean connectionType;

    public User(String username, boolean connectionType){
        this.username = username;
        this.connectionType = connectionType;
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
}


