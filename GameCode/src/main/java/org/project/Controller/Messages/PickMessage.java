package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;
import org.project.Model.Coordinates;

import java.util.List;

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
