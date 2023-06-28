package org.project.Controller.Messages;
import org.project.Controller.Messages.MessageID;

/**
 * Represents a message for displaying a pop-up message.
 * Contains information about the text and an identifier.
 */

public class PopUpMsg extends Message{
    private String text;
    private int identifier;

    /**
     * Constructor
     * @param text of the pop-up message
     */
    public PopUpMsg(String text){
        super(MessageID.POP_UP);
        this.text = text;
    }
    public PopUpMsg(){
        super(MessageID.POP_UP);
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setIdentifier(int identifier){
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }

}
