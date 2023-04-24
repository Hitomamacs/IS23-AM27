package org.project.Controller.Messages;

import org.project.Controller.Messages.Message;
import org.project.Controller.Messages.MessageID;

public class QuitMessage extends Message {

   public QuitMessage(String username){

       super(username);
       this.setMessageID(MessageID.QUIT);
   }
}
