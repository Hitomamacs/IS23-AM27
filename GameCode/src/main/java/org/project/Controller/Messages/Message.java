package org.project.Controller.Messages;

/**
 * The class represents a generic message and is used as a superclass for other classes
 * representing specific types of messages.
 */
public class Message {

    /**
     * ID of the message
     */
    MessageID ID;

    /**
     * Constructor
     * @param ID message
     */
    public Message(MessageID ID){
        this.ID = ID;
    }
}

