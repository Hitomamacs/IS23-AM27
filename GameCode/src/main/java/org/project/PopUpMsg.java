package org.project;

public class PopUpMsg extends Message{

    private String text;

    public PopUpMsg(String text){
        super();
        this.setMessageID(MessageID.POP_UP);
        this.text = text;
    }
    public PopUpMsg(String username, String text){
        super(username);
        this.setMessageID(MessageID.POP_UP);
        this.text = text;
    }
    public String getText(){
        return text;
    }
}
