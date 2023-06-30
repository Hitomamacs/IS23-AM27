package org.project.Controller.Control;

import org.project.ClientPack.ObservableObject;
/**
 * Represents a user in the system.
 * Extends the ObservableObject class for implementing the observer pattern.
 */

public class User extends ObservableObject {

    /**
     * user's name
     */
    private String username;
    /**
     * if connection type is 0 is a rmi client
     * if connection type is 1 is a socket client
     */
    private boolean connectionType;
    /**
     * if user is connected this boolean is true
     * otherwise it is false
     */
    private boolean isConnected;

    /**
     * Constructs a User object with the specified username and connection type.
     * Sets the isConnected flag to true by default.
     *
     * @param username the username of the user
     * @param connectionType the connection type of the user
     */

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

    /**
     * Checks if the user is connected.
     *
     * @return true if the user is connected, false otherwise
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Sets the connected status of the user and notifies observers of the change.
     *
     * @param connected the connected status to set
     */
    public synchronized void setConnected(boolean connected) {
        isConnected = connected;
        firePropertyChange("ConnectionUpdate", this);
    }
}


