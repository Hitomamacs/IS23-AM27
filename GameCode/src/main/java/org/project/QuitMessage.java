package org.project;

public class QuitMessage extends Message{

   public QuitMessage(String username){

       super(username);
       this.setMessageID(MessageID.QUIT);
   }
}
