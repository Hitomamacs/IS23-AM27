package org.project.Controller.Messages;

import org.project.Controller.Messages.MessageID;

/**
 * Represents a message indicating a player's intention to quit the game.
 * Contains information about the username of the player.
 */

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
