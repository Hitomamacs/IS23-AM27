package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;
import org.project.Model.Coordinates;

import java.util.List;

/**
 * Represents a message for the coordinates of the picked tiles
 * Contains information about the username and a list of coordinates.
 */

public class PickMessage extends Message{
    private List<Coordinates> coordinates;
    private String username;
    public PickMessage(String username, List<Coordinates> coordinates) {
        super(MessageID.PICK);
        this.username = username;
        this.coordinates = coordinates;
    }
    public List<Coordinates> getCoordinates(){
        return coordinates;
    }
    public String getUsername(){
        return username;
    }
}
