package org.project;

import java.util.List;

public class pickMessage extends Message{

    MessageID messageID;
    String username;
    List<Coordinates> coordinates;

    public pickMessage(String username, List<Coordinates> coordinates) {
        super(username);
        this.messageID = MessageID.PICK;
        this.coordinates = coordinates;
    }
    public List<Coordinates> getCoordinates(){
        return coordinates;
    }
}
