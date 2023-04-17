package org.project;

import java.util.List;

public class pickMessage {

    MessageID messageID;
    String username;
    List<Coordinates> coordinates;

    public pickMessage(String username, List<Coordinates> coordinates) {
        this.messageID = MessageID.PICK;
        this.username = username;
        this.coordinates = coordinates;
    }
    public String getUsername(){
        return username;
    }
    public List<Coordinates> getCoordinates(){
        return coordinates;
    }
    public MessageID getMessageID(){
        return messageID;
    }
}
