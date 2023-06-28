package org.project.Controller.Control;

/**
 * Custom exception class for invalid login attempts.
 * Extends the Exception class.
 */
public class InvalidLoginException extends Exception{

    public int identifier;
    public InvalidLoginException(String message, int identifier){

        super(message);
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }

}
