package org.project.Controller.States.Exceptions;

public class InvalidMoveException extends Exception{

    int identifier;
    public InvalidMoveException(String message, int identifier){

        super(message);
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }
}
