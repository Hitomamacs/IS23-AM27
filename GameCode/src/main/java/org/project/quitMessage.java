package org.project;

public class quitMessage extends Message{
    String username;
    MessageID messageID;
   public quitMessage(String username){

       super(username);
       this.messageID = MessageID.PICK;
   }
}
