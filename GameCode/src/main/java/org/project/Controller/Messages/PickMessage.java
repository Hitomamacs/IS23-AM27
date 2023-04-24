package org.project.Controller.Messages;

import org.project.Controller.Messages.Message;
import org.project.Controller.Messages.MessageID;
import org.project.Model.Coordinates;

import java.util.List;

public class PickMessage extends Message {
    private List<Coordinates> coordinates;

    public PickMessage(String username, List<Coordinates> coordinates) {
        super(username);
        this.setMessageID(MessageID.PICK);
        this.coordinates = coordinates;
    }
    public List<Coordinates> getCoordinates(){
        return coordinates;
    }
}
