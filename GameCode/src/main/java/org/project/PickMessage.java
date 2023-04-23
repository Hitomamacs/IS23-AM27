package org.project;

import java.util.List;

public class PickMessage extends Message{
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
