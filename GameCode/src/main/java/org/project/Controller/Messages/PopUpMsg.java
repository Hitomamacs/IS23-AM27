package org.project.Controller.Messages;
import org.project.Controller.Messages.MessageID;

public class PopUpMsg extends Message{
    private String text;
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

}
