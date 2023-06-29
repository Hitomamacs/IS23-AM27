package org.project.Controller.States.Exceptions;

/**
 * InvalidMoveException is an exception class that represents an invalid move in the game.
 * It is thrown when a player attempts to make an invalid move during his turn.
 */

public class InvalidMoveException extends Exception{

    int identifier;

    /**
     * Constructor
     * @param message the error message describing the invalid move
     * @param identifier the identifier for the specific type of invalid move
     */
    public InvalidMoveException(String message, int identifier){

        super(message);
        this.identifier = identifier;
    }

    /**
     * Returns the identifier for the specific type of invalid move.
     * @return the identifier
     */
    public int getIdentifier(){
        return identifier;
    }
}
