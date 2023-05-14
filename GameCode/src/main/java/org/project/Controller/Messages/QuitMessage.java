package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

public class QuitMessage extends Message{
    private String username;
   public QuitMessage(String username){
       super(MessageID.QUIT);
       this.username = username;
   }
   public String getUsername(){
       return username;
   }
}
